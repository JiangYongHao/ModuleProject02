package net.weirun.moduleproject.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JiangYongHao on 2016/6/17.
 * <p/>
 * 定时器帮助类
 */
public class TimerUtil {

    /**
     * 时间到了就执行
     */
    public interface OnTimeOutListener {
        /**
         * 时间到了就执行
         */
        void onTimeOut(int time, boolean isStop);
    }

    private OnTimeOutListener listener;


    /**
     * 定时的时间 单位m
     */
    private int timeCount;

    /**
     * 当前的时间标志 m
     */
    private int currentTime = 0;


    private boolean isAdd;

    private TextView timeTextView;

    private String mNormalText;
    private String mAppendText;

    private StringBuilder mBuilder = new StringBuilder();


    public void setListener(OnTimeOutListener listener) {
        this.listener = listener;
    }

    /**
     * 启动
     *
     * @param count
     */
    public void startTimer(int count) {
        this.timeCount = count;
        isAdd = true;
        currentTime = count;
        startTimer();
    }

//    /**
//     * 启动
//     *
//     * @param count
//     */
//    public void startTimer(int count, TextView textView) {
//        this.timeTextView = textView;
//        textView.setText("" + count);
//        this.timeCount = count;
//        isAdd = true;
//        currentTime = count;
//        startTimer();
//    }

    /**
     * 启动定时器
     *
     * @param count    时间 s :秒
     * @param textView 需要显示的view
     * @param normal   View 默认的显示字符串
     * @param append   在时间后添加的字符串
     */
    public void startTimer(int count, TextView textView, String normal, String append) {
        this.timeTextView = textView;
        textView.setText("" + count);
        this.timeCount = count;
        isAdd = true;
        currentTime = count;
        this.mNormalText = normal;
        this.mAppendText = append;
        startTimer();
    }


    /**
     * 停止计时器
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timerTask.cancel();
            timer = null;
        }
    }


    /**
     * 启动定时器
     */
    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1001) {
                if (isAdd) {
                    currentTime--;
                    if (currentTime >= 0) {
                        if (listener != null) {
                            listener.onTimeOut(currentTime, false);
                        }
                        if (timeTextView != null) {
                            mBuilder.delete(0, mBuilder.length());
                            mBuilder.append(currentTime);
                            if (mAppendText != null) {
                                mBuilder.append(mAppendText);
                            }
                            timeTextView.setText(mBuilder.toString());
                        }
                    } else {
                        if (listener != null) {
                            listener.onTimeOut(currentTime, true);
                        }
                        if (timeTextView != null && mNormalText != null) {
                            timeTextView.setText(mNormalText);
                        } else if (timeTextView != null) {
                            timeTextView.setText("");
                        }
                        isAdd = false;
                        currentTime = -1;
                    }
                }
            }

            super.handleMessage(msg);
        }
    };
    private Timer timer;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1001);
        }
    };

}
