package com.space.testspeed;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by licht on 2019/8/15.
 */

public class DownTaskThread extends Thread {
    private final static String TAG = DownTaskThread.class.getName();
    private int threadId;
    private File downFile;
    private String downUrl;
    private long blockSize;
    private long startPos;
    private long endPos;
    private boolean isStop;
    private byte[] bytes = new byte[1024 * 10];
    private URL url ;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public DownTaskThread(int threadId, File downFile, String downUrl, long blockSize) {
        this.threadId = threadId;
        this.downFile = downFile;
        this.downUrl = downUrl;
        this.blockSize = blockSize;
        try {
            url= new URL(downUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (!isStop) {
            BufferedInputStream in = null;
            try {
                Log.e(TAG, "run: ===============");
                Log.e(TAG, "run: " + downUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                startPos = threadId * blockSize;
                endPos = (threadId + 1) * blockSize - 1;
                connection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                connection.setRequestMethod("GET");
                Log.e(TAG, "run: " + "Range bytes=" + startPos + "-" + endPos);
                if (connection.getResponseCode() == 206) {
                    Log.e(TAG, "run: 开始时间=" + System.currentTimeMillis());
                    int length = 0;
                    in = new BufferedInputStream(connection.getInputStream());
                    while ((length = in.read(bytes)) > 0) {
                        if (isStop) {
                            break;
                        }
                    }
                }
                Log.e(TAG, "run: 完成时间=" + System.currentTimeMillis());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                        Log.e(TAG, "run: finish");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }


}
