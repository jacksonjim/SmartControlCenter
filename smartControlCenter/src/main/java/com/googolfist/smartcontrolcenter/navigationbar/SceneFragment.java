package com.googolfist.smartcontrolcenter.navigationbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.common.CommonAdapter;
import com.googolfist.smartcontrolcenter.common.DividerGridDecoration;
import com.googolfist.smartcontrolcenter.common.DividerItemDecoration;
import com.googolfist.smartcontrolcenter.common.MultiItemTypeAdapter;
import com.googolfist.smartcontrolcenter.common.SceneLayoutManager;
import com.googolfist.smartcontrolcenter.common.ViewHolder;
import com.googolfist.smartcontrolcenter.scene.SceneAdapta;
import com.googolfist.smartcontrolcenter.scene.SceneViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SceneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SceneFragment extends Fragment implements MultiItemTypeAdapter.ItemClickListener {
    private static final String TAG = "SceneFragment";
    private RecyclerView mSceneView;

    public SceneFragment() {
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
    public static SceneFragment newInstance(String param1, String param2) {
        SceneFragment fragment = new SceneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scene, container, false);
        initSceneView(view);
        return view;
    }

    private void initSceneView(final View view) {

        mSceneView = (RecyclerView) view.findViewById(R.id.scene_list);
        List<Map<String, Object>> testdata = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap();
            map.put("name", "Test Scene" + i);
            map.put("icon", i % 3 == 0 ? R.drawable.icon1 : R.drawable.icon2);
            testdata.add(map);
        }
        CommonAdapter sceneAdapta = new CommonAdapter(getContext(), R.layout.scene_item, testdata) {
            @Override
            protected void convert(ViewHolder viewHolder, Object o, int position) {
                Map<String, Object> map = (Map<String, Object>) o;
                viewHolder.setText(R.id.scene_name, (CharSequence) map.get("name"));
                viewHolder.setImageResource(R.id.scene_icon, (Integer) map.get("icon"));
            }

        };
        mSceneView.setLayoutManager(new SceneLayoutManager(getContext(), 4));
        mSceneView.setAdapter(sceneAdapta);

        mSceneView.addItemDecoration(new DividerGridDecoration(getContext()));
        sceneAdapta.setOnItemClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
        Log.d(TAG, "onItemClick: " + position);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
        Log.d(TAG, "onItemLongClick: " + position);
        return false;
    }
}
