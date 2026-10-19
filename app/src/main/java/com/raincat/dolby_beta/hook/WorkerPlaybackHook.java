package com.raincat.dolby_beta.hook;

import android.content.Context;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Lets the player ask the Worker for an audio URL before NetEase blocks a
 * paid/album song with its purchase dialog. Only enabled in Worker API mode.
 */
public class WorkerPlaybackHook {
    public WorkerPlaybackHook(Context context) {
        ClassLoader loader = context.getClassLoader();
        Class<?> musicInfo = XposedHelpers.findClassIfExists(
                "com.netease.cloudmusic.meta.MusicInfo", loader);
        Class<?> songPrivilege = XposedHelpers.findClassIfExists(
                "com.netease.cloudmusic.meta.virtual.SongPrivilege", loader);

        if (musicInfo != null) {
            hook(musicInfo, "canPlayMusicOnline", true);
            hook(musicInfo, "isFeeSong", false);
            hook(musicInfo, "isPermanentPayed", true);
            hook(musicInfo, "needAuditionSong", false);
        }
        if (songPrivilege != null) {
            hook(songPrivilege, "getPlayMaxLevel", 999000);
            hook(songPrivilege, "getDownMaxLevel", 999000);
            hook(songPrivilege, "getFee", 0);
            hook(songPrivilege, "isPermanentPayed", true);
            hook(songPrivilege, "needAuditionSong", false);
        }
    }

    private void hook(Class<?> clazz, String method, Object value) {
        try {
            XposedHelpers.findAndHookMethod(clazz, method,
                    XC_MethodReplacement.returnConstant(value));
        } catch (Throwable throwable) {
            XposedBridge.log("[dolby_beta] Worker playback hook skipped: "
                    + clazz.getSimpleName() + "." + method + " (" + throwable + ")");
        }
    }
}
