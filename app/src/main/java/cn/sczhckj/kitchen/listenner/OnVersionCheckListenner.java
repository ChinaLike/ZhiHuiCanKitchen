package cn.sczhckj.kitchen.listenner;

/**
 * @ describe: 版本检测监听
 * @ author: Like on 2017-01-12.
 * @ email: 572919350@qq.com
 */

public interface OnVersionCheckListenner {
    void onSuccess(String context);

    void onFail();
}
