package com.googolfist.smartcontrolcenter.devices;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.model.EquipModel;
import com.googolfist.smartcontrolcenter.model.EquipStatus;
import com.googolfist.smartcontrolcenter.services.ISmartHomeService;
import com.googolfist.smartcontrolcenter.services.SmartHomeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Administrator on 2017/6/9.
 */

public class ZoneListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String TAG = "ZoneListFragment";
    private ListView mZoneList;
    private ZoneListAdapter mZoneListAdapter;
    private boolean mIsTwoPage;
    private ServiceConnection connection;
    private ISmartHomeService mStartHomeService;
    private HashMap<String, EquipModel> mEquipList;

    public ZoneListFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initConnetction() {
        if (connection != null)
            return;
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mStartHomeService = ISmartHomeService.Stub.asInterface(service);
                try {
                    mEquipList = (HashMap<String, EquipModel>) mStartHomeService.getEquipList();
                    updateList(getActivity());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mStartHomeService = null;
            }
        };
    }

    private void bindSerivce(Context context) {
        Intent bindIntent = new Intent(context, SmartHomeService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
        bindIntent.putExtras(bundle);
        context.bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    private void unBindingService(Context context) {
        context.unbindService(connection);
        connection = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zone_list, container, false);
        mZoneList = (ListView) view.findViewById(R.id.zoneList);
        mZoneList.setAdapter(mZoneListAdapter);
        mZoneList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.right_list) != null) {
            mIsTwoPage = true;
        } else {
            mIsTwoPage = false;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            initData(activity);
        }
    }

    private void initData(Context context) {
        initConnetction();
        bindSerivce(context);
    }

    private void updateList(Context context) {
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        for (String key : mEquipList.keySet()) {
            EquipModel model = mEquipList.get(key);
            String name = "";
            String equipNo = "";
            int status = R.drawable.status_offline_24dp;
            if (model != null) {
                equipNo = model.equipNo;
                name = model.name;
                status = model.getStatus() == EquipStatus.DISCONNECTED ? status : R.drawable.status_online_24dp;
            }
            HashMap<String, Object> item = new HashMap<>();
            item.put("name", name);
            item.put("icon", 0xFF << 24 | (int) (Math.random() * 0xFFFFFF + 1));
            item.put("status", status);
            item.put("equipNo", equipNo);
            data.add(item);
        }
        mZoneListAdapter = new ZoneListAdapter(context, data, R.layout.zone_list_item, new String[]{"name", "icon", "status"}, new int[]{R.id.zone_name, R.id.zone_list_icon, R.id.equip_status_icon});
        mZoneList.setAdapter(mZoneListAdapter);
        mZoneListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initData(context);
    }

    @Override
    public void onDetach() {
        unBindingService(getActivity());
        if (mEquipList != null) {
            mEquipList.clear();
        }
        mEquipList = null;
        mStartHomeService = null;
        mZoneListAdapter = null;
        if (mZoneList != null) {
            mZoneList.removeAllViewsInLayout();
        }
        mZoneList = null;
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
        if (mIsTwoPage) {
            Fragment fragment = new DeviceListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("equipNo", (String) map.get("equipNo"));
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.right_list, fragment).commit();
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DeviceListActivity.class);
            intent.putExtra("equipNo", (String) map.get("equipNo"));
            startActivity(intent);
        }
    }
}
