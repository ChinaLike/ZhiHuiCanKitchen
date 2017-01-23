package cn.sczhckj.kitchen.listenner;

/**
 * @ describe: 版本检测监听
 * @ author: Like on 2017-01-12.
 * @ email: 572919350@qq.com
 */

public interface OnVersionCheckListenner {
    /**
     * 初始化成功
     */
    int INIT_SUCCESS = 0;
    /**
     * 初始化失败
     */
    int INIT_FAIL = 1;
    /**
     * 初始化有更新
     */
    int INIT_UPDATA = 2;

    void onSuccess(String context,int type);

    void onFail();
}
