package com.example.mynotebook.activity;

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

import com.example.mynotebook.NotebookDatabaseHelper;
import com.example.mynotebook.R;
import com.example.mynotebook.RecordCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private ImageButton add;

    private SQLiteDatabase db;
    private RecordCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = NotebookDatabaseHelper.getInstance().getWritableDatabase();

        adapter = new RecordCursorAdapter(this, null);
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Cursor cursor = db.query("NOTE", null, null, null, null, null, "id desc");
        adapter.setCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    public void deleteItem(int id) {
        db.delete("note", "id=?", new String[]{String.valueOf(id)});
        initData();
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
