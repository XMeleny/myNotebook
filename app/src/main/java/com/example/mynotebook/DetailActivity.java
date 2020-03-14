package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.BufferOverflowException;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    int id;
    EditText title;
    EditText content;

    Button btn_update;
    Button btn_delete;

    Intent intent;
    MyDatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        title = findViewById(R.id.detail_title);
        content = findViewById(R.id.detail_content);

        btn_update = findViewById(R.id.detail_update);
        btn_delete = findViewById(R.id.detail_delete);

        helper = new MyDatabaseHelper(this, "notebook.db", null, 1);
        db = helper.getWritableDatabase();


        intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click update button");
                ContentValues values = new ContentValues();
                values.put("title", title.getText().toString());
                values.put("content", content.getText().toString());
                db.update("note", values, "id=?", new String[]{String.valueOf(id)});
                finish();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:test the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("删除");
                builder.setMessage("删除后不可撤回，确定删除吗？");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("NOTE", "id=?", new String[]{String.valueOf(id)});
                        finish();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailActivity.this, "取消删除", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
//                db.delete("NOTE","id=?",new String[]{String.valueOf(id)});
//                finish();
            }
        });
    }

    //TODO: ALTER BEFORE GO BACK
    //TODO: TEST
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
                    "你还没有保存修改哦！点击“修改”保存\n", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
