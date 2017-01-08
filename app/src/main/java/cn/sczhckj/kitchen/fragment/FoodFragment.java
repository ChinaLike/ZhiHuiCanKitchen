package cn.sczhckj.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
import cn.sczhckj.kitchen.adapter.TableAdapter;
import cn.sczhckj.kitchen.adapter.TodoAdapter;
import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.RequestCommonBean;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.data.response.ResponseCode;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.mode.KitchenMode;
import cn.sczhckj.kitchen.mode.RetrofitRequest;
import cn.sczhckj.kitchen.overwrite.DashlineItemDivider;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.show.T;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ describe:  菜品界面
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class FoodFragment extends BaseFragment implements Callback<Bean<List<TodoBean>>> {


    @Bind(R.id.breviary_print_name)
    TextView breviaryPrintName;
    @Bind(R.id.breviary_print_table)
    TextView breviaryPrintTable;
    @Bind(R.id.print_breviary_parent)
    RelativeLayout printBreviaryParent;
    @Bind(R.id.food_name)
    TextView foodName;
    @Bind(R.id.food_table_recyclerview)
    RecyclerView foodTableRecyclerview;
    @Bind(R.id.food_recyclerview)
    RecyclerView foodRecyclerview;
    @Bind(R.id.food_context_parent)
    LinearLayout foodParent;
    /**
     * 后厨请求数据
     */
    private KitchenMode mKitchenMode;
    /**
     * 适配器
     */
    private TodoAdapter mTodoAdapter;
    /**
     * 头部数据
     */
    private TodoBean headBean = new TodoBean();
    /**
     * 台桌适配器
     */
    private TableAdapter mTableAdapter;

    /**
     * 一行显示的台桌数量
     */
    private final int COUNT = 5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mKitchenMode = new KitchenMode(getContext());
        initTodoAdapter();
        initTableAdapter();
        initTodo();
    }

    /**
     * 初始化代加工菜品
     */
    private void initTodo() {
        mKitchenMode.todo(this);
    }

    /**
     * 初始化适配器
     */
    private void initTodoAdapter() {
        mTodoAdapter = new TodoAdapter(getContext(), null);
        foodRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        foodRecyclerview.setAdapter(mTodoAdapter);
        foodRecyclerview.addItemDecoration(new DashlineItemDivider(ContextCompat.getColor(getContext(), R.color.line), 100000, 1));
    }

    /**
     * 初始化台桌适配器
     */
    private void initTableAdapter() {
        mTableAdapter = new TableAdapter(getContext(), null);
        foodTableRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), COUNT));
        foodTableRecyclerview.setAdapter(mTableAdapter);
    }

    /**
     * 获取到头部数据
     *
     * @param mList
     * @return
     */
    private List<TodoBean> header(List<TodoBean> mList) {
        if (mList != null && mList.size() > 0) {
            headBean = mList.get(0);
            disposeHeader(headBean);
            mList.remove(0);
            EventBus.getDefault().post(new SendEvent(SendEvent.FOOD_LABLE, headBean.getName(), headBean.getDetails().size()));
        }
        return mList;
    }

    /**
     * 适配头部数据
     *
     * @param bean
     */
    private void disposeHeader(TodoBean bean) {
        foodName.setText(bean.getName());
        mTableAdapter.notifyDataSetChanged(bean.getDetails());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.print_breviary_parent)
    public void onClick() {
        onLableClickListenner.onLableClick(OnLableClickListenner.PRINT_LABLE);
    }

    @Override
    public void onResponse(Call<Bean<List<TodoBean>>> call, Response<Bean<List<TodoBean>>> response) {
        Bean<List<TodoBean>> bean = response.body();
        if (bean != null && bean.getCode() == ResponseCode.SUCCESS) {
            List<TodoBean> todoBeen = bean.getResult();
            mTodoAdapter.notifyDataSetChanged(header(todoBeen));
        } else {

        }
    }

    @Override
    public void onFailure(Call<Bean<List<TodoBean>>> call, Throwable t) {

    }

    /**
     * 设置补打缩略信息
     *
     * @param name  菜品名称
     * @param table 台桌
     */
    public void setPrintTitle(String name, String table) {
        breviaryPrintTable.setVisibility(View.VISIBLE);
        breviaryPrintName.setText(name);
        breviaryPrintTable.setText(table);
    }

    /**
     * 设置动画效果
     */
    public void setAnimation() {
//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_in);
////        foodParent.setAnimation(animation);
//        printBreviaryParent.setAnimation(animation);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reviceMessage(SendEvent event) {
        if (event.getType() == SendEvent.FOOD_REFRESH) {
            /**刷新代加工菜品*/
        } else if (event.getType() == SendEvent.PRINT_LABLE) {
            /**刷新打印记录缩略信息*/
            setPrintTitle(event.getName(), event.getTable());
        }
    }


}