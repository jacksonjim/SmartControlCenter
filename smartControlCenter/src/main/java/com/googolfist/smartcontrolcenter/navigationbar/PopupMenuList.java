package com.googolfist.smartcontrolcenter.navigationbar;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.googolfist.smartcontrolcenter.R;


/**
 * Created by Administrator on 2017/7/19.
 */

public class PopupMenuList extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "PopupMenuList";
    private View mContextView;
    private TextView mAddDevice;
    private TextView mAddScene;
    private TextView mAddZone;
    private PopupMenuOnClickListener mListener;

    public PopupMenuList(final Activity context, final PopupMenuOnClickListener listener) {
        super(context);
        mListener = listener;
        LayoutInflater inflater = LayoutInflater.from(context);
        mContextView = inflater.inflate(R.layout.popup_menu, null);
        Point outSize = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(outSize);
        setContentView(mContextView);
        //setWidth(outSize.x / 2 + 40);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        ColorDrawable colorDrawable = new ColorDrawable(0xFF000000);
        this.setBackgroundDrawable(colorDrawable);
        setAnimationStyle(R.style.AnimationPreview);
        mAddScene = (TextView) mContextView.findViewById(R.id.popup_add_scene);
        mAddDevice = (TextView) mContextView.findViewById(R.id.popup_add_device);
        mAddZone = (TextView) mContextView.findViewById(R.id.popup_add_zone);
        initViewListener();

    }

    private void initViewListener() {
        mAddDevice.setOnClickListener(this);
        mAddScene.setOnClickListener(this);
        mAddZone.setOnClickListener(this);
    }

    public void showPopupWindow(View parent) {
        if (!isShowing()) {
            final int offsetX = parent.getWidth() - getWidth();
            showAsDropDown(parent, offsetX, 0);
        } else {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        mAddScene = null;
        mAddDevice = null;
        mAddZone = null;
        mContextView = null;
        super.dismiss();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        int id = v.getId();
        if (mListener != null)
            mListener.onClick(id);
        /*if(id == R.id.popup_add_scene) {

        }

        if (id == R.id.popup_add_device) {

        }

        if (id == R.id.popup_add_zone) {

        }*/


    }

    public interface PopupMenuOnClickListener {
        void onClick(int rsId);
    }
}
