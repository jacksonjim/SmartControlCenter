package com.googolfist.smartcontrolcenter.navigationbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class MainAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainAdapter";
    private List<Fragment> mFragments;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        initFrame();
    }

    public void initFrame() {
        HomeFragment home = new HomeFragment();
        SceneFragment scene = new SceneFragment();
        VoiceFragment voice = new VoiceFragment();
        DeviceStatusFragment status = new DeviceStatusFragment();
        PersonalFragment personal = new PersonalFragment();
        mFragments.add(home);
        mFragments.add(scene);
        mFragments.add(voice);
        mFragments.add(status);
        mFragments.add(personal);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
