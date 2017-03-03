package cn.sczhckj.kitchen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cn.sczhckj.kitchen.Config;
import cn.sczhckj.kitchen.data.bean.PushCommonBean;
import cn.sczhckj.kitchen.data.bean.kitchen.HeartBean;
import cn.sczhckj.kitchen.data.constant.OP;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.show.L;
import cn.sczhckj.kitchen.websocket.WebSocket;
import cn.sczhckj.kitchen.websocket.WebSocketConnection;
import cn.sczhckj.kitchen.websocket.WebSocketException;
import cn.sczhckj.platform.rest.io.RestRequest;
import cn.sczhckj.platform.rest.io.json.JSONRestRequest;

/**
 * @ describe:  心跳检测
 * @ author: Like on 2017/1/8.
 * @ email: 572919350@qq.com
 */

public class HeartService extends Service implements WebSocket.ConnectionHandler {
    /**
     * 心跳发送数据时间
     */
    private final int TIME = 30 * 1000;
    /**
     * 重新连接时间
     */
    private final int RECONNECT_TIME = 30 * 1000;

    private WebSocketConnection mWebSocket = new WebSocketConnection();

    private Timer timer;

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
        connect();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 连接
     */
    private void connect() {
        try {
            mWebSocket.connect(Config.URL_HEART_SERVICE + AppSystemUntil.getAndroidID(getApplicationContext()), this);
            sendMessage(mWebSocket);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 间隔发送消息检测心跳
     */
    private void sendMessage(final WebSocketConnection mWebSocket) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String msg = msg();
                mWebSocket.sendTextMessage(msg);
            }
        }, 100, TIME);
    }

    /**
     * 重新连接
     */
    private void reConnect() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mWebSocket.reconnect();
            }
        }, 0, RECONNECT_TIME);
    }

    /**
     * 发送消息内容
     *
     * @return
     */
    private String msg() {
        HeartBean bean = new HeartBean();
        bean.setDeviceId(AppSystemUntil.getAndroidID(getApplicationContext()));
        bean.setIp(AppSystemUntil.ip(getApplicationContext()));

        RestRequest<HeartBean> restRequest = JSONRestRequest.Builder.build(HeartBean.class)
                .op(OP.PUSH_HEART)
                .time()
                .bean(bean);

        return restRequest.toRequestString();
    }

    @Override
    public void onBinaryMessage(byte[] payload) {
    }

    @Override
    public void onClose(int code, String reason) {
        timer = new Timer();
        reConnect();
    }

    @Override
    public void onOpen() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onRawTextMessage(byte[] payload) {
    }

    @Override
    public void onTextMessage(String payload) {
        RestRequest<PushCommonBean> rest = JSONRestRequest.Parser.parse(payload, PushCommonBean.class);
        String op = rest.getOp();
        switch (op) {
            case OP.PUSH_HEART:
                L.d("心跳检测返回数据");
                break;
        }
    }

}
