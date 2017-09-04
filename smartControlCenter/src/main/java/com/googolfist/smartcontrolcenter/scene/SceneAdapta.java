package com.googolfist.smartcontrolcenter.scene;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.googolfist.smartcontrolcenter.common.MultiItemTypeAdapter;
import com.googolfist.smartcontrolcenter.common.ViewHolder;
import com.truizlop.sectionedrecyclerview.HeaderViewHolder;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class SceneAdapta extends MultiItemTypeAdapter<SceneViewHolder> {
    private static final String TAG = "RoomAdapter";
    // private final LayoutInflater mInflater;
    private Context mContext;
    private List<SceneTypeEntity> mSceneTypeEntities;


    public SceneAdapta(Context context, List<SceneViewHolder> datas) {
        super(context, datas);
        mContext = context;
    }
}
