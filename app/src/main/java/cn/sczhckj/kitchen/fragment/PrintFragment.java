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
import cn.sczhckj.kitchen.adapter.PrintAdapter;
import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.kitchen.DoneBean;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.data.response.ResponseCode;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.mode.KitchenMode;
import cn.sczhckj.kitchen.overwrite.DashlineItemDivider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ describe:  小票打印界面
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class PrintFragment extends BaseFragment implements Callback<Bean<List<DoneBean>>> {


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_print, null, false);
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
        mKitchenMode = new KitchenMode(getContext());
        mKitchenMode.done(this);
    }

    private void initAdapter() {
        mPrintAdapter = new PrintAdapter(getContext(), null);
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
            if (mList.size()>0) {
                EventBus.getDefault().post(new SendEvent(SendEvent.PRINT_LABLE, mList.get(0).getName(),mList.get(0).getTableName()));
            }
        } else {

        }
    }

    @Override
    public void onFailure(Call<Bean<List<DoneBean>>> call, Throwable t) {

    }

    /**
     * 设置待加工菜品的缩略信息
     *
     * @param name  菜品名称
     * @param count 数量
     */
    public void setFoodTitle(String name, int count) {
        breviaryFoodConut.setVisibility(View.VISIBLE);
        breviaryFoodName.setText(name);
        breviaryFoodConut.setText(count + "份");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reviceMessage(SendEvent event) {
        if (event.getType() == SendEvent.FOOD_LABLE){
            /**刷新待加工缩略信息*/
            setFoodTitle(event.getName(),event.getCount());
        }
    }

}
