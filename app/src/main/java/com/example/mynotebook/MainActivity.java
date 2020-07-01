package com.example.mynotebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private ImageButton add;

    private MyDatabaseHelper helper;
    private SQLiteDatabase db;
    private List<Record> recordList = new ArrayList<>();
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new RecordAdapter(this, recordList);

        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    // TODO: 2020/7/1 recyclerView绑定数据库而非list
    // TODO: 2020/7/1 添加长按功能
    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        helper = new MyDatabaseHelper(this, "notebook.db", null, 1);
        db = helper.getReadableDatabase();

        //清空list
        recordList.clear();

        //select all and put them in recordList
        Cursor cursor = db.query("NOTE", null, null, null, null, null, "id desc");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Record record = new Record(id, title, content);
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.setmRecordList(recordList);
        adapter.notifyDataSetChanged();
    }

    //连续返回退出
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
