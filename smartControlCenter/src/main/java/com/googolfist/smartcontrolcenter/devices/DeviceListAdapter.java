package com.googolfist.smartcontrolcenter.devices;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.services.ISmartHomeService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 */

class DeviceListAdapter extends SimpleAdapter {

    private static final String TAG = "DeviceListAdapter";
    private List<? extends Map<String, ?>> mData;
    private LayoutInflater mInflater;
    private int mResource;
    private ISmartHomeService mStartHomeService;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public DeviceListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(mInflater, position, convertView, parent, mResource);
    }

    private View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent, int resource) {
        ListItemView itemView;
        if (convertView == null) {
            itemView = new ListItemView();
            convertView = inflater.inflate(resource, parent, false);
            itemView.imageButton = (ImageButton) convertView.findViewById(R.id.device_icon);
            itemView.nameView = (TextView) convertView.findViewById(R.id.device_name);
            itemView.lightSwitchBtn = (ToggleButton) convertView.findViewById(R.id.device_switch_button);
            itemView.status = (TextView) convertView.findViewById(R.id.device_status);
            convertView.setTag(itemView);
        } else {
            itemView = (ListItemView) convertView.getTag();
        }

        buildView(position, itemView);

        return convertView;
    }

    private void buildView(int position, final ListItemView itemView) {
        final Map data = mData.get(position);
        if (data != null) {
            itemView.imageButton.setImageResource((int) data.get("image"));
            itemView.nameView.setText((String) data.get("name"));
            itemView.lightSwitchBtn.setChecked((boolean) data.get("switch"));
            itemView.status.setText((String) data.get("status"));
            /*itemView.lightSwitchBtn.setEnabled(status);
            itemView.imageButton.setEnabled(status);
            itemView.nameView.setEnabled(status);*/

            itemView.lightSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        mStartHomeService.execuEquipSet((String) data.get("equipNo"), (String) data.get("setNo"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setStartHomeService(ISmartHomeService StartHomeService) {
        this.mStartHomeService = StartHomeService;
    }



   /* private void bindView(int position, View view) {
        final Map dataSet = mDatas.get(position);
        if (dataSet == null) {
            return;
        }

        final ViewBinder binder = mViewBinder;
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }

                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }

                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " +
                                    (data == null ? "<unknown type>" : data.getClass()));
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }*/

    class ListItemView {
        ImageButton imageButton;
        TextView nameView;
        ToggleButton lightSwitchBtn;
        TextView status;
    }
}
