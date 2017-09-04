package com.googolfist.smartcontrolcenter.cctv;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.googolfist.smartcontrolcenter.R;

import java.util.Calendar;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    private static final String CAM_SN = "657836629";
    private static final String CODE = "PUCBLE";
    private static final String TOKEN = "at.5h3hehpx35l4wmta87aosbsd4g0mnwic-1e5nvshetq-1kqeb0r-vyq2xo1dk";
    private static final String APPKEY = "295654fb3d2944a48782d71bb4a66b1f";
    private static final String SECRET = "c2179bb10cca35b3781ca74cf959127e";
    private VideoPlayerCallBack mCallBack;
    private String playerUrl;
    private EZUIPlayer mPlayer;

    /**
     * {"AppKey":"295654fb3d2944a48782d71bb4a66b1f","AccessToken":"at.5h3hehpx35l4wmta87aosbsd4g0mnwic-1e5nvshetq-1kqeb0r-vyq2xo1dk","Url":"ezopen://AES:fqGMyQOmkv9nccLvulfBRA@open.ys7.com/657836629/1.hd.live"}
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initVideoDevice();

    }

    private void initVideoDevice() {
        //playerUrl = "ezopen://AES:fqGMyQOmkv9nccLvulfBRA@open.ys7.com/657836632/1.rec";
        playerUrl = "ezopen://AES:fqGMyQOmkv9nccLvulfBRA@open.ys7.com/657836629/1.live";
        ///setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //playerUrl = "ezopen://AES:fqGMyQOmkv9nccLvulfBRA@open.ys7.com/657836629/1.hd.live";
        mPlayer = (EZUIPlayer) findViewById(R.id.player_ui);
        EZUIKit.initWithAppKey(getApplication(), APPKEY);
        EZUIKit.setAccessToken(TOKEN);
        mCallBack = new VideoPlayerCallBack();
        mPlayer.setCallBack(mCallBack);
        mPlayer.setUrl(playerUrl);
        mPlayer.startPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.setSurfaceSize(mPlayer.getWidth(), mPlayer.getHeight());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.releasePlayer();
    }

    class VideoPlayerCallBack implements EZUIPlayer.EZUIPlayerCallBack {
        @Override
        public void onPlaySuccess() {

        }

        @Override
        public void onPlayFail(EZUIError ezuiError) {

        }

        @Override
        public void onVideoSizeChange(int i, int i1) {

        }

        @Override
        public void onPrepared() {

        }

        @Override
        public void onPlayTime(Calendar calendar) {

        }

        @Override
        public void onPlayFinish() {

        }
    }

}
