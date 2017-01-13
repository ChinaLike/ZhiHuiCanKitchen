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

    private View view;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_print, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 数据初始化
     */
    private void init() {
        initAdapter();
        animation = new AnimationImpl(getContext());
        mKitchenMode = new KitchenMode(getContext());
        mKitchen = new KitchenImpl(getContext());
        mKitchenMode.done(this);
    }

    private void initAdapter() {
        mPrintAdapter = new PrintAdapter(getContext(), null);
        mPrintAdapter.setOnItemClickListenner(this);
        printRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        printRecyclerView.setAdapter(mPrintAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
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
            if (bean != null && bean.getCode() == ResponseCode.SUCCESS){
                T.showShort(getContext(),bean.getMessage());
            }else {
                T.showShort(getContext(),"补打失败");
            }
        }

        @Override
        public void onFailure(Call<Bean<ResponseCommonBean>> call, Throwable t) {
            T.showShort(getContext(),"补打失败");
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
     */
    public void setFoodTitle(String name, int count) {
        if (count>0) {
            breviaryFoodConut.setVisibility(View.VISIBLE);
            breviaryFoodName.setText(name);
            breviaryFoodConut.setText(count + "份");
        }else {
            breviaryFoodConut.setVisibility(View.GONE);
            breviaryFoodName.setText(name);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reviceMessage(SendEvent event) {
        if (event.getType() == SendEvent.FOOD_LABLE) {
            /**刷新待加工缩略信息*/
            setFoodTitle(event.getName(), event.getCount());
        } else if (event.getType() == SendEvent.KEY_AFFIRM && !MainActivity.isFoodView) {
            /**按键补打*/
            if (doneBean != null) {
                mKitchen.print(doneBean, printCallback);
            }
        } else if (event.getType() == SendEvent.KEY_NEXT && !MainActivity.isFoodView) {
            /**下一个*/
            next();
        } else if (event.getType() == SendEvent.KEY_PRE && !MainActivity.isFoodView) {
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
            refreshAdapter(index);
        }
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
            if (i == index) {
                mList.get(i).setSelect(true);
            } else {
                mList.get(i).setSelect(false);
            }
        }
        mPrintAdapter.notifyDataSetChanged(mList);
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
