package cn.sczhckj.kitchen.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import cn.sczhckj.kitchen.Config;
import cn.sczhckj.kitchen.data.bean.PushCommonBean;
import cn.sczhckj.kitchen.data.constant.OP;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.show.L;
import cn.sczhckj.kitchen.websocket.WebSocket;
import cn.sczhckj.kitchen.websocket.WebSocketConnection;
import cn.sczhckj.kitchen.websocket.WebSocketException;
import cn.sczhckj.platform.rest.io.RestRequest;
import cn.sczhckj.platform.rest.io.json.JSONRestRequest;

/**
 * @ describe:
 * @ author: Like on 2017-04-28.
 * @ email: 572919350@qq.com
 */

public class WebSocketService extends Service implements WebSocket.ConnectionHandler {
    /**
     * 心跳发送数据时间
     */
    private final int TIME = 30 * 1000;

    private WebSocketConnection mWebSocket = new WebSocketConnection();

    private Timer timer;

    /**
     * 是否链接
     */
    private boolean isConnect = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            timer = new Timer();
            mWebSocket.connect(Config.URL_KITCHEN_SERVICE + AppSystemUntil.getAndroidID(getApplicationContext()), this);
            startTimer();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    mWebSocket.reconnect();
                    break;
            }
        }
    };

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mWebSocket != null) {
                    if (!isConnect) {
                        /**没有连接，重新连接*/
                        mHandler.sendEmptyMessage(0x123);
                    }
                }
            }
        }, 100, TIME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBinaryMessage(byte[] payload) {
    }

    @Override
    public void onClose(int code, String reason) {
        isConnect = false;
    }

    @Override
    public void onOpen() {
        isConnect = true;
    }

    @Override
    public void onRawTextMessage(byte[] payload) {
    }

    @Override
    public void onTextMessage(String payload) {
        RestRequest<PushCommonBean> rest = JSONRestRequest.Parser.parse(payload, PushCommonBean.class);
        String op = rest.getOp();
        switch (op) {
            case OP.PUSH_REFRESH:
                //刷新菜品
                EventBus.getDefault().post(new SendEvent(SendEvent.FOOD_REFRESH));
                break;
            case OP.PUSH_REBOOK:
                //退单
                EventBus.getDefault().post(new SendEvent(SendEvent.PUSH_DEBOOK, rest.getBean().getDebook()));
                break;
        }
    }
}
