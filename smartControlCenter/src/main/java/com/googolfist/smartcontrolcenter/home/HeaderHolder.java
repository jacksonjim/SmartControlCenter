package com.googolfist.smartcontrolcenter.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.googolfist.smartcontrolcenter.R;

/**
 * Created by Administrator on 2017/7/5.
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    private TextView mNameView;

    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        mNameView = (TextView) itemView.findViewById(R.id.header_holder);
    }

    public TextView getNameView() {
        return mNameView;
    }
}
