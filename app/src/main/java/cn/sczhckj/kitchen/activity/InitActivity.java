package cn.sczhckj.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.data.bean.device.VersionBean;
import cn.sczhckj.kitchen.data.constant.FileConstant;
import cn.sczhckj.kitchen.listenner.OnVersionCheckListenner;
import cn.sczhckj.kitchen.manage.DownLoadManager;
import cn.sczhckj.kitchen.mode.KitchenImpl;
import cn.sczhckj.kitchen.overwrite.ProgressDialog;
import cn.sczhckj.kitchen.service.HeartService;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.FileUntils;

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
        /**创建下载文件夹*/
        FileUntils.createFileDir(FileConstant.PATH);
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
    private void startService() {
        Intent intent = new Intent(InitActivity.this, HeartService.class);
        startService(intent);
    }

    @OnClick(R.id.activity_init)
    public void onClick() {
        /**重新加载*/
        init();
    }

    @Override
    public void onSuccess(String context, int type) {
        title.setText(context);
        switch (type) {
            case OnVersionCheckListenner.INIT_SUCCESS:
                inputMain();
                break;
            case OnVersionCheckListenner.INIT_UPDATA:
                if (mKitchen.getVersionBean() != null) {
                    hint(mKitchen.getVersionBean());
                } else {
                    inputMain();
                }
                break;
        }
    }

    @Override
    public void onFail() {
        activityInit.setClickable(true);
        title.setText("初始化失败，点击屏幕重新初始化...");
    }

    /**
     * 进入主页
     */
    private void inputMain() {
        Intent intent = new Intent(InitActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void hint(final VersionBean bean) {
        final ProgressDialog dialog = new ProgressDialog(InitActivity.this);
        dialog.setTitle("版本更新");
        String str = "当前版本：" + AppSystemUntil.getVersionName(InitActivity.this)
                + "\n最新版本：" + bean.getVersion()
                + "\n更新大小：" + bean.getSize()
                + "\n更新内容:" + bean.getContent();
        dialog.setContextText(str);
        dialog.setNegativeButton("马上更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager manager = new DownLoadManager();
                manager.retrofitDownload(bean.getUrl(), mKitchen.judgeName(bean.getName()), InitActivity.this);
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("暂不更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                inputMain();
            }
        });
        /**点击外部不能关闭*/
        dialog.setCancelable(false);
        dialog.show();
    }

}
