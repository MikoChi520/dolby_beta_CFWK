package com.raincat.dolby_beta.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <pre>
 *     author : RainCat
 *     e-mail : nining377@gmail.com
 *     time   : 2021/04/14
 *     desc   : 设置中心
 *     version: 1.0
 * </pre>
 */

public class SettingHelper {
    public static final String refresh_setting = "β_refresh_setting";
    public static final String proxy_setting = "β_proxy_setting";
    public static final String background_setting = "β_background_setting";
    public static final String beauty_setting = "β_beauty_setting";
    public static final String sidebar_setting = "β_sidebar_setting";

    public static final String master_key = "β_master_key";
    public static final String master_title = "总开关";

    public static final String dex_key = "β_dex_key";
    public static final String dex_title = "启用DEX缓存";
    public static final String dex_sub = "加快模块加载速度，但同版本号的内测版与稳定版互装可能会有兼容性问题";

    public static final String warn_key = "β_warn_key";
    public static final String warn_title = "开启Hook警告";
    public static final String warn_sub = "当模块出现部分类无法Hook时在通知栏上显示，方便定位排查问题";

    public static final String black_key = "β_black_key";
    public static final String black_title = "本地黑胶";
    public static final String black_sub = "去广告、鲸云音效、个性换肤等（自定义启动图等需要访问网易服务器的设置不可用）";

    public static final String listen_key = "β_listen_key";
    public static final String listen_title = "解锁一起听蒙面查看权限";
    public static final String listen_sub = "开启后可直接查看对方信息，无需对方解除蒙面";

    public static final String fix_comment_key = "β_fix_comment_key";
    public static final String fix_comment_title = "修复评论区加载失败";
    public static final String fix_comment_sub = "如平时不看评论区或评论区无问题请勿打钩";

    public static final String update_key = "β_update_key";
    public static final String update_title = "隐藏升级提示";

    public static final String sign_key = "β_sign_key";
    public static final String sign_title = "自动签到";

    public static final String sign_song_key = "β_sign_song_key";
    public static final String sign_song_title = "每日歌曲打卡";
    public static final String sign_song_sub = "获取每日推荐歌单中的歌曲进行打卡，有利于提高等级，会影响年度听歌总结且有可能会导致几天内签到天数不变，介意者慎用";

    public static final String proxy_key = "β_proxy_key";
    public static final String proxy_title = "Worker API 设置";

    public static final String background_key = "β_background_key";
    public static final String background_title = "播放界面背景设置";

    public static final String worker_key = "β_worker_key";
    public static final String worker_title = "Worker API 模式";
    public static final String worker_sub = "直接调用 Cloudflare Worker 补全音源";

    public static final String worker_url_key = "β_worker_url_key";
    public static final String worker_url_title = "Worker 地址";
    public static final String worker_url_default = "https://music.nix.de5.net";

    public static final String kuwo_cookie_key = "β_kuwo_cookie_key";
    public static final String kuwo_cookie_title = "酷我Cookie";
    public static final String kuwo_cookie_default = "Hm_Iuvt_cdb524f42f0ce19b169b8072123a4727=CQXkhzXjGD6MFQrPTBxEpSmZXF78wP8e; Secret=1d0d220792feb563f97fdb0de2b7ebad69f781cdcdbe51d1203a3be9d3e92f5e04b00a24";

    public static final String qq_cookie_key = "β_qq_cookie_key";
    public static final String qq_cookie_title = "QQCookie";
    public static final String qq_cookie_default = "uin=<your_uin>; qm_keyst=<your_qm_keyst>";

    public static final String migu_cookie_key = "β_migu_cookie_key";
    public static final String migu_cookie_title = "咪咕Cookie";
    public static final String migu_cookie_default = "<your_aversionid>";

    public static final String beauty_key = "β_beauty_key";
    public static final String beauty_title = "美化设置";

    public static final String beauty_night_mode_key = "β_beauty_night_mode_key";
    public static final String beauty_night_mode_title = "跟随系统切换夜间模式";
    public static final String beauty_night_mode_sub = "自动根据系统深色模式状态切换夜间/日间模式";

