package com.example.mynotebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
//                Toast.makeText(view.getContext(),"you click: id is "+record.getId()+",and title is "+record.getTitle(),Toast.LENGTH_SHORT).show();
                //todo:implement the corresponding function

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
