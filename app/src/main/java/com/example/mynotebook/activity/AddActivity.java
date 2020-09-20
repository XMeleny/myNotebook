package com.example.mynotebook.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotebook.R;
import com.example.mynotebook.database.NotebookDatabaseHelper;
import com.example.mynotebook.utility.Utils;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(content)) {
            long res = NotebookDatabaseHelper.insert(title, content);
            if (res == -1) {
                Utils.toast("插入失败");
            }
        }
    }
}
