package com.googolfist.smartcontrolcenter.common;

import android.support.v4.util.SparseArrayCompat;
import android.util.SparseArray;

/**
 * Created by Administrator on 2017/7/13.
 */

public class ItemViewDelegateManager<T> {
    SparseArrayCompat<ItemViewDelegate<T>> mDelegates;
    private int mItemCount;

    public ItemViewDelegateManager() {
        super();
        mDelegates = new SparseArrayCompat<>();
        mItemCount = 0;
    }

    public int getItemViewDelegateCount() {
        return mItemCount;
    }

    public boolean isUseItemViewDelegateManager() {
        return mItemCount > 0;
    }

    public void addViewDelegate(ItemViewDelegate<T> viewDelegate) {
        int type = mDelegates.size();
        if (viewDelegate != null) {
            mDelegates.put(type, viewDelegate);
            type++;
            mItemCount++;
        }
    }

    public void addViewDelegate(int type, ItemViewDelegate<T> viewDelegate) {
        ItemViewDelegate delegate = mDelegates.get(type);
        if (delegate == null) {
            mDelegates.put(type, viewDelegate);
            mItemCount++;
        }
    }

    public void removeViewDelegate(ItemViewDelegate<T> viewDelegate) {
        if (viewDelegate != null) {
            int key = mDelegates.indexOfValue(viewDelegate);
            removeViewDelegate(key);
        } else {
            throw new NullPointerException("viewDelegate is Null");
        }
    }

    public void removeViewDelegate(int type) {
        int key = mDelegates.indexOfKey(type);
        if (key >= 0) {
            mDelegates.removeAt(key);
            mItemCount--;
            if (mItemCount <= 0) {
                mItemCount = 0;
            }
        }
    }

    public ItemViewDelegate<T> getItemViewDelegate(int type) {
        return mDelegates.get(type);
    }

    public int getItemLayoutId(int type) {
        return getItemViewDelegate(type).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewDelegate delegate) {
        return mDelegates.indexOfValue(delegate);
    }

    public int getItemViewType(T itemView, int position) {
        for (int i = 0; i < mItemCount; i++) {
            ItemViewDelegate<T> item = mDelegates.valueAt(i);
            if (item.isForViewType(itemView, position)) {
                return mDelegates.keyAt(i);
            }
        }

        return -1;
    }

    public void convert(ViewHolder viewHolder, T item, int position) {
        for (int i = 0; i < mItemCount; i++) {
            ItemViewDelegate<T> delegate = mDelegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                delegate.convert(viewHolder, item, position);
                return;
            }
        }
    }

}
