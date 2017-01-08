package cn.sczhckj.kitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.fragment.FoodFragment;
import cn.sczhckj.kitchen.fragment.PrintFragment;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.service.PollService;

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
     * 轮询Service
     */
    private Intent intent;

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
//        initService();
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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (mFoodFragment == null) {
            mFoodFragment = new FoodFragment();
            mFoodFragment.setOnLableClickListenner(this);
            transaction.add(R.id.activity_main_parent, mFoodFragment);
        }else {

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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        if (mPrintFragment == null) {
            mPrintFragment = new PrintFragment();
            mPrintFragment.setOnLableClickListenner(this);
            transaction.add(R.id.activity_main_parent, mPrintFragment);
        }else {

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
            initFoodFragment();
        } else if (type == OnLableClickListenner.PRINT_LABLE) {
            /**小票*/
            initPrintFragment();
        }
    }
}
