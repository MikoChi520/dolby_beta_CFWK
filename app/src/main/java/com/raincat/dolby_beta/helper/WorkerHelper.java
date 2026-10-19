package com.raincat.dolby_beta.helper;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Calls the Cloudflare Worker JSON API to replace NetEase player/download URLs.
 * This avoids the HTTP CONNECT proxy and TLS interception entirely.
 */
public final class WorkerHelper {
    private static final String TAG = "DolbyWorker";
    private static final int CONNECT_TIMEOUT = 6000;
    private static final int READ_TIMEOUT = 9000;

    private WorkerHelper() {
    }

    public static String modifyPlayer(String original) {
        if (!SettingHelper.getInstance().isEnable(SettingHelper.worker_key)) {
            return original;
        }
        String base = normalize(SettingHelper.getInstance().getWorkerUrl());
        if (TextUtils.isEmpty(base)) {
            return original;
        }
        // NetEase normally calls this hook on a worker thread. Avoid a
        // NetworkOnMainThreadException if an old client calls it synchronously.
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return original;
        }

        try {
            JSONObject root = new JSONObject(original);
            Object raw = root.opt("data");
            JSONArray songs;
            boolean single = false;
            if (raw instanceof JSONArray) {
                songs = (JSONArray) raw;
            } else if (raw instanceof JSONObject) {
                songs = new JSONArray();
                songs.put(raw);
                single = true;
            } else {
                return original;
            }
            if (songs.length() == 0) {
                return original;
            }

            JSONArray ids = new JSONArray();
            for (int i = 0; i < songs.length(); i++) {
                JSONObject song = songs.optJSONObject(i);
                long id = song == null ? 0 : song.optLong("id");
                if (id > 0) {
                    ids.put(id);
                }
            }
            if (ids.length() == 0) {
                return original;
            }

            String endpoint = base + "/api/song/enhance/player/url?ids="
                    + URLEncoder.encode(ids.toString(), "UTF-8");
            String body = get(endpoint);
            if (TextUtils.isEmpty(body)) {
                return original;
            }

            JSONArray results = new JSONObject(body).optJSONArray("data");
            if (results == null) {
                return original;
            }
            Map<Long, JSONObject> byId = new HashMap<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.optJSONObject(i);
                if (result != null) {
                    byId.put(result.optLong("id"), result);
                }
            }

            boolean changed = false;
            for (int i = 0; i < songs.length(); i++) {
                JSONObject song = songs.optJSONObject(i);
                if (song == null) {
                    continue;
                }
                JSONObject result = byId.get(song.optLong("id"));
                if (result != null && apply(song, result)) {
                    changed = true;
                }
            }
            if (!changed) {
                return original;
            }

            root.put("data", single ? songs.getJSONObject(0) : songs);
            return root.toString();
        } catch (Throwable throwable) {
            Log.w(TAG, "Worker request failed", throwable);
            return original;
        }
    }

    private static boolean apply(JSONObject song, JSONObject result) throws Exception {
        // Cloud-disk songs (flag 8) must keep their original URL.
        if ((song.optInt("flag", 0) & 0x8) != 0) {
            return false;
        }
        String url = result.optString("url", "");
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        // NetEase's modern clients reject cleartext media URLs.
        if (url.startsWith("http://")) {
            url = "https://" + url.substring("http://".length());
        }

        song.put("url", url);
        song.put("br", result.optInt("br", song.optInt("br")));
        song.put("size", result.optLong("size", song.optLong("size")));
        song.put("md5", result.optString("md5", ""));
        song.put("code", 200);
        song.put("fee", 0);
        song.put("payed", 1);
        song.put("flag", 0);
        song.put("freeTrialInfo", JSONObject.NULL);

        copyIfPresent(song, result, "type");
        copyIfPresent(song, result, "encodeType");
        copyIfPresent(song, result, "level");
        return true;
    }

    private static void copyIfPresent(JSONObject target, JSONObject source, String key) throws Exception {
        String value = source.optString(key, "");
        if (!TextUtils.isEmpty(value)) {
            target.put(key, value);
        }
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        String base = value.trim();
        while (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        if (TextUtils.isEmpty(base)) {
            return "";
        }
        if (!base.startsWith("http://") && !base.startsWith("https://")) {
            base = "https://" + base;
        }
        return base;
    }

    private static String get(String endpoint) {
        HttpURLConnection connection = null;
        InputStream stream = null;
        try {
            connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "DolbyBeta-Worker/1.0");

            int code = connection.getResponseCode();
            stream = code >= 200 && code < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream();
            if (stream == null) {
                Log.w(TAG, "Worker HTTP " + code);
                return "";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (Throwable throwable) {
            Log.w(TAG, "Worker request error", throwable);
            return "";
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception ignored) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
