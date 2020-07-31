package com.example.mynotebook.fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mynotebook.R;
import com.example.mynotebook.activity.AddActivity;
import com.example.mynotebook.application.BaseApplication;

import java.util.Calendar;

public class AllReminderFragment extends Fragment {

    private Button btnOpenDatePick;
    private static final String TAG = "AlarmFragment";
    Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        btnOpenDatePick = view.findViewById(R.id.btn_open_date_pick_dialog);
        btnOpenDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePickerDialog();
            }
        });

        return view;
    }

    private void getDatePickerDialog() {
        if (getActivity() == null) return;
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        getTimePickerDialog();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void getTimePickerDialog() {
        if (getActivity() == null) return;
        TimePickerDialog dialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//                        if (alarmManager != null) {
//                            alarmManager.set(AlarmManager.RTC_WAKEUP,
//                                    calendar.getTimeInMillis(),
//                                    PendingIntent.getActivity(BaseApplication.context, 0, new Intent(BaseApplication.context, AddActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
//                        }
                        setAlarmEvent(alarmManager, calendar);
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAlarmEvent(AlarmManager alarmManager, Calendar calendar) {
        if (alarmManager != null) {
            //using handler
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    TAG,
                    new AlarmManager.OnAlarmListener() {
                        @Override
                        public void onAlarm() {
                            Intent intent = new Intent(BaseApplication.context, AddActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            BaseApplication.context.startActivity(intent);
                        }
                    },
                    new Handler(Looper.getMainLooper()));

            // using PendingIntent
//            alarmManager.set(AlarmManager.RTC_WAKEUP,
//                    calendar.getTimeInMillis(),
//                    PendingIntent.getActivity(BaseApplication.context,
//                            0,
//                            new Intent(BaseApplication.context, AddActivity.class),
//                            PendingIntent.FLAG_UPDATE_CURRENT)
//            );
        }
    }
}