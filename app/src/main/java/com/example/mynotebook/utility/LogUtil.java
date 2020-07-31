package com.example.mynotebook.utility;

import android.util.Log;

import com.example.mynotebook.BuildConfig;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/31
 */
public class LogUtil {
    private static String BUILD_TYPE = BuildConfig.BUILD_TYPE;

    private static boolean isDebug() {
        return "debug".equalsIgnoreCase(BUILD_TYPE);
    }

    public void d(String str) {
        d("xm", str);
    }

    public void d(String tag, String str) {
        if (isDebug()) {
            Log.d(tag, str);
        }
    }

    public void i(String str) {
        i("xm", str);
    }

    public void i(String tag, String str) {
        if (isDebug()) {
            Log.i(tag, str);
        }
    }

    public void w(String str) {
        w("xm", str);
    }

    public void w(String tag, String str) {
        if (isDebug()) {
            Log.w(tag, str);
        }
    }

    public void e(String str) {
        e("xm", str);
    }

    public void e(String tag, String str) {
        Log.e(tag, str);
    }
}
