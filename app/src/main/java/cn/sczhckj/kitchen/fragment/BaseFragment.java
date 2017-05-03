package cn.sczhckj.kitchen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.sczhckj.kitchen.R;
import cn.sczhckj.kitchen.listenner.OnLableClickListenner;

/**
 * @ describe:
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public abstract class BaseFragment extends Fragment {
    /**
     * 视图
     */
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setLayoutId(), null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public abstract int setLayoutId();

    public abstract void init();

    /**
     * 标签被点击
     */
    protected OnLableClickListenner onLableClickListenner;

    public void setOnLableClickListenner(OnLableClickListenner onLableClickListenner) {
        this.onLableClickListenner = onLableClickListenner;
    }

    /**
     * 当点击某一Item时，当前Item滚动到顶部
     *
     * @param postion
     */
    protected void move(int postion , RecyclerView.Adapter adapter , RecyclerView view , LinearLayoutManager manager) {
        if (postion < 0 || postion >= adapter.getItemCount()) {
            return;
        }
        view.stopScroll();
        moveToPosition(postion , view , manager);
    }

    protected void moveToPosition(int postion  , RecyclerView view , LinearLayoutManager manager) {

        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (postion <= firstItem) {
            view.scrollToPosition(postion);
        } else if (postion <= lastItem) {
            int top = view.getChildAt(postion - firstItem).getTop();
            view.scrollBy(0, top);
        } else {
            view.scrollToPosition(postion);
        }

    }

}
