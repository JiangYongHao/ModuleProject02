package net.weirun.moduleproject.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.net.HttpURLConnection;

/**
 * Created by JiangYongHao on 2016/7/8.
 */
public class VersionUtils {

    private static final String TAG = "VersionUtils";

    public static final String URL = "";

    public void checkVersion(Context context) {
        getCurrentVersion(context);
    }

    /**
     * 获取当前版本号
     *
     * @param context
     */
    private void getCurrentVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            Log.d(TAG, "getCurrentVersion:  version = " + packageInfo.versionName);
            Log.d(TAG, "getCurrentVersion:  version = " + packageInfo.versionCode);
//            return;packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        HttpURLConnection
    }
}
