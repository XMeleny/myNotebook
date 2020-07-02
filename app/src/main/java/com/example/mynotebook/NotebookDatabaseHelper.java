package com.example.mynotebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mynotebook.application.BaseApplication;

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

    private static synchronized NotebookDatabaseHelper getInstance() {
        if (notebookDatabaseHelper == null) {
            synchronized (NotebookDatabaseHelper.class) {
                notebookDatabaseHelper = new NotebookDatabaseHelper();
            }
        }
        return notebookDatabaseHelper;
    }

    //functions
    public static Cursor getAllNote() {
        return getInstance().getReadableDatabase().query("note", null, null, null, null, null, "id desc");
    }

    public static int deleteById(int id) {
        return getInstance().getWritableDatabase().delete("note", "id=?", new String[]{String.valueOf(id)});
    }

    public static int update(int id, String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        return getInstance().getWritableDatabase().update("note", values, "id=?", new String[]{String.valueOf(id)});
    }

    public static long insert(String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        return getInstance().getWritableDatabase().insert("note", null, values);
    }

    //database structure
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
