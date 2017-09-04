package com.googolfist.smartcontrolcenter.common;

/**
 * Created by Administrator on 2017/7/13.
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder viewHolder, T item, int position);
}
