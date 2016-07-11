package net.weirun.moduleproject;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by JiangYongHao on 2016/7/5.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
