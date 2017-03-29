package cn.sczhckj.kitchen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import cn.sczhckj.kitchen.data.event.SendEvent;

/**
 * @ describe:  轮询Service
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class PollService extends Service {

    /**
     * 延迟多少秒之后，执行发送消息
     */
    private final long DELAY = 500;
    /**
     * 每隔多少秒发送一次
     */
    private final long TIME = 20 * 1000;

    private Timer timer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendMessage();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 间隔发送消息
     */
    private void sendMessage() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                EventBus.getDefault().post(new SendEvent(SendEvent.FOOD_REFRESH));
            }
        }, DELAY, TIME);
    }

}
