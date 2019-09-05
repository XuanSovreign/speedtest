package com.space.testspeed;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by licht on 2019/8/15.
 */

public class DownThread extends Thread {
    private static final String TAG = DownThread.class.getName();
    private String  downUrl;
    private DownTaskThread[] mThreads;
    private File downFile;
    private int threadNum;

    public DownThread(String downUrl, File downFile, int threadNum) {
        this.downFile=downFile;
        this.downUrl=downUrl;
        this.threadNum=threadNum;
    }

    public DownThread(String downUrl, int threadNum) {
        this.downUrl=downUrl;
        this.threadNum=threadNum;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(downUrl);
            Log.e(TAG, "run: "+downUrl );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            long lengthLong = connection.getContentLengthLong();
            Log.e(TAG, "run: length="+lengthLong);
            if (lengthLong <= 0) {
                Log.e(TAG, "run: 文件不存在");
                return;
            }
            mThreads = new DownTaskThread[threadNum];
            long blockSize = lengthLong % mThreads.length == 0 ? lengthLong / mThreads.length : lengthLong / mThreads.length + 1;

            for (int i = 0; i < mThreads.length; i++) {
                mThreads[i] = new DownTaskThread(i, downFile,downUrl , blockSize);
                mThreads[i].start();
                Log.e(TAG, "onStartCommand: thread"+i );
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stopWork() {
        for (int i = 0; i < mThreads.length; i++) {
            if (mThreads[i] != null) {
                mThreads[i].setStop(true);
            }
        }
    }
}
