package com.example.mynotebook;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    int id;
    EditText title;
    EditText content;

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

        btn_delete = findViewById(R.id.detail_delete);

        helper = new MyDatabaseHelper(this, "notebook.db", null, 1);
        db = helper.getWritableDatabase();

        intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String strTitle = title.getText().toString().trim();
        String strContent = content.getText().toString().trim();

        // 没有内容则删除
        if (TextUtils.isEmpty(strTitle) && TextUtils.isEmpty(strContent)) {
            db.delete("note", "id=?", new String[]{String.valueOf(id)});
        } else {
            ContentValues values = new ContentValues();
            values.put("title", strTitle);
            values.put("content", strContent);
            db.update("note", values, "id=?", new String[]{String.valueOf(id)});
        }
    }
}
