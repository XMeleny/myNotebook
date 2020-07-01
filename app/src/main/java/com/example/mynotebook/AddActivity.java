package com.example.mynotebook;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = NotebookDatabaseHelper.getInstance().getWritableDatabase();

        etTitle = findViewById(R.id.titleEdit);
        etContent = findViewById(R.id.contentEdit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(content)) {
            ContentValues values = new ContentValues();

            values.put("title", ((EditText) findViewById(R.id.titleEdit)).getText().toString());
            values.put("content", ((EditText) findViewById(R.id.contentEdit)).getText().toString());

            long res = db.insert("NOTE", null, values);
            if (res == -1) {
                Utils.toast("插入失败");
            }
        }
    }
}
