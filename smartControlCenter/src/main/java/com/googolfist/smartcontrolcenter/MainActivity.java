package com.googolfist.smartcontrolcenter;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.QRCodeScanActivity;
import com.googolfist.smartcontrolcenter.navigationbar.BottomNavigationViewEx;
import com.googolfist.smartcontrolcenter.navigationbar.MainAdapter;
import com.googolfist.smartcontrolcenter.navigationbar.PopupMenuList;
import com.googolfist.smartcontrolcenter.scene.EditSceneActivity;
import com.googolfist.smartcontrolcenter.services.ISmartHomeService;
import com.googolfist.smartcontrolcenter.services.SmartHomeService;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements PopupMenuList.PopupMenuOnClickListener {

    private static final String TAG = "MainActivity";
    private TextView mTextMessage;
    private Button mConnetct;
    private ISmartHomeService mStartHomeServiceADL;
    private Intent bindIntent;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mStartHomeServiceADL = ISmartHomeService.Stub.asInterface(service);
           /* try {
               *//* List<String> list = mStartHomeServiceADL.getEquipNoList();
                Log.d(TAG, "onServiceConnected: " + list.toArray());*//*
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mStartHomeServiceADL = null;
        }
    };
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private ViewPager mViewPager;
    private BottomNavigationViewEx mNavigation;
    private Toolbar mToolbar;

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            showMenuItem(item.getItemId());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_scene_model:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_voice:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_device_status:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_personal:
                    mViewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }

    };

    private void showMenuItem(int itemId) {
        boolean showScene = itemId == R.id.navigation_scene_model || itemId == R.id.navigation_home;
        MenuItem menu = mToolbar.getMenu().findItem(R.id.toolbar_add);
        menu.setVisible(showScene);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*new Thread(){
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "run: +++++++++++++++++++++++++++++ +");
                new TestJni().testPrint();
            }
        }.start();*/
        if (item.getItemId() == R.id.messages) {
            Toast.makeText(this, "Open Message Box", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.toolbar_add) {
            Toast.makeText(this, "popup add action", Toast.LENGTH_SHORT).show();

            PopupMenuList menuList = new PopupMenuList(this, this);
            menuList.showPopupWindow(findViewById(R.id.toolbar));

        }
        return super.onOptionsItemSelected(item);
    }


    private void initListener() {
        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(TAG, "onPageScrolled: =>" + position + "->" + positionOffset + "+>" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                //Log.d(TAG, "onPageSelected: " + position);
                mNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d(TAG, "onPageScrollStateChanged: " + state);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        }
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        //toolbar.setTitleTextAppearance(getApplicationContext(), R.style.toolbar);
        setSupportActionBar(mToolbar);
        initListener();
        //testEncode();
        new Thread() {
            @Override
            public void run() {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, SmartHomeService.class);
                startService(intent1);
            }
        }.start();

        bindIntent = new Intent(this, SmartHomeService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", true);
        bindIntent.putExtras(bundle);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //Log.d(TAG, "onCreate: "+ mViewPager + "\n" + findViewById(R.id.viewpager));
        mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        // DevicesModel listData = new DevicesModel();

        /*mTextMessage = (TextView) findViewById(R.id.textView);
        mConnetct = (Button) findViewById(R.id.button);

        mConnetct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // webServices.setUpdates("2003", "84:86:f3:00:23:9a", "slight_" + ((int)(Math.random() * 4) + 1), "0#255", "admin");;
                Intent intent = new Intent();
                //intent.setClass(MainActivity.this, DevicePanelActivity.class);
                //intent.setClass(MainActivity.this, VideoActivity.class);
                intent.setClass(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);


            }
        });*/

        mNavigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        mNavigation.enableItemShiftingMode(false);
        mNavigation.enableAnimation(false);
        mNavigation.enableShiftingMode(false);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void testEncode() {
        Security.addProvider(new BouncyCastleProvider());
        //Base64UrlSafe(AES(appkey , 序列号))
        try {
            final String CIPHER_ALGORITHM_ECB_7 = "AES/ECB/PKCS7Padding";
            final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
            final String KEY_ALGORITHM = "AES";
            //ASE:fqGMyQOmkv9nccLvulfBRA
            String appkey = "295654fb3d2944a48782d71bb4a66b1f";
            //appkey = "657836629";
            KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
            keygen.init(256);
            byte[] code = "PUCBLE".getBytes("utf-8");
            //Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB_7, BouncyCastleProvider.PROVIDER_NAME);
            SecretKey secretKey = new SecretKeySpec(appkey.getBytes("UTF-8"), "AES");
            Log.d(TAG, "testEncode--==-: " + keygen.generateKey().getEncoded() + ",--=>" + secretKey.getEncoded());
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] bytes = cipher.doFinal(code);

            Log.d(TAG, "testEncode->: " + bytes);
            String rst = Base64.encodeToString(bytes, Base64.URL_SAFE | Base64.NO_PADDING);
            Log.d(TAG, "testEncode:->> " + rst);

            String rstkey = "3239353635346662336432393434613438373832643731626234613636623166";
            //secretKey = new SecretKeySpec(appkey.getBytes("UTF-8"), "AES");
            //cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB_7);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥
            byte[] original = cipher.doFinal(Base64.decode("fqGMyQOmkv9nccLvulfBRA", Base64.URL_SAFE | Base64.NO_PADDING));
            Log.d(TAG, "testEncode->decode: " + new String(original));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int rsId) {
        if (rsId == R.id.popup_add_scene) {
            Intent intent = new Intent(MainActivity.this, EditSceneActivity.class);
            intent.putExtra("action", "edit");
            intent.putExtra("name", "abc");
            startActivity(intent);
        }

        if (rsId == R.id.popup_add_device) {
            Intent intent = new Intent(MainActivity.this, QRCodeScanActivity.class);
            //Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivity(intent);
        }

        if (rsId == R.id.popup_add_zone) {
            showAddZoneDialog();
        }
    }

    private void showAddZoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add the zone or room");
        View zoneAddDialog = LayoutInflater.from(this).inflate(R.layout.zone_add_dialog, null);
        final TextInputLayout contentWrapper = (TextInputLayout) zoneAddDialog.findViewById(R.id.zoneContentWrapper);
        contentWrapper.setHint(getString(R.string.zone_name));
        final EditText content = (EditText) zoneAddDialog.findViewById(R.id.content_zone);
        builder.setView(zoneAddDialog);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, content.getText() + " Zone is created", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}
