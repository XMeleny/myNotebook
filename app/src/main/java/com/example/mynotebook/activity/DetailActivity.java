package com.example.mynotebook.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotebook.R;
import com.example.mynotebook.database.NotebookDatabaseHelper;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    //bundle key and value
    private static final String KEY_MODE = "mode";
    private static final String VALUE_MODE_INSERT = "insert";
    private static final String VALUE_MODE_UPDATE = "update";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

    //exception cases
    private static final String EXCEPTION_CASE_INIT_ERROR = "init DetailActivity error, please use getXxxIntent to start activity";

    //data
    private String mode;
    private int id;

    //ui
    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private void init() {
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        //handle the mode
        Intent intent = getIntent();

        mode = intent.getStringExtra(KEY_MODE);

        if (TextUtils.isEmpty(mode)) {
            throw new IllegalStateException(EXCEPTION_CASE_INIT_ERROR);
        }

        switch (mode) {
            case VALUE_MODE_INSERT:
                //do nothing
                break;
            case VALUE_MODE_UPDATE:
                id = intent.getIntExtra("id", 0);
                etTitle.setText(intent.getStringExtra("title"));
                etContent.setText(intent.getStringExtra("content"));
                break;
            default:
                break;
        }
    }

    public static void insertMemo(@NonNull Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_MODE, VALUE_MODE_INSERT);
        context.startActivity(intent);
    }

    public static void updateMemo(@NonNull Context context, int id, String title, String content) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_MODE, VALUE_MODE_UPDATE);

        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_CONTENT, content);

        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            switch (mode) {
                case VALUE_MODE_INSERT:
                    //do nothing
                    break;
                case VALUE_MODE_UPDATE:
                    NotebookDatabaseHelper.deleteById(id);
                    break;
                default:
                    break;
            }
        } else {
            switch (mode) {
                case VALUE_MODE_INSERT:
                    NotebookDatabaseHelper.insert(title, content);
                    break;
                case VALUE_MODE_UPDATE:
                    NotebookDatabaseHelper.update(id, title, content);
                    break;
                default:
                    break;
            }
        }
    }
}
