package com.googolfist.smartcontrolcenter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "MultiItemTypeAdapter";
    private final LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected ItemClickListener mItemClickListener;

    //private int mLayoutId;

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        super();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = delegate.getItemViewLayoutId();
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        //
        onViewHolderCreated(viewHolder, viewHolder.getConverView());
        //
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    public void onViewHolderCreated(ViewHolder viewHolder, View itemView) {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addViewDelegate(delegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int type, ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addViewDelegate(type, delegate);
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager())
            return super.getItemViewType(position);
        else
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.isUseItemViewDelegateManager();
    }

    public boolean isEnabled(int type) {
        return true;
    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setListener(final ViewGroup parent, final ViewHolder viewHolder, int itemType) {
        if (!isEnabled(itemType)) {
            return;
        }
        viewHolder.getConverView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConverView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                return mItemClickListener != null ?
                        mItemClickListener.onItemLongClick(v, viewHolder, position)
                        : false;
            }
        });
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public interface ItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }
}
