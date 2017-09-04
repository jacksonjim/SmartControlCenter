package com.googolfist.smartcontrolcenter.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ViewHolder";
    private SparseArray<View> mViews;
    private Context mContext;
    private View mConverView;


    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConverView = itemView;
        mViews = new SparseArray();
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context, View itemView) {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    /**
     * @param id resource id
     * @return View
     */
    public <T extends View> T getViewById(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mConverView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public View getConverView() {
        return mConverView;
    }

    public ViewHolder setText(int id, CharSequence text) {
        TextView textView = getViewById(id);
        textView.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int id, int resId) {
        ImageView imageView = getViewById(id);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int id, Bitmap bitmap) {
        ImageView imageView = getViewById(id);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int id, Drawable drawable) {
        ImageView view = getViewById(id);
        view.setImageDrawable(drawable);
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int id, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getViewById(id).setAlpha(value);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(value, value);
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            getViewById(id).startAnimation(alphaAnimation);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link @View.VISIBLE}, {@link #@View.INVISIBLE}, or {@link #@View.GONE}.
     * @attr ref android.R.styleable#View_visibility
     */
    public ViewHolder setVisibility(int id, int visibility) {
        getViewById(id).setVisibility(visibility);
        return this;
    }

    // item view Listener

    public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
        View view = getViewById(id);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int id, View.OnTouchListener listener) {
        View view = getViewById(id);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongPressListener(int id, View.OnLongClickListener listener) {
        View view = getViewById(id);
        view.setOnLongClickListener(listener);
        return this;
    }

}
