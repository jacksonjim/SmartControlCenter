package com.googolfist.smartcontrolcenter.devices;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.googolfist.smartcontrolcenter.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16.
 */

public class ZoneListAdapter extends SimpleAdapter {
    private static final String TAG = "ZoneListAdapter";
    private List<? extends Map<String, ?>> mData;
    private LayoutInflater mInflater;
    private int mResource;
    private int[] mTo;
    private String[] mFrom;

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
    public ZoneListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
        mTo = to;
        mFrom = from;
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
            itemView.icon = (ImageView) convertView.findViewById(R.id.zone_list_icon);
            itemView.name = (TextView) convertView.findViewById(R.id.zone_name);
            itemView.status = (ImageView) convertView.findViewById(R.id.equip_status_icon);
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
            itemView.icon.setBackgroundColor((int) data.get("icon"));
            itemView.name.setText((String) data.get("name"));
            itemView.status.setImageResource((int) data.get("status"));
        }
    }

    class ListItemView {
        ImageView icon;
        TextView name;
        ImageView status;
    }
}
