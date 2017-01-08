package cn.sczhckj.kitchen.fragment;

import android.support.v4.app.Fragment;

import cn.sczhckj.kitchen.listenner.OnLableClickListenner;

/**
 * @ describe:
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 标签被点击
     */
    protected OnLableClickListenner onLableClickListenner;

    public void setOnLableClickListenner(OnLableClickListenner onLableClickListenner) {
        this.onLableClickListenner = onLableClickListenner;
    }

}
