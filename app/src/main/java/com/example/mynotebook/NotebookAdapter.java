package com.example.mynotebook;

/**
 * @author zhuxiaomei
 * email:  zhuxiaomei.meleny@bytedance.com
 * date:   2020/7/2
 */
public interface NotebookAdapter {
    void onChanged();

    void onInserted(int pos);

    void onDeleted(int pos);
}
