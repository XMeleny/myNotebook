package com.example.mynotebook.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotebook.R;
import com.example.mynotebook.activity.DetailActivity;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/1
 */
public class RecordCursorAdapter extends RecyclerView.Adapter<RecordCursorAdapter.ViewHolder> implements NotebookAdapter {

    @Override
    public void onChanged() {
        setCursor(NotebookDatabaseHelper.getAllNote());
        notifyDataSetChanged();
    }

    @Override
    public void onInserted(int pos) {
        setCursor(NotebookDatabaseHelper.getAllNote());
        notifyItemInserted(pos);
    }

    @Override
    public void onDeleted(int pos) {
        setCursor(NotebookDatabaseHelper.getAllNote());
        notifyItemRemoved(pos);
    }

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

    public RecordCursorAdapter(Context context) {
        this.context = context;
        this.cursor = NotebookDatabaseHelper.getAllNote();
        NotebookDatabaseHelper.registerAdapter(this);
    }

    public void setCursor(Cursor newCursor) {
        if (cursor == newCursor) {
            return;
        }
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
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
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));

                    DetailActivity.updateMemo(context, id, title, content);
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
                                if (cursor.moveToPosition(holder.getAdapterPosition())) {
                                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                                    NotebookDatabaseHelper.deleteById(id);
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
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            if (TextUtils.isEmpty(title)) {
                holder.recordTitle.setVisibility(View.GONE);
            } else {
                holder.recordTitle.setVisibility(View.VISIBLE);
                holder.recordTitle.setText(title);
            }
            if (TextUtils.isEmpty(content)) {
                holder.recordContent.setVisibility(View.GONE);
            } else {
                holder.recordContent.setVisibility(View.VISIBLE);
                holder.recordContent.setText(content);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
