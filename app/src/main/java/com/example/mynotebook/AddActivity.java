package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btn_add = findViewById(R.id.insert);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new MyDatabaseHelper(AddActivity.this, "notebook.db",null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("title",((EditText)findViewById(R.id.titleEdit)).getText().toString());
                values.put("content",((EditText)findViewById(R.id.contentEdit)).getText().toString());

                long res = db.insert("NOTE",null, values);
                if(res != -1) {
                    Toast.makeText(AddActivity.this,"insert Successful",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddActivity.this,"insert failed",Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
