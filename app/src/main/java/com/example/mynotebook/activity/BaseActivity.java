package com.example.mynotebook.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mynotebook.R;
import com.example.mynotebook.fragment.AllMemoFragment;
import com.example.mynotebook.fragment.AllReminderFragment;
import com.example.mynotebook.widget.BottomNavigateItem;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vpFragmentContainer;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>() {{
        add(new AllMemoFragment());
        add(new AllReminderFragment());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        vpFragmentContainer = findViewById(R.id.vp_fragment_container);

        vpFragmentContainer.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        vpFragmentContainer.addOnPageChangeListener(this);
        LinearLayout bottom = findViewById(R.id.bottom);

        BottomNavigateItem item1 = new BottomNavigateItem(this, R.drawable.ic_list, "list");
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpFragmentContainer.setCurrentItem(0);
            }
        });
        item1.addToLinearLayout(bottom);

        BottomNavigateItem item2 = new BottomNavigateItem(this, R.drawable.ic_alarm, "alarm");
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpFragmentContainer.setCurrentItem(1);
            }
        });
        item2.addToLinearLayout(bottom);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}