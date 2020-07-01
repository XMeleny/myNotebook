package com.example.mynotebook.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotebook.NotebookDatabaseHelper;
import com.example.mynotebook.R;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private int id;
    private EditText title;
    private EditText content;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.detail_title);
        content = findViewById(R.id.detail_content);

        db = NotebookDatabaseHelper.getInstance().getWritableDatabase();

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
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
