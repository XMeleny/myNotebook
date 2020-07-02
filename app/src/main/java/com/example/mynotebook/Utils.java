package com.example.mynotebook;

import android.app.Activity;
import android.widget.Toast;

import com.example.mynotebook.application.BaseApplication;

import java.util.HashMap;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/1
 */
public class Utils {
    // TODO: 2020/7/1 添加连续返回退出，用register的方式？
    //连续返回退出
//    private long exitTime = 0;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(getApplicationContext(),
//                    "你还没有保存修改哦！点击“修改”保存\n", Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
//            finish();
//        }
//    }
    private static HashMap<Activity, Long> ACTIVITY_LAST_BACK_MAP = new HashMap<>();
    private static long EXIT_TIME = 2000;

    public static void onActivityBackPress(Activity activity) {
        long curTime = System.currentTimeMillis();
        if (ACTIVITY_LAST_BACK_MAP.containsKey(activity)) {
            long lastTime = ACTIVITY_LAST_BACK_MAP.get(activity);
            if (curTime - lastTime > EXIT_TIME) {
                //间隔过长，更新最近返回时间
                ACTIVITY_LAST_BACK_MAP.put(activity, curTime);
                toast("再按一次退出");
            } else {
                //连续返回，结束activity
                ACTIVITY_LAST_BACK_MAP.remove(activity);
                activity.finish();
            }
        } else {
            //第一次返回，添加到map中
            ACTIVITY_LAST_BACK_MAP.put(activity, curTime);
            toast("再按一次退出");
        }
    }

    // TODO: 2020/7/1 log整合

    public static void toast(String text) {
        Toast.makeText(BaseApplication.context, text, Toast.LENGTH_SHORT).show();
    }
}
