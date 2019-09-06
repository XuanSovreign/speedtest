package com.space.testspeed;

import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler=new Handler();
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mIntent = new Intent(this, DownloadService.class);
        startService(mIntent);
        final TextView tvSpeed = findViewById(R.id.tv_speed);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getNetData(tvSpeed);
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this,1000);
            }
        });
    }

    private void getNetData(TextView speedView){
        String netSpeed = NetSpeed.getNetSpeed(Process.myUid());
        speedView.setText(netSpeed);
    }

    @Override
    protected void onStop() {
        if (isFinishing()) {
            stopService(mIntent);
        }
        super.onStop();
    }
}
