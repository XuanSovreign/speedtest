package com.space.testspeed;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * @Author Sovereign
 * @Date 2019/8/15
 */

public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getName();
    private DownThread mDownThread;
    private SharedPreferences mPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPreferences = getSharedPreferences("speed_ip", MODE_PRIVATE);
        String ip = mPreferences.getString("ip_address", "");
//        String path = "http://192.168.0.100:8080/down.rar";
        String path = "http://" + ip + "/down.rar";
//        String path = "http://" + ip + "/hwhnServer.zip";
//        mDownThread = new DownThread(path, file,2);
        mDownThread = new DownThread(path, 2);
        mDownThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mDownThread != null) {
            mDownThread.stopWork();
        }
        super.onDestroy();
    }
}
