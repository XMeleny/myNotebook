package com.example.mynotebook.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mynotebook.R;
import com.example.mynotebook.application.BaseApplication;

import java.util.HashMap;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/1
 */
public class Utils {
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

    public static void toast(String text) {
        Toast.makeText(BaseApplication.context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showBottomDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.BottomDialog);

        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bottom_dialog, null);
        dialog.setContentView(root);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        root.measure(0, 0);//0 unspecified
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.height = root.getMeasuredHeight();

        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}
