package com.example.mynotebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<Record> mRecordList;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView recordTitle;
        TextView recordContent;
        View recordView;

        public ViewHolder(View view) {
            super(view);
            recordTitle = view.findViewById(R.id.record_title);
            recordContent = view.findViewById(R.id.record_content);
            recordView = view;
        }
    }

    public RecordAdapter(Context context, List<Record> recordList) {
        this.context = context;
        this.mRecordList = recordList;
    }

    public void setmRecordList(List<Record> mRecordList) {
        this.mRecordList = mRecordList;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.recordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Record record = mRecordList.get(position);

                Intent intent = new Intent(context, DetailActivity.class);
                String str_id = String.valueOf(record.getId());
                String str_title = record.getTitle();
                String str_content = record.getContent();
                intent.putExtra("id", str_id);
                intent.putExtra("title", str_title);
                intent.putExtra("content", str_content);
                context.startActivity(intent);
            }
        });

        holder.recordView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //弹出对话框
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("删除")
                        .setMessage("删除后不可撤回，确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (context instanceof MainActivity) {
                                    int id = mRecordList.get(holder.getAdapterPosition()).getId();
                                    ((MainActivity) context).deleteItem(id);
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
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        Record record = mRecordList.get(position);
        holder.recordTitle.setText(record.getTitle());
        holder.recordContent.setText(record.getContent());
    }

    @Override
    public int getItemCount() {
        return mRecordList.size();
    }
}
