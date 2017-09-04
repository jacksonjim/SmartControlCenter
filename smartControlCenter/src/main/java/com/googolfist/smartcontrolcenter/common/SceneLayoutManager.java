package com.googolfist.smartcontrolcenter.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/7/17.
 */

public class SceneLayoutManager extends GridLayoutManager {

    private static final String TAG = "SceneLayoutManager";

    public SceneLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        /*int count = state != null ? state.getItemCount() : 0;
        Log.d(TAG, "onMeasure: " + state.getItemCount());
        if (count > 0) {
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                measureChild(view, widthSpec, heightSpec);
                int measureWidth = View.MeasureSpec.getSize(widthSpec);
                int measureHeight = view.getMeasuredHeight();
                setMeasuredDimension(measureWidth, measureHeight);
            }
        }*/
    }

}
