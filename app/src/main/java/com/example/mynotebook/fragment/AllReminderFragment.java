package com.example.mynotebook.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.mynotebook.R;

public class AllReminderFragment extends Fragment {
    private static final String TAG = "AlarmFragment";

    private Button btnOpenBottomDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        btnOpenBottomDialog = view.findViewById(R.id.btn_open_bottom_dialog);
        btnOpenBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomDialog();
            }
        });

        return view;
    }

    private void openBottomDialog() {
        if (getActivity() != null) {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_content_normal, null);

            Dialog bottomDialog = new Dialog(getActivity(),R.style.BottomDialog);
            bottomDialog.setContentView(contentView);

            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.width = getActivity().getResources().getDisplayMetrics().widthPixels;
            contentView.setLayoutParams(layoutParams);

            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomDialog.show();
        }
    }
}