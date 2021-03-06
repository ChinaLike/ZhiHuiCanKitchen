package cn.sczhckj.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.activity.MainActivity;
import cn.sczhckj.kitchen.adapter.PrintAdapter;
import cn.sczhckj.kitchen.animation.AnimationImpl;
import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.ResponseCommonBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DoneBean;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.data.response.ResponseCode;
import cn.sczhckj.kitchen.listenner.OnItemClickListenner;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.mode.KitchenImpl;
import cn.sczhckj.kitchen.mode.KitchenMode;
import cn.sczhckj.kitchen.overwrite.DashlineItemDivider;
import cn.sczhckj.kitchen.until.show.L;
import cn.sczhckj.kitchen.until.show.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ describe:  小票打印界面
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class PrintFragment extends BaseFragment implements Callback<Bean<List<DoneBean>>>, OnItemClickListenner {


    @Bind(R.id.breviary_food_name)
    TextView breviaryFoodName;
    @Bind(R.id.breviary_food_conut)
    TextView breviaryFoodConut;
    @Bind(R.id.food_breviary_parent)
    RelativeLayout foodBreviaryParent;
    @Bind(R.id.print_recy)
    RecyclerView printRecyclerView;
    /**
     * 获取数据
     */
    private KitchenMode mKitchenMode;

    /**
     * 补打记录适配
     */
    private PrintAdapter mPrintAdapter;
    /**
     * 补打记录
     */
    private List<DoneBean> mList = new ArrayList<>();
    /**
     * 默认下标
     */
    private int index = -1;
    /**
     * 当前补打记录
     */
    private DoneBean doneBean;
    /**
     * 补打数据请求
     */
    private KitchenImpl mKitchen;

    /**
     * 动画
     */
    private AnimationImpl animation;
    /**
     * 布局管理器
     */
    private LinearLayoutManager manager;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_print;
    }

    /**
     * 数据初始化
     */
    @Override
    public void init() {
        initAdapter();
        animation = new AnimationImpl(getContext());
        mKitchenMode = new KitchenMode(getContext());
        mKitchen = new KitchenImpl(getContext());
        done();
    }

    /**
     * 刷新补打记录
     */
    private void done() {
        if (mKitchenMode == null) {
            mKitchen = new KitchenImpl(getContext());
        }
        mKitchenMode.done(this);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mPrintAdapter = new PrintAdapter(getContext(), null);
        mPrintAdapter.setOnItemClickListenner(this);
        manager = new LinearLayoutManager(getContext());
        printRecyclerView.setLayoutManager(manager);
        printRecyclerView.setAdapter(mPrintAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.food_breviary_parent)
    public void onClick() {
        onLableClickListenner.onLableClick(OnLableClickListenner.FOOD_LABLE);
    }

    @Override
    public void onResponse(Call<Bean<List<DoneBean>>> call, Response<Bean<List<DoneBean>>> response) {
        Bean<List<DoneBean>> bean = response.body();
        if (bean != null && bean.getCode() == ResponseCode.SUCCESS) {
            mList = bean.getResult();
            mPrintAdapter.notifyDataSetChanged(mList);
            doneBean = null;
            if (mList.size() > 0) {
                doneBean = mList.get(0);
                index = -1;
                EventBus.getDefault().post(new SendEvent(SendEvent.PRINT_LABLE, mList.get(0).getName(), mList.get(0).getTableName()));
            }
        } else {

        }
    }

    @Override
    public void onFailure(Call<Bean<List<DoneBean>>> call, Throwable t) {

    }

    /**
     * 补打回调
     */
    Callback<Bean<ResponseCommonBean>> printCallback = new Callback<Bean<ResponseCommonBean>>() {
        @Override
        public void onResponse(Call<Bean<ResponseCommonBean>> call, Response<Bean<ResponseCommonBean>> response) {
            Bean<ResponseCommonBean> bean = response.body();
            if (bean != null && bean.getCode() == ResponseCode.SUCCESS) {
                T.showCenterShort(getContext(), bean.getMessage());
                done();
            } else if (bean != null && bean.getCode() == ResponseCode.FAILURE) {
                T.showCenterShort(getContext(), bean.getMessage());
            } else {
                T.showCenterShort(getContext(), "补打失败");
            }
        }

        @Override
        public void onFailure(Call<Bean<ResponseCommonBean>> call, Throwable t) {
            T.showCenterShort(getContext(), "网络异常，请稍后再试！");
        }
    };

    /**
     * 设置进入动画效果
     */
    public void setAnimationIn() {
        animation.downIn(view);
    }

    /**
     * 设置退出动画效果
     */
    public void setAnimationOut() {
        animation.downOut(view);
    }

    /**
     * 设置待加工菜品的缩略信息
     *
     * @param name  菜品名称
     * @param count 数量
     * @param unit 单位
     */
    public void setFoodTitle(String name, int count,String unit) {
        if (count > 0) {
            breviaryFoodConut.setVisibility(View.VISIBLE);
            breviaryFoodName.setText(name);
            breviaryFoodConut.setText(count + ""+unit);
        } else {
            breviaryFoodConut.setVisibility(View.GONE);
            breviaryFoodName.setText(name);
        }
    }

    /**
     * 补打事件
     * @param event
     */
    public void printEvent(SendEvent event) {
        if (event.getType() == SendEvent.FOOD_LABLE) {
            /**刷新待加工缩略信息*/
            setFoodTitle(event.getName(), event.getCount() ,event.getUnit());
        } else if (event.getType() == SendEvent.KEY_AFFIRM && MainActivity.currentView == MainActivity.PRINT) {
            /**按键补打*/
            if (doneBean != null) {
                mKitchen.print(doneBean, printCallback);
            }
        } else if (event.getType() == SendEvent.KEY_NEXT && MainActivity.currentView == MainActivity.PRINT) {
            /**下一个*/
            next();
        } else if (event.getType() == SendEvent.KEY_PRE && MainActivity.currentView == MainActivity.PRINT) {
            /**上一个*/
            pre();
        } else if (event.getType() == SendEvent.FOOD_FINISH) {
            /**出菜成功后，通知刷新补打记录*/
            mKitchenMode.done(this);
        }
    }

    /**
     * 下一个
     */
    private void next() {
        int size = mList.size();
        if (index < (size - 1)) {
            index++;
        } else {
            index = 0;
        }
        refreshAdapter(index);
    }

    /**
     * 上一个
     */
    private void pre() {
        if (index > 0) {
            index--;
            refreshAdapter(index);
        }
    }

    /**
     * 刷新适配
     *
     * @param index
     */
    private void refreshAdapter(int index) {
        for (int i = 0; i < mList.size(); i++) {
            doneBean = mList.get(index);
            if (i == index) {
                mList.get(i).setSelect(true);
            } else {
                mList.get(i).setSelect(false);
            }
        }
        mPrintAdapter.notifyDataSetChanged(mList);
        move(index, mPrintAdapter, printRecyclerView, manager);
    }

    @Override
    public void onClick(View v, int postion, Object bean) {
        index = postion;
        doneBean = (DoneBean) bean;
        if (doneBean != null) {
            mKitchen.print(doneBean, printCallback);
        }
    }
}
