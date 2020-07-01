package com.example.mynotebook;

import android.widget.Toast;

import com.example.mynotebook.application.BaseApplication;

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

    // TODO: 2020/7/1 log整合

    public static void toast(String text) {
        Toast.makeText(BaseApplication.context, text, Toast.LENGTH_SHORT).show();
    }
}
