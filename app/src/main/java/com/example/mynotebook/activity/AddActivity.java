package com.example.mynotebook.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotebook.database.NotebookDatabaseHelper;
import com.example.mynotebook.R;
import com.example.mynotebook.utility.Utils;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.titleEdit);
        etContent = findViewById(R.id.contentEdit);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();

        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(content)) {
            long res = NotebookDatabaseHelper.insert(title, content);
            if (res == -1) {
                Utils.toast("插入失败");
            }
        }
    }
}
