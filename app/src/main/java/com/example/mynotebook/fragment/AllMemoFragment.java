package com.example.mynotebook.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mynotebook.R;
import com.example.mynotebook.activity.AddActivity;
import com.example.mynotebook.activity.MainActivity;
import com.example.mynotebook.application.BaseApplication;
import com.example.mynotebook.database.RecordCursorAdapter;

public class AllMemoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecordCursorAdapter adapter;

    private ImageButton add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_memo, container, false);

        adapter = new RecordCursorAdapter(getActivity());
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        // 动画
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(300);
        defaultItemAnimator.setRemoveDuration(300);
        recyclerView.setItemAnimator(defaultItemAnimator);

        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseApplication.context, AddActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}