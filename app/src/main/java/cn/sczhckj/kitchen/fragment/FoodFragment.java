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
import cn.sczhckj.kitchen.activity.MainActivity;
import cn.sczhckj.kitchen.adapter.TableAdapter;
import cn.sczhckj.kitchen.adapter.TodoAdapter;
import cn.sczhckj.kitchen.animation.AnimationImpl;
import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.ResponseCommonBean;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.data.response.ResponseCode;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;
import cn.sczhckj.kitchen.mode.KitchenImpl;
import cn.sczhckj.kitchen.mode.KitchenMode;
import cn.sczhckj.kitchen.overwrite.DashlineItemDivider;
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

    private View view;

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

    /**
     * 出菜数据请求
     */
    private KitchenImpl mKitchen;
    /**
     * 待加工所有菜品
     */
    private List<TodoBean> todoBeen = new ArrayList<>();
    /**
     * 动画
     */
    private AnimationImpl animation;
    /**
     * 当前显示位置
     */
    private int currentPosition = -1;
    /**
     * 当前需要出菜
     */
    private TodoBean currentBean = new TodoBean();
    /**
     * 布局管理器
     */
    private LinearLayoutManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, null, false);
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
        animation = new AnimationImpl(getContext());
        mKitchenMode = new KitchenMode(getContext());
        mKitchen = new KitchenImpl(getContext());
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
        manager = new LinearLayoutManager(getContext());
        foodRecyclerview.setLayoutManager(manager);
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
            /**每一次刷新时，如果不操作，将会默认选择第一个*/
            currentBean = headBean;
        } else {
            foodName.setText("暂无菜品");
            mTableAdapter.notifyDataSetChanged(null);
            EventBus.getDefault().post(new SendEvent(SendEvent.FOOD_LABLE, "暂无菜品", 0));
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
        mTableAdapter.handleShow(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            todoBeen = bean.getResult();
            mTodoAdapter.notifyDataSetChanged(header(todoBeen));
        } else {

        }
    }

    @Override
    public void onFailure(Call<Bean<List<TodoBean>>> call, Throwable t) {

    }

    /**
     * 出菜回调
     */
    Callback<Bean<ResponseCommonBean>> finishCallback = new Callback<Bean<ResponseCommonBean>>() {
        @Override
        public void onResponse(Call<Bean<ResponseCommonBean>> call, Response<Bean<ResponseCommonBean>> response) {
            Bean<ResponseCommonBean> bean = response.body();
            if (bean != null && bean.getCode() == ResponseCode.SUCCESS) {
                T.showCenterShort(getContext(), "出菜成功");
                /**出菜成功刷新菜品*/
                initTodo();
                EventBus.getDefault().post(new SendEvent(SendEvent.FOOD_FINISH));
            } else if (bean != null && bean.getCode() == ResponseCode.FAILURE) {
                T.showCenterShort(getContext(), bean.getMessage());
            } else {
                T.showCenterShort(getContext(), "出菜未成功，请重新出菜");
            }
        }

        @Override
        public void onFailure(Call<Bean<ResponseCommonBean>> call, Throwable t) {
            T.showCenterShort(getContext(), "网络异常，请稍后再试！");
        }
    };

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
     * 设置进入动画效果
     */
    public void setAnimationIn() {
        animation.upIn(view);
    }

    /**
     * 设置退出动画效果
     */
    public void setAnimationOut() {
        animation.upOut(view);
    }

    /**
     * 消息判断
     * @param event
     */
    public void foodEvent(SendEvent event) {
        if (event.getType() == SendEvent.FOOD_REFRESH) {
            /**刷新代加工菜品*/
            initTodo();
        } else if (event.getType() == SendEvent.PRINT_LABLE) {
            /**刷新打印记录缩略信息*/
            setPrintTitle(event.getName(), event.getTable());
        } else if (event.getType() == SendEvent.KEY_AFFIRM && MainActivity.isFoodView) {
            /**出菜，出菜顺序是按照第一项第一桌顺序出菜*/
            mKitchen.foodFinish(currentBean, 0, finishCallback);
        } else if (event.getType() == SendEvent.KEY_NEXT && MainActivity.isFoodView) {
            /**下一个*/
            next();
        } else if (event.getType() == SendEvent.KEY_PRE && MainActivity.isFoodView) {
            /**上一个*/
            pre();
        } else if (event.getType() == SendEvent.NON_AUTO_FOOD_FINISH) {
            /**手动点击菜品完成*/
            if (event.isHeader()) {
                /**头部数据*/
                mKitchen.foodFinish(headBean, event.getPostion(), finishCallback);
            } else {
                /**非头部数据*/
                mKitchen.foodFinish(event.getBean(), 0, finishCallback);
            }
        }
    }

    /**
     * 下一个
     */
    private void next() {
        int size = todoBeen.size();//出去头部数据
        if (currentPosition < (size - 1)) {
            currentPosition++;
            mTableAdapter.handleShow(false);
        } else {
            currentPosition = -1;
            mTableAdapter.handleShow(true);
        }
        refreshAdapter(currentPosition);
    }

    /**
     * 上一个
     */
    private void pre() {
        if (currentPosition > 0) {
            currentPosition--;
            mTableAdapter.handleShow(false);
        } else {
            currentPosition = -1;
            mTableAdapter.handleShow(true);
        }
        refreshAdapter(currentPosition);
    }

    /**
     * 刷新适配
     *
     * @param index
     */
    private void refreshAdapter(int index) {
        for (int i = 0; i < todoBeen.size(); i++) {
            if (index == -1) {
                todoBeen.get(i).setSelect(false);
                currentBean = headBean;
            } else {
                currentBean = todoBeen.get(index);
                if (i == index) {
                    todoBeen.get(i).setSelect(true);
                } else {
                    todoBeen.get(i).setSelect(false);
                }
            }
        }
        mTodoAdapter.notifyDataSetChanged(todoBeen);
        /**把最新项移动到顶端*/
        move(index, mTodoAdapter, foodRecyclerview, manager);
    }


}
