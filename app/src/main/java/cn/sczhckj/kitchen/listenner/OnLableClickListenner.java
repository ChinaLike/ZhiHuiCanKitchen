package cn.sczhckj.kitchen.listenner;

/**
 * @ describe:  标签点击
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public interface OnLableClickListenner {
    /**
     * 菜品
     */
    int FOOD_LABLE = 0;
    /**
     * 小票
     */
    int PRINT_LABLE = 1;

    void onLableClick(int type);
}
