package com.googolfist.smartcontrolcenter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googolfist.smartcontrolcenter.R;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */

public class RoomAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DeviceHolder, RecyclerView.ViewHolder> implements DeviceHolder.HolderCallbak {
    private static final String TAG = "RoomAdapter";
    private final LayoutInflater mInflater;
    private Context mContext;
    private List<DeviceTypeEntity> mDeviceTypeEntities;
    private DeviceOnItemClickListener mItemOnClickListener;

    public RoomAdapter(Context context) {
        super();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setDeviceTypeEntities(List<DeviceTypeEntity> entities) {
        if (entities != null) {
            mDeviceTypeEntities = entities;
            notifyDataSetChanged();
        }
    }

    @Override
    protected int getSectionCount() {
        int size = 0;
        if (mDeviceTypeEntities != null) {
            size = mDeviceTypeEntities.size();
        }
        return size;
    }

    @Override
    protected int getItemCountForSection(int section) {
        DeviceTypeEntity deviceTypeEntity = mDeviceTypeEntities.get(section);
        int count = 0;
        if (deviceTypeEntity != null) {
            count = deviceTypeEntity.getDeivecEntitis().size();
        }
        return count;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup viewGroup, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.header_holder, viewGroup, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    protected DeviceHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {
        return new DeviceHolder(mContext, mInflater.inflate(R.layout.device_entity, viewGroup, false), viewGroup);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderHolder headerHolder, int section) {
        headerHolder.getNameView().setText(mDeviceTypeEntities.get(section).getTypeName());
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder viewHolder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(final DeviceHolder deviceHolder, int section, int position) {
        final DeviceEntity deviceEntity = mDeviceTypeEntities.get(section).getDeivecEntitis().get(position);
        deviceHolder.setName(deviceEntity.getName());
        Log.d(TAG, "onBindItemViewHolder: " + deviceEntity.getName() + "-" + deviceEntity.isOpened());
        deviceHolder.setSwitchChecked(deviceEntity.isOpened());
        deviceHolder.setCallbak(this);
        deviceHolder.getConverView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemOnClickListener != null) {
                    int pos = deviceHolder.getAdapterPosition();
                    mItemOnClickListener.onItemClick(v, deviceHolder, pos, deviceEntity);
                }
            }
        });
    }

    @Override
    public void call(String name, boolean isOpened) {
        int size = mDeviceTypeEntities.size();
        for (int i = 0; i < size; i++) {
            List<DeviceEntity> deviceEntities = mDeviceTypeEntities.get(i).getDeivecEntitis();
            int count = deviceEntities.size();
            for (int j = 0; j < count; j++) {
                DeviceEntity dv = deviceEntities.get(j);
                if (dv.getName().equals(name)) {
                    dv.setOpened(isOpened);
                    break;
                }
            }
        }
    }

    public void setItemOnClickListener(DeviceOnItemClickListener itemOnClickListener) {
        mItemOnClickListener = itemOnClickListener;
    }
}
