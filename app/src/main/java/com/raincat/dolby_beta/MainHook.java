package com.raincat.dolby_beta;

import android.text.TextUtils;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * <pre>
 *     author : RainCat
 *     e-mail : nining377@gmail.com
 *     time   : 2019/10/23
 *     desc   : hook入口
 *     version: 1.0
 * </pre>
 */
public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Exception {
        if (!TextUtils.isEmpty(lpparam.packageName) && lpparam.packageName.equals("com.netease.cloudmusic")) {
            new Hook(lpparam);
        } else if (!TextUtils.isEmpty(lpparam.packageName) && lpparam.packageName.equals("com.netease.cloudmusic.lite")) {
            new HookOther(lpparam);
        } else if (!TextUtils.isEmpty(lpparam.packageName) && lpparam.packageName.equals("com.hihonor.cloudmusic")) {
            new HookOther(lpparam);
        }
    }

}