    public static final String beauty_tab_hide_key = "β_beauty_tab_hide_key";
    public static final String beauty_tab_hide_title = "精简Tab";
    public static final String beauty_tab_hide_sub = "首页仅保留“我的”与“发现”，并默认显示“我的";

    public static final String beauty_bubble_hide_key = "β_beauty_bubble_hide_key";
    public static final String beauty_bubble_hide_title = "移除小红点";

    public static final String beauty_banner_hide_key = "β_beauty_banner_hide_key";
    public static final String beauty_banner_hide_title = "移除发现页与歌单广场Banner";

    public static final String beauty_ksong_hide_key = "β_beauty_ksong_key";
    public static final String beauty_ksong_hide_title = "移除播放页K歌图标";

    public static final String beauty_black_hide_key = "β_beauty_black_key";
    public static final String beauty_black_hide_title = "播放页专辑图片外面的黑胶隐藏";

    public static final String beauty_rotation_key = "β_beauty_rotation_key";
    public static final String beauty_rotation_title = "播放页专辑图片停止转动";

    public static final String beauty_background_key = "β_beauty_background_key";
    public static final String beauty_background_title = "自定义播放界面背景";

    public static final String beauty_comment_hot_key = "β_beauty_comment_hot_key";
    public static final String beauty_comment_hot_title = "评论区优先显示“最热”内容";

    public static final String beauty_sidebar_hide_key = "β_beauty_sidebar_hide_key";
    public static final String beauty_sidebar_hide_title = "精简侧边栏";
    public static final String beauty_sidebar_hide_sub = "部分Item需配合“设置”->“侧边栏管理”开关生效";

    public static final String background_url_key = "β_background_url_key";
    public static final String background_url_title = "图片URL(请自行上传至图床)";
    public static final String background_url_default = "";

    public static final String background_blur_key = "β_background_blur_key";
    public static final String background_blur_title = "高斯模糊度(默认透明无模糊)";
    public static final int background_blur_default = 0 ;

    private static SettingHelper instance;

    private SharedPreferences sharedPreferences;
    private HashMap<String, Boolean> settingMap;
    private HashMap<String, Boolean> sidebarSettingMap;

    public static SettingHelper getInstance() {
        return instance;
    }

