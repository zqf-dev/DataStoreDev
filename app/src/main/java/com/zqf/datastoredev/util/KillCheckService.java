package com.zqf.datastoredev.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class KillCheckService extends Service {
    private static final String TAG = "KillCheckService";
    private String mPN = "";
    private Timer timer = null;
    private ActivityManager am = null;

    @Override
    public void onCreate() {
        timer = new Timer();
        mPN = AppInfoUtils.getProcessName(this);
        Log.i(TAG, "mPackageName: >> " + mPN);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "开始检查服务");
                Message message = new Message();
                message.what = 0x101;
                mHandler.sendMessage(message);
            }
        }, 1000, 3000);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try {
                if (msg.what == 0x101) {
                    if (!isBackgroundRunning()) {
                        Log.i(TAG, "退出操作");
                        visualDisconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    });

    private boolean isBackgroundRunning() {
        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> processList = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : processList) {
            if (info.baseActivity.getPackageName().contains(mPN)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 断开可视化连接
     */
    private void visualDisconnect() {
        try {
            Log.i(TAG, "reqVisualDisconnect return error Message: >> ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        Log.i(TAG, "销毁服务");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
