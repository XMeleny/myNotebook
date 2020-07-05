package com.example.mynotebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mynotebook.application.BaseApplication;

import java.util.HashSet;
import java.util.Set;

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

    // 监听者模式
    private static Set<NotebookAdapter> adapters = new HashSet<>();
    public static void registerAdapter(NotebookAdapter adapter) {
        adapters.add(adapter);
    }

    private static void notifyDataChanged() {
        for (NotebookAdapter adapter : adapters) {
            adapter.onChanged();
        }
    }

    private static void notifyItemInserted(int pos){
        for(NotebookAdapter adapter: adapters){
            adapter.onInserted(pos);
        }
    }


    //functions
    public static Cursor getAllNote() {
        Cursor res = getInstance().getReadableDatabase().query("note", null, null, null, null, null, "id desc");
        return res;
    }

    public static long insert(String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        long res = getInstance().getWritableDatabase().insert("note", null, values);
        notifyItemInserted(0);
        return res;
    }

    public static int deleteById(int id) {
        int res = getInstance().getWritableDatabase().delete("note", "id=?", new String[]{String.valueOf(id)});
        notifyDataChanged();
        return res;
    }

    public static int update(int id, String title, String content) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        int res = getInstance().getWritableDatabase().update("note", values, "id=?", new String[]{String.valueOf(id)});
        notifyDataChanged();
        return res;
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
