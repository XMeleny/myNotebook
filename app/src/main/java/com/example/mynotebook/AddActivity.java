package com.example.mynotebook;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper = new MyDatabaseHelper(AddActivity.this, "notebook.db", null, 1);
        db = dbHelper.getWritableDatabase();

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
                Toast.makeText(AddActivity.this, "insert failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
