package com.example.mynotebook.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mynotebook.R;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/30
 */
// TODO: 2020/7/30 建造者模式
public class BottomNavigateItem extends RelativeLayout {

    ImageView ivIcon;
    TextView tvDescription;

    public BottomNavigateItem(Context context) {
        super(context);
        init();
    }

    public BottomNavigateItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.bottom_navigate_item, this, true);

        ivIcon = findViewById(R.id.iv_icon);
        tvDescription = findViewById(R.id.tv_description);
    }

    public void setIcon(int drawableId) {
        ivIcon.setBackgroundResource(drawableId);
    }

    public void setDescription(String description) {
        tvDescription.setText(description);
    }
}
