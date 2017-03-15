package cn.sczhckj.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.Config;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.PushCommonBean;
import cn.sczhckj.kitchen.data.constant.OP;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.fragment.FoodFragment;
import cn.sczhckj.kitchen.fragment.PrintFragment;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.service.PollService;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.show.T;
import cn.sczhckj.kitchen.websocket.WebSocket;
import cn.sczhckj.kitchen.websocket.WebSocketConnection;
import cn.sczhckj.kitchen.websocket.WebSocketException;
import cn.sczhckj.platform.rest.io.RestRequest;
import cn.sczhckj.platform.rest.io.json.JSONRestRequest;

public class MainActivity extends AppCompatActivity implements OnLableClickListenner, WebSocket.ConnectionHandler {

    @Bind(R.id.activity_main_parent)
    FrameLayout activityMainParent;

    /**
     * 菜品
     */
    private FoodFragment mFoodFragment;
    /**
     * 小票
     */
    private PrintFragment mPrintFragment;
    /**
     * 轮询Service
     */
    private Intent intent;
    /**
     * 是否处于待加工菜品界面
     */
    public static boolean isFoodView = true;
    /**
     * WebSocket连接
     */
    private WebSocketConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

    }

    /**
     * 初始化
     */
    private void init() {
        initPrintFragment();
        initFoodFragment();
        initService();
        initWebSocket();
    }

    /**
     * 初始化WebSocket
     */
    private void initWebSocket() {
        try {
            connection = new WebSocketConnection();
            connection.connect(Config.URL_KITCHEN_SERVICE + AppSystemUntil.getAndroidID(this), this);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化服务轮询待加工菜品
     */
    private void initService() {
        intent = new Intent(MainActivity.this, PollService.class);
        startService(intent);
    }

    /**
     * 菜品界面初始化
     */
    private void initFoodFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mFoodFragment == null) {
            mFoodFragment = new FoodFragment();
            mFoodFragment.setOnLableClickListenner(this);
            transaction.add(R.id.activity_main_parent, mFoodFragment);
        } else {

        }
        hideFragment(transaction);
        transaction.show(mFoodFragment);
        transaction.commit();
    }

    /**
     * 小票打印初始化
     */
    private void initPrintFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mPrintFragment == null) {
            mPrintFragment = new PrintFragment();
            mPrintFragment.setOnLableClickListenner(this);
            transaction.add(R.id.activity_main_parent, mPrintFragment);
        } else {

        }
        hideFragment(transaction);
        transaction.show(mPrintFragment);
        transaction.commit();
    }

    /**
     * 隐藏界面
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mFoodFragment != null) {
            transaction.hide(mFoodFragment);
        }
        if (mPrintFragment != null) {
            transaction.hide(mPrintFragment);
        }
    }

    @Override
    public void onLableClick(int type) {
        if (type == OnLableClickListenner.FOOD_LABLE) {
            /**菜品*/
            isFoodView = true;
            initFoodFragment();
//            mPrintFragment.setAnimationOut();
            mFoodFragment.setAnimationIn();
        } else if (type == OnLableClickListenner.PRINT_LABLE) {
            /**小票*/
            isFoodView = false;
            initPrintFragment();
//            mFoodFragment.setAnimationOut();
            mPrintFragment.setAnimationIn();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //监控/拦截/屏蔽返回键
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onOpen() {
        if (!connection.isConnected()) {
            connection.reconnect();
        }
    }

    @Override
    public void onClose(int code, String reason) {

    }

    @Override
    public void onTextMessage(String payload) {
        RestRequest<PushCommonBean> restRequest
                = JSONRestRequest.Parser.parse(payload, PushCommonBean.class);
        if (OP.PUSH_REFRESH.equals(restRequest.getOp())) {
            /**刷新菜品*/
            EventBus.getDefault().post(new SendEvent(SendEvent.FOOD_REFRESH));
        }
    }

    @Override
    public void onRawTextMessage(byte[] payload) {

    }

    @Override
    public void onBinaryMessage(byte[] payload) {

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        T.showShort(MainActivity.this, keyCode + "");
        switch (keyCode) {
            case KeyEvent.KEYCODE_Q:
                /**上,Code码为152-45*/
                EventBus.getDefault().post(new SendEvent(SendEvent.KEY_PRE));
                break;
            case KeyEvent.KEYCODE_W:
                /**下,Code码为146-51*/
                EventBus.getDefault().post(new SendEvent(SendEvent.KEY_NEXT));
                break;
            case KeyEvent.KEYCODE_E:
                /**确认,Code码为149-33*/
                EventBus.getDefault().post(new SendEvent(SendEvent.KEY_AFFIRM));
                break;
            case KeyEvent.KEYCODE_R:
                /**打印,Code码为151-46*/
                if (!isFoodView) {
                    onLableClick(OnLableClickListenner.FOOD_LABLE);
                } else {
                    onLableClick(OnLableClickListenner.PRINT_LABLE);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
