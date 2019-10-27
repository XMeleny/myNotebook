package com.example.mynotebook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private ImageButton add;

    private MyDatabaseHelper helper;
    private List<Record> recordList=new ArrayList<>();
    private RecordAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        helper=new MyDatabaseHelper(this,"notebook.db",null,1);
        helper.getWritableDatabase();

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        recyclerView=findViewById(R.id.recycleView);
        layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecordAdapter(this,recordList);
        recyclerView.setAdapter(adapter);
    }

    private void init()
    {
        SQLiteDatabase db=helper.getWritableDatabase();

//        Log.d(TAG, "in init function");

        //清空list
        recordList.clear();

        //select all and put them in recordList
        Cursor cursor=db.query("NOTE",null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String content=cursor.getString(cursor.getColumnIndex("content"));

//                Log.d(TAG, String.valueOf(id));

                Record record=new Record(id,title,content);
                recordList.add(record);
            }while (cursor.moveToNext());
        }
        cursor.close();

    }

    //连续返回退出
    private long exitTime=0;

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit(){
        if((System.currentTimeMillis()-exitTime)>2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else{
            finish();
            System.exit(0);
        }
    }
}
