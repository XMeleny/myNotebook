package com.example.mynotebook;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/1
 */
public class NotebookDatabaseHelper extends SQLiteOpenHelper {

    private static volatile NotebookDatabaseHelper notebookDatabaseHelper;

    private NotebookDatabaseHelper() {
        super(BaseApplication.context, "notebook.db", null, 1);
    }

    public static synchronized NotebookDatabaseHelper getInstance() {
        if (notebookDatabaseHelper == null) {
            synchronized (NotebookDatabaseHelper.class) {
                notebookDatabaseHelper = new NotebookDatabaseHelper();
            }
        }
        return notebookDatabaseHelper;
    }

    public static final String CREATE_NOTEBOOK =
            "create table NOTE(" +
            "id integer primary key autoincrement," +
            "title text," +
            "content text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_NOTEBOOK);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.toast("create notebook error!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //改版本号时
        // FIXME: 2020/7/1 不去删除数据库而仅更新
        db.execSQL("drop table if exists NOTE");
        onCreate(db);
    }
}
