package cn.sczhckj.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.listenner.OnVersionCheckListenner;
import cn.sczhckj.kitchen.mode.KitchenImpl;
import cn.sczhckj.kitchen.service.HeartService;
import cn.sczhckj.kitchen.until.AppSystemUntil;

public class InitActivity extends AppCompatActivity implements OnVersionCheckListenner {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.activity_init)
    RelativeLayout activityInit;
    @Bind(R.id.device_id)
    TextView deviceId;

    /**
     * 版本更新
     */
    private KitchenImpl mKitchen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        ButterKnife.bind(this);
        /**设置设备Id*/
        deviceId.setText("设备ID:" + AppSystemUntil.getAndroidID(this));
        init();

        startService();
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("数据加载中...");
        activityInit.setClickable(false);
        mKitchen = new KitchenImpl(InitActivity.this);
        mKitchen.checkVersion(this);
    }

    /**
     * 开启心跳检测
     */
    private void startService(){
        Intent intent = new Intent(InitActivity.this, HeartService.class);
        startService(intent);
    }

    @OnClick(R.id.activity_init)
    public void onClick() {
        /**重新加载*/
        init();
    }

    @Override
    public void onSuccess(String context) {
        title.setText("初始化成功" + context);
        Intent intent = new Intent(InitActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFail() {
        activityInit.setClickable(true);
        title.setText("初始化失败，点击屏幕重新初始化...");
    }
}
