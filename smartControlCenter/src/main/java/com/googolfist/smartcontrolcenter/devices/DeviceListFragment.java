package com.googolfist.smartcontrolcenter.devices;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.model.DeviceSetItem;
import com.googolfist.smartcontrolcenter.model.DeviceYxpItem;
import com.googolfist.smartcontrolcenter.model.ItemData;
import com.googolfist.smartcontrolcenter.services.SmartHomeService;
import com.googolfist.smartcontrolcenter.services.ISmartHomeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DeviceListFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String TAG = "DeviceListFragment";
    private GridView mDeviceList;
    private DeviceListAdapter mSimpleAdapter;
    private Bundle mBundle;
    private ServiceConnection mConnection;
    private ISmartHomeService mSmartHomeService;
    private List<ItemData> mDevicesStatus;
    private List mDevicesSet;


    public DeviceListFragment() {

    }

    private void connectionSevice() {
        if (mConnection != null)
            return;
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mSmartHomeService = ISmartHomeService.Stub.asInterface(service);
                try {
                    mDevicesStatus = mSmartHomeService.getYxpList(getEquipNo());
                    mDevicesSet = mSmartHomeService.getSetsList(getEquipNo());
                    updateList(getActivity());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void updateList(Context context) {
        if (mDevicesStatus == null) {
            return;
        }
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        int[] ids = new int[]{R.drawable.icon1, R.drawable.icon2, R.drawable.icon3};
        final int size = mDevicesSet.size();
        for (int i = 0; i < size; i++) {
            HashMap<String, Object> item = new HashMap<>();
            DeviceSetItem setItem = (DeviceSetItem) mDevicesSet.get(i);
            item.put("image", ids[(int) (Math.random() * 3)]);
            item.put("name", setItem.set_nm);
            item.put("switch", i % 2 == 0);
            item.put("status", setItem.set_type);
            item.put("equipNo", setItem.equip_no);
            item.put("setNo", setItem.set_no);
            data.add(item);
        }
        mSimpleAdapter = new DeviceListAdapter(context, data, R.layout.device_list_item,
                new String[]{"image", "name", "switch", "status"}, new int[]{R.id.device_icon, R.id.device_name, R.id.device_switch_button});
        mSimpleAdapter.setStartHomeService(mSmartHomeService);
        mDeviceList.setAdapter(mSimpleAdapter);
        mSimpleAdapter.notifyDataSetChanged();
    }

    private String getEquipNo() {
        String equipNo = "";
        if (mBundle != null) {
            equipNo = mBundle.getString("equipNo");
        }
        return equipNo;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick: " + position + ">>" + id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initData(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            initData(activity);
        }
    }


    private void initData(Context context) {
        connectionSevice();
        bindSerivce(context);
    }

    private void bindSerivce(Context context) {
        Intent intent = new Intent(context, SmartHomeService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
        intent.putExtras(bundle);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        mDeviceList = (GridView) view.findViewById(R.id.deviceList);
        mDeviceList.setAdapter(mSimpleAdapter);
        mDeviceList.setOnItemClickListener(this);

        mBundle = getArguments();
        if (mBundle == null)
            mBundle = getActivity().getIntent().getExtras();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Context context = getActivity();
        if (context != null)
            context.unbindService(mConnection);
        super.onDetach();
    }


}
