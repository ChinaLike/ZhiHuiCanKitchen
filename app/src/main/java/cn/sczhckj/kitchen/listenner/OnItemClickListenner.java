package cn.sczhckj.kitchen.listenner;

import android.view.View;

/**
 * @ describe:  RecyclerviewItem点击监听
 * @ author: Like on 2017-01-09.
 * @ email: 572919350@qq.com
 */

public interface OnItemClickListenner {
    void onClick(View v, int postion, Object bean);
}
