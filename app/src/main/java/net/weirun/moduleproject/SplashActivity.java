package net.weirun.moduleproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.weirun.moduleproject.constant.ShareFileKey;
import net.weirun.moduleproject.utils.TimerUtil;
/**
 * Created by JiangYongHao on 2016/6/20.
 */

/**
 * 首页引导页
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private FrameLayout imageLayout;
    private ImageView splashIv;
    private TextView timeText;
    private ViewPager viewPager;

    private TimerUtil timerUtil;

    private int timeCount = 5;

    private boolean isShowSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initShare();
        timerUtil = new TimerUtil();
        assignViews();
        initTimerUtil();
    }

    /**
     * 获取share数据
     */
    private void initShare() {
        SharedPreferences sp = getSharedPreferences(ShareFileKey.FILE_NAME, MODE_PRIVATE);
        isShowSplash = sp.getBoolean(ShareFileKey.KEY_IS_SPLASH_SHOW, true);
    }

    /**
     * 初始化timer
     */
    private void initTimerUtil() {
        timerUtil = new TimerUtil();
        timerUtil.startTimer(timeCount);
        timeText.setText(timeCount + "");
        timerUtil.setListener(new TimerUtil.OnTimeOutListener() {
            @Override
            public void onTimeOut(int time, boolean isStop) {
                if (isStop) {
                    showSplash();
//                    toLogin();

                } else {
                    timeText.setText(time + "");
                }
            }
        });
    }

    /**
     * 是否显示 引导页
     */
    private void showSplash() {

        timerUtil.stopTimer();
        if (isShowSplash) {
//            getSharedPreferences(ShareFileKey.FILE_NAME, MODE_PRIVATE).edit().putBoolean(ShareFileKey.KEY_IS_SPLASH_SHOW, false);
            toLogin();
        } else {
            toLogin();
        }
    }

    private void toLogin() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }


    /**
     * 初始化组件
     */
    private void assignViews() {
        imageLayout = (FrameLayout) findViewById(R.id.image_layout);
        splashIv = (ImageView) findViewById(R.id.splash_iv);
        timeText = (TextView) findViewById(R.id.progress);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }


}

