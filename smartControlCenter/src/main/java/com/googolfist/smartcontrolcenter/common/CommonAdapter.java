package com.googolfist.smartcontrolcenter.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected List<T> mDatas;
    private final LayoutInflater mInflater;
    private int mLayoutId;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        addItemViewDelegate(new ItemViewDelegate<T>() {

            @Override
            public int getItemViewLayoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder viewHolder, T item, int position) {
                CommonAdapter.this.convert(viewHolder, item, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T t, int position);
}
