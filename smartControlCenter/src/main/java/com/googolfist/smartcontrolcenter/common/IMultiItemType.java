package com.googolfist.smartcontrolcenter.common;

/**
 * Created by Administrator on 2017/7/5.
 */

public interface IMultiItemType<T> {
    int getLayoutId(int type);

    int getItemViewType(int position, T t);
}
