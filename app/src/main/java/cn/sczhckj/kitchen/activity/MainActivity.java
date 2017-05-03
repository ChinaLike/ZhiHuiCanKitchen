package cn.sczhckj.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.Config;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.PushCommonBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DebookBean;
import cn.sczhckj.kitchen.data.constant.OP;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.fragment.DebookFragment;
import cn.sczhckj.kitchen.fragment.FoodFragment;
import cn.sczhckj.kitchen.fragment.PrintFragment;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.mode.MusicImpl;
import cn.sczhckj.kitchen.overwrite.CommonDialog;
import cn.sczhckj.kitchen.service.PollService;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.show.L;
import cn.sczhckj.kitchen.until.show.T;
import cn.sczhckj.kitchen.websocket.WebSocket;
import cn.sczhckj.kitchen.websocket.WebSocketConnection;
import cn.sczhckj.kitchen.websocket.WebSocketException;
import cn.sczhckj.platform.rest.io.RestRequest;
import cn.sczhckj.platform.rest.io.json.JSONRestRequest;

public class MainActivity extends AppCompatActivity implements OnLableClickListenner {

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
     * 退菜记录
     */
    private DebookFragment mDebookFragment;
    /**
     * 当前界面下标
     */
    public static int currentView;
    /**
     * 待加工下标
     */
    public static final int FOOD = 0;
    /**
     * 打印下标
     */
    public static final int PRINT = 1;
    /**
     * 退点下标
     */
    public static final int DEBOOK = 2;

    private Intent intent;

    /**
     * 通用弹窗
     */
    private CommonDialog mDialog;

    private MusicImpl mMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mDialog = new CommonDialog(MainActivity.this, CommonDialog.Mode.TEXT);
        mDialog.setSupportKey(true);
        mMusic = new MusicImpl(MainActivity.this, MusicImpl.DEBOOK, R.raw.dd);
        initDebookFragment();
        initPrintFragment();
        initFoodFragment();
        initService();
    }

    /**
     * 有退菜时弹窗显示，如果正在显示则替换显示内容
     *
     * @param bean
     */
    public void debook(DebookBean bean) {
        if (mFoodFragment != null) {
            mFoodFragment.initTodo();
        }
        if (bean != null) {
            String content = bean.getTableName() + "\t退点\t" + bean.getName() + "\t" + bean.getDebookCount() + bean.getDebookUnit();
            mMusic.play();
            if (mDialog.isShowing()) {
                mDialog.setTextContext(content);
            } else {
                mDialog.setTitle("退菜提醒")
                        .setTextContext(content)
                        .setTextSize(20F)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog.dismiss();
                            }
                        })
                        .show();
            }
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
     * 初始化退菜界面
     */
    private void initDebookFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mDebookFragment == null) {
            mDebookFragment = new DebookFragment();
            mDebookFragment.setOnLableClickListenner(this);
            transaction.add(R.id.activity_main_parent, mDebookFragment);
        } else {

        }
        hideFragment(transaction);
        transaction.show(mDebookFragment);
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
        if (mDebookFragment != null){
            transaction.hide(mDebookFragment);
        }
    }

    @Override
    public void onLableClick(int type) {
        if (type == OnLableClickListenner.FOOD_LABLE) {
            /**菜品*/
            currentView = FOOD;
            initFoodFragment();
//            mPrintFragment.setAnimationOut();
//            mFoodFragment.setAnimationIn();
        } else if (type == OnLableClickListenner.PRINT_LABLE) {
            /**小票*/
            currentView = PRINT;
            initPrintFragment();
//            mFoodFragment.setAnimationOut();
//            mPrintFragment.setAnimationIn();
        } else if (type == OnLableClickListenner.DEBOOK_LABLE) {
            /**退货*/
            currentView = DEBOOK;
            initDebookFragment();
//            mDebookFragment.setAnimationIn();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
        EventBus.getDefault().unregister(this);
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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
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
                if (currentView == FOOD) {
                    onLableClick(OnLableClickListenner.PRINT_LABLE);
                } else if (currentView == PRINT) {
                    onLableClick(OnLableClickListenner.DEBOOK_LABLE);
                } else if (currentView == DEBOOK) {
                    onLableClick(OnLableClickListenner.FOOD_LABLE);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBus(SendEvent event) {
        if (event.getType() == SendEvent.PUSH_DEBOOK) {
            /**有退菜弹窗*/
            debook(event.getDebook());
        }
        if (mFoodFragment != null) {
            mFoodFragment.foodEvent(event);
        }
        if (mPrintFragment != null) {
            mPrintFragment.printEvent(event);
        }
        if (mDebookFragment != null) {
            mDebookFragment.debookEvent(event);
        }

    }

}
