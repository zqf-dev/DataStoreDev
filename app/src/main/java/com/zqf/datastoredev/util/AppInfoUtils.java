package com.zqf.datastoredev.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class AppInfoUtils {
    private static String mAppVersionName;
    private static Bundle mConfigBundle;
    /**
     * 获取应用名称
     *
     * @param context Context
     * @return 应用名称
     */
    public static CharSequence getAppName(Context context) {
        if (context == null) return "";
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.loadLabel(packageManager);
        } catch (Throwable e) {
            Log.i("AppInfoUtils", e.getMessage());
        }
        return "";
    }

    /**
     * 获取 App 的 ApplicationId
     *
     * @param context Context
     * @return ApplicationId
     */
    public static String getProcessName(Context context) {
        if (context == null) return "";
        try {
            return context.getApplicationInfo().processName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获取主进程的名称
     *
     * @param context Context
     * @return 主进程名称
     */
    public static String getMainProcessName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getApplicationContext().getApplicationInfo().processName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 判断当前进程名称是否为主进程
     *
     * @param context Context
     * @param bundle Bundle
     * @return 是否主进程
     */
    public static boolean isMainProcess(Context context, Bundle bundle) {
        if (context == null) {
            return false;
        }

        String mainProcessName = AppInfoUtils.getMainProcessName(context);
        if (TextUtils.isEmpty(mainProcessName) && bundle != null) {
            mainProcessName = bundle.getString("com.sensorsdata.analytics.android.MainProcessName");
        }

        if (TextUtils.isEmpty(mainProcessName)) {
            return true;
        }

        String currentProcess = getCurrentProcessName();
        return TextUtils.isEmpty(currentProcess) || mainProcessName.equals(currentProcess);
    }

    /**
     * 获得当前进程的名字
     *
     * @return 进程名称
     */
    private static String getCurrentProcessName() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return Application.getProcessName();
            }

            String currentProcess = getCurrentProcessNameByCmd();
            if (TextUtils.isEmpty(currentProcess)) {
                currentProcess = getCurrentProcessNameByAT();
            }
            return currentProcess;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getCurrentProcessNameByAT() {
        String processName = null;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader());
            Method declaredMethod = activityThread.getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            Object processInvoke = declaredMethod.invoke(null);
            if (processInvoke instanceof String) {
                processName = (String) processInvoke;
            }
        } catch (Throwable e) {
            //ignore
        }
        return processName;
    }

    private static String getCurrentProcessNameByCmd() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                return new String(buffer, 0, len, StandardCharsets.UTF_8);
            }
        } catch (Throwable e) {
            // ignore
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
