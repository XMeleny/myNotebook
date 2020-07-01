package com.example.mynotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotebook.activity.DetailActivity;
import com.example.mynotebook.activity.MainActivity;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/1
 */
public class RecordCursorAdapter extends RecyclerView.Adapter<RecordCursorAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        View recordView;

        TextView recordTitle;
        TextView recordContent;

        public ViewHolder(View view) {
            super(view);
            recordView = view;
            recordTitle = view.findViewById(R.id.record_title);
            recordContent = view.findViewById(R.id.record_content);
        }
    }

    private Context context;
    private Cursor cursor;

    public RecordCursorAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void setCursor(Cursor newCursor) {
        if (cursor == newCursor) {
            return;
        }
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.recordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (cursor.moveToPosition(position)) {
                    Intent intent = new Intent(context, DetailActivity.class);

                    String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));

                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    context.startActivity(intent);
                }
            }
        });

        holder.recordView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //弹出对话框
                new AlertDialog.Builder(context)
                        .setTitle("删除")
                        .setMessage("删除后不可撤回，确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (context instanceof MainActivity) {
                                    if (cursor.moveToPosition(holder.getAdapterPosition())) {
                                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                                        NotebookDatabaseHelper.getInstance().deleteById(id);
                                        setCursor(NotebookDatabaseHelper.getInstance().getAllNote());
                                        notifyDataSetChanged();
                                    }
                                }
                            }
                        })
                        .create()
                        .show();

                // 消费该事件，避免长短同时响应
                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            holder.recordTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
            holder.recordContent.setText(cursor.getString(cursor.getColumnIndex("content")));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
