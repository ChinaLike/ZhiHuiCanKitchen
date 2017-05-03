package cn.sczhckj.kitchen.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.activity.MainActivity;
import cn.sczhckj.kitchen.adapter.DebookAdapter;
import cn.sczhckj.kitchen.animation.AnimationImpl;
import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.kitchen.DebookBean;
import cn.sczhckj.kitchen.data.event.SendEvent;
import cn.sczhckj.kitchen.data.response.ResponseCode;
import cn.sczhckj.kitchen.mode.KitchenMode;
import cn.sczhckj.kitchen.overwrite.DashlineItemDivider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ describe: 退菜详情界面
 * @ author: Like on 2017-04-28.
 * @ email: 572919350@qq.com
 */

public class DebookFragment extends BaseFragment implements Callback<Bean<List<DebookBean>>>{

    @Bind(R.id.debook_list)
    RecyclerView debookList;
    /**
     * 退菜列表
     */
    private DebookAdapter mAdapter;
    /**
     * 布局管理器
     */
    private LinearLayoutManager manager;
    /**
     * 数据获取
     * @return
     */
    private KitchenMode mKitchenMode;
    /**
     * 动画
     */
    private AnimationImpl animation;
    /**
     * 退菜记录
     */
    private List<DebookBean> mList = new ArrayList<>();
    /**
     * 默认下标
     */
    private int index = -1;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_debook;
    }

    @Override
    public void init() {
        initData();

        animation = new AnimationImpl(getContext());

        mAdapter = new DebookAdapter(getContext(),null);
        manager = new LinearLayoutManager(getContext());
        debookList.setLayoutManager(manager);
        debookList.setAdapter(mAdapter);
        debookList.addItemDecoration(new DashlineItemDivider(ContextCompat.getColor(getContext(), R.color.line), 100000, 1));
    }

    /**
     * 获取数据
     */
    private void initData(){
        mKitchenMode = new KitchenMode(getContext());
        mKitchenMode.debook(this);
    }

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
     * 退菜广播处理
     * @param event
     */
    public void debookEvent(SendEvent event) {
        if (event.getType() == SendEvent.PUSH_DEBOOK) {
            /**刷新退菜信息*/
            initData();
        } else if (event.getType() == SendEvent.KEY_AFFIRM && MainActivity.currentView == MainActivity.DEBOOK) {

        } else if (event.getType() == SendEvent.KEY_NEXT && MainActivity.currentView == MainActivity.DEBOOK) {
            /**下一个*/
            next();
        } else if (event.getType() == SendEvent.KEY_PRE && MainActivity.currentView == MainActivity.DEBOOK) {
            /**上一个*/
            pre();
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
            if (i == index) {
                mList.get(i).setSelect(true);
            } else {
                mList.get(i).setSelect(false);
            }
        }
        mAdapter.notifyDataSetChanged(mList);
        move(index, mAdapter, debookList, manager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResponse(Call<Bean<List<DebookBean>>> call, Response<Bean<List<DebookBean>>> response) {
        Bean<List<DebookBean>> bean = response.body();
        if (bean != null && bean.getCode() == ResponseCode.SUCCESS){
            index = -1;
            mList = bean.getResult();
            mAdapter.notifyDataSetChanged(mList);
        }else if (bean != null && bean.getCode() == ResponseCode.FAILURE){

        }else {

        }
    }

    @Override
    public void onFailure(Call<Bean<List<DebookBean>>> call, Throwable t) {

    }
}
