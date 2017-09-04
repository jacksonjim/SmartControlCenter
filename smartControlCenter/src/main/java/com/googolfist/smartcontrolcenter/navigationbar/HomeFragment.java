package com.googolfist.smartcontrolcenter.navigationbar;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.common.DividerItemDecoration;
import com.googolfist.smartcontrolcenter.common.ViewHolder;
import com.googolfist.smartcontrolcenter.deviceControlUI.airConditioner.AirConditionerCtrlActivity;
import com.googolfist.smartcontrolcenter.deviceControlUI.curtain.CurtainCtrlActivity;
import com.googolfist.smartcontrolcenter.deviceControlUI.door.DoorCtrlActivity;
import com.googolfist.smartcontrolcenter.deviceControlUI.lights.LightCtrlActivity;
import com.googolfist.smartcontrolcenter.deviceControlUI.window.WindowCtrlActivity;
import com.googolfist.smartcontrolcenter.home.DeviceEntity;
import com.googolfist.smartcontrolcenter.home.DeviceOnItemClickListener;
import com.googolfist.smartcontrolcenter.home.DeviceTypeEntity;
import com.googolfist.smartcontrolcenter.home.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements DeviceOnItemClickListener {
    private static final String TAG = "HomeFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RadioGroup mRadioGroup;
    private RecyclerView mRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void iniTabs(LayoutInflater inflater, View view) {
        //Log.d(TAG, "iniTabs: "+ LayoutInflater.from(getContext()).inflate(R.layout.tab_radiobutton, null));

        for (int i = 0; i < 10; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.tab_radiobutton, null);
            radioButton.setId(i);
            radioButton.setText("房间" + i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            mRadioGroup.addView(radioButton, params);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.tabs);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.devicesByRoom);
        iniTabs(inflater, view);
        initDeviceForRoom();
        return view;
    }

    private void initDeviceForRoom() {
        RoomAdapter roomAdapter = new RoomAdapter(getContext());
        List<DeviceTypeEntity> entities = new ArrayList<>();
        String[] deviceTypeNames = getResources().getStringArray(R.array.device_type_name);
        int[] deviceTypeIds = getResources().getIntArray(R.array.device_type_id);
        if (deviceTypeIds.length != deviceTypeNames.length) {
            throw new Error("device type ids's length or name's length isn't equals");
        }
        int len = deviceTypeNames.length;
        for (int i = 0; i < len; i++) {
            DeviceTypeEntity deviceTypeEntity = new DeviceTypeEntity();
            List<DeviceEntity> deviceEntities = new ArrayList<>();
            String typeName = deviceTypeNames[i];
            int typeId = deviceTypeIds[i];
            int count = (int) (1 + Math.random() * 6);
            for (int j = 0; j < count; j++) {
                DeviceEntity deviceEntity = new DeviceEntity();
                deviceEntity.setDeviceID(j);
                deviceEntity.setTypeId(typeId);
                deviceEntity.setName(typeName + j);
                deviceEntity.setOpened(j % 2 == 0);
                deviceEntities.add(deviceEntity);
            }
            deviceTypeEntity.setTypeName(typeName);
            deviceTypeEntity.setTypeID(typeId);
            deviceTypeEntity.setDeivecEntitis(deviceEntities);
            entities.add(deviceTypeEntity);
        }
        mRecyclerView.setAdapter(roomAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter.setDeviceTypeEntities(entities);
        roomAdapter.setItemOnClickListener(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View v, ViewHolder holder, int position, Object itemData) {
        DeviceEntity device = (DeviceEntity) itemData;
        if (device != null) {
            Intent intent = new Intent();
            switch (device.getTypeId()) {
                case 0: //Light
                    intent.setClass(getActivity(), LightCtrlActivity.class);
                    break;
                case 1: //Window
                    intent.setClass(getActivity(), WindowCtrlActivity.class);
                    break;
                case 2: //Curtain
                    intent.setClass(getActivity(), CurtainCtrlActivity.class);
                    break;
                case 3: //Door
                    intent.setClass(getActivity(), DoorCtrlActivity.class);
                    break;
                case 4: //Air Conditioner
                    intent.setClass(getActivity(), AirConditionerCtrlActivity.class);
                    break;
            }
            ActivityCompat.startActivity(getContext(),intent,  ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, "content").toBundle());
            //startActivity(intent,);
        }
    }
}
