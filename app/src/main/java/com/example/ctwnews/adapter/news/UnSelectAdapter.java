package com.example.ctwnews.adapter.news;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ctwnews.R;
import com.example.ctwnews.bean.ItemBean;

import java.util.List;

public class UnSelectAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {

    private Context context;
    private boolean isEdit = false;
    private OnAction onAction;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public UnSelectAdapter(int layoutResId, @Nullable List<ItemBean> data, Context context, OnAction onAction) {
        super(layoutResId, data);
        this.context = context;
        this.onAction = onAction;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ItemBean item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.getView(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    onAction.onSelect(item.getName());
                }
            }
        });
    }

    public interface OnAction {
        void onSelect(String name);

    }
}
