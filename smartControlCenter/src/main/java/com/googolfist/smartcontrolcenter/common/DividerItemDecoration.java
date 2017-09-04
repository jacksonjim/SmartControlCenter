package com.googolfist.smartcontrolcenter.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.googolfist.smartcontrolcenter.R;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "DividerItemDecoration";
    private final Context mContext;
    private final Drawable mDivider;
    private int mOrientation;
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public DividerItemDecoration(Context context, int orientation) {
        super();
        mContext = context;
        mOrientation = orientation;
        final TypedArray typedArray = mContext.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        Log.d(TAG, "DividerItemDecoration: " + mDivider);
        typedArray.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation == HORIZONTAL || orientation == VERTICAL) {
            mOrientation = orientation;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == HORIZONTAL) {
            drawVerticalLine(c, parent, state);
        } else if (mOrientation == VERTICAL) {
            drawHorzontalLine(c, parent, state);
        }
    }

    private void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            //Log.d(TAG, "drawHorzontalLine: " + parent + ".>>>" + child);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() + layoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            // Log.d(TAG, "drawVerticalLine: " + left + "-" + top + "-" +  right + "-" + bottom);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorzontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == HORIZONTAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