    private SettingHelper(Context context) {
        refreshSetting(context);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SettingHelper(context);
        }
    }

    public void refreshSetting(Context context) {
        sharedPreferences = context.getSharedPreferences("com.netease.cloudmusic.preferences", Context.MODE_MULTI_PROCESS);
        settingMap = new HashMap<>();

        settingMap.put(master_key, sharedPreferences.getBoolean(master_key, true));
        settingMap.put(dex_key, sharedPreferences.getBoolean(dex_key, true));
        settingMap.put(warn_key, sharedPreferences.getBoolean(warn_key, true));
        settingMap.put(black_key, sharedPreferences.getBoolean(black_key, true));
        settingMap.put(listen_key, sharedPreferences.getBoolean(listen_key, false));
        settingMap.put(fix_comment_key, sharedPreferences.getBoolean(fix_comment_key, false));
        settingMap.put(update_key, sharedPreferences.getBoolean(update_key, true));
        settingMap.put(sign_key, sharedPreferences.getBoolean(sign_key, false));
        settingMap.put(sign_song_key, sharedPreferences.getBoolean(sign_song_key, false));

        settingMap.put(worker_key, sharedPreferences.getBoolean(worker_key, true));

        settingMap.put(beauty_night_mode_key, sharedPreferences.getBoolean(beauty_night_mode_key, false));
        settingMap.put(beauty_tab_hide_key, sharedPreferences.getBoolean(beauty_tab_hide_key, false));
        settingMap.put(beauty_bubble_hide_key, sharedPreferences.getBoolean(beauty_bubble_hide_key, false));
        settingMap.put(beauty_banner_hide_key, sharedPreferences.getBoolean(beauty_banner_hide_key, false));
        settingMap.put(beauty_ksong_hide_key, sharedPreferences.getBoolean(beauty_ksong_hide_key, false));
        settingMap.put(beauty_rotation_key, sharedPreferences.getBoolean(beauty_rotation_key, false));
        settingMap.put(beauty_black_hide_key, sharedPreferences.getBoolean(beauty_black_hide_key, false));
        settingMap.put(beauty_comment_hot_key, sharedPreferences.getBoolean(beauty_comment_hot_key, false));
        settingMap.put(beauty_background_key, sharedPreferences.getBoolean(beauty_background_key, false));
    }

    public void setSetting(String key, boolean value) {
        settingMap.put(key, value);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getSetting(String key) {
        Boolean value = settingMap.get(key);
        return value != null && value;
    }

    public boolean isEnable(String key) {
        return getSetting(master_key) && getSetting(key);
    }

    private void deleteSetting(String key) {
        if (sharedPreferences.contains(key)) {
            sharedPreferences.edit().remove(key).apply();
        }
    }

    public void resetSetting() {
        deleteSetting(master_key);
        deleteSetting(dex_key);
        deleteSetting(warn_key);
        deleteSetting(black_key);
        deleteSetting(listen_key);
        deleteSetting(fix_comment_key);
        deleteSetting(update_key);
        deleteSetting(sign_key);
        deleteSetting(sign_song_key);
        deleteSetting(worker_key);
        deleteSetting(worker_url_key);
        deleteSetting(beauty_night_mode_key);
        deleteSetting(beauty_tab_hide_key);
        deleteSetting(beauty_bubble_hide_key);
        deleteSetting(beauty_banner_hide_key);
        deleteSetting(beauty_ksong_hide_key);
        deleteSetting(beauty_rotation_key);
        deleteSetting(beauty_black_hide_key);
        deleteSetting(beauty_comment_hot_key);
        deleteSetting(beauty_background_key);
    }

    public HashMap<String, Boolean> getSidebarSetting(LinkedHashMap<String, String> map) {
        if (sidebarSettingMap == null) {
            sidebarSettingMap = new HashMap<>();
            for (String key : map.keySet()) {
                sidebarSettingMap.put(key, sharedPreferences.getBoolean(key, false));
            }
        }
        return sidebarSettingMap;
    }

    public void setSidebarSetting(String key, boolean value) {
        sidebarSettingMap.put(key, value);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public String getWorkerUrl() {
        String value = sharedPreferences.getString(SettingHelper.worker_url_key, SettingHelper.worker_url_default);
        return value == null ? SettingHelper.worker_url_default : value.trim();
    }

    public void setWorkerUrl(String url) {
        sharedPreferences.edit().putString(SettingHelper.worker_url_key, url == null ? "" : url.trim()).apply();
    }

    public String getKuwoCookie() {
        return sharedPreferences.getString(SettingHelper.kuwo_cookie_key, SettingHelper.kuwo_cookie_default);
    }
    public void setKuwoCookie(String cookie) {
        if (!TextUtils.isEmpty(cookie))
            sharedPreferences.edit().putString(SettingHelper.kuwo_cookie_key, cookie).apply();
    }
    public String getQqCookie() {
        return sharedPreferences.getString(SettingHelper.qq_cookie_key, SettingHelper.qq_cookie_default);
    }
    public void setQqCookie(String cookie) {
        if (!TextUtils.isEmpty(cookie))
            sharedPreferences.edit().putString(SettingHelper.qq_cookie_key, cookie).apply();
    }
    public String getMiguCookie() {
        return sharedPreferences.getString(SettingHelper.migu_cookie_key, SettingHelper.migu_cookie_default);
    }
    public void setMiguCookie(String cookie) {
        if (!TextUtils.isEmpty(cookie))
            sharedPreferences.edit().putString(SettingHelper.migu_cookie_key, cookie).apply();
    }
    public String getPictureUrl() {
        return sharedPreferences.getString(SettingHelper.background_url_key, SettingHelper.background_url_default);
    }
    public void setPictureUrl(String url) {
        if (!TextUtils.isEmpty(url))
            sharedPreferences.edit().putString(SettingHelper.background_url_key, url).apply();
    }
    public int getBackgroundBlur() {
        return sharedPreferences.getInt(SettingHelper.background_blur_key, SettingHelper.background_blur_default);
    }

    public void setBackgroundBlur(String blur) {
        if (!TextUtils.isEmpty(blur))
            sharedPreferences.edit().putInt(SettingHelper.background_blur_key, Integer.parseInt(blur)).apply();
    }
}
