package com.googolfist.smartcontrolcenter.scene;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.googolfist.smartcontrolcenter.R;
import com.googolfist.smartcontrolcenter.common.CommonAdapter;
import com.googolfist.smartcontrolcenter.common.DividerItemDecoration;
import com.googolfist.smartcontrolcenter.common.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditSceneActivity extends AppCompatActivity {
    private static final String TAG = "EditSceneActivity";
    private Toolbar mToolbar;
    private EditText mSceneNameView;
    private ImageView mSceneIconView;
    private RecyclerView mSceneDeviceList;
    private Button mAddDeviceButton;

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.scene_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "onOptionsItemSelected: back");
            finish();
        }

        if (item.getItemId() == R.id.scene_edit_finish) {
            Log.d(TAG, "onOptionsItemSelected: finish");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_scene);
        mToolbar = (Toolbar) findViewById(R.id.scene_toolbar);
        mToolbar.setTitle("Edit Scene Mode");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mSceneNameView = (EditText) findViewById(R.id.scene_name_editor);
        mSceneIconView = (ImageView) findViewById(R.id.scene_editor_icon);
        mSceneDeviceList = (RecyclerView) findViewById(R.id.scene_device_list);
        mAddDeviceButton = (Button) findViewById(R.id.add_device_to_scene);

        mSceneNameView.setText(intent.getStringExtra("name"));

        mSceneIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Select Icon for Scene", Toast.LENGTH_SHORT).show();
            }
        });

        mAddDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "add Device to List", Toast.LENGTH_SHORT).show();
            }
        });

        mSceneDeviceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList data = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "Device" + i);
            map.put("location", "room" + i % 3);
            data.add(map);
        }

        mSceneDeviceList.setAdapter(new CommonAdapter(this, R.layout.scene_device_item, data) {
            @Override
            protected void convert(ViewHolder viewHolder, Object o, int position) {
                Map<String, Object> map = (Map<String, Object>) o;
                viewHolder.setText(R.id.device_name, (CharSequence) map.get("name"));
                viewHolder.setText(R.id.location_name, (CharSequence) map.get("location"));
            }
        });

        mSceneDeviceList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
