package com.example.mynotebook.widget;

import android.content.Context;
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

    // children widget
    ImageView ivIcon;
    TextView tvDescription;

    // children attr
    int icon;
    String description;

    public BottomNavigateItem(Context context, int icon, String description) {
        super(context);
        this.icon = icon;
        this.description = description;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.bottom_navigate_item, this, true);

        ivIcon = findViewById(R.id.iv_icon);
        ivIcon.setBackgroundResource(icon);
        
        tvDescription = findViewById(R.id.tv_description);
        tvDescription.setText(description);
    }

    public void setColor(int color) {
        // TODO: 2020/7/30
    }
}
