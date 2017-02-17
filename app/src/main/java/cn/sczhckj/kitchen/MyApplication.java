package cn.sczhckj.kitchen;

import android.app.Application;

import cn.sczhckj.kitchen.manage.ExceptionPush;

/**
 * @ describe:
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**异常提交*/
        ExceptionPush.init(this).openCrashHandler(Config.HOST, "p");
    }
}
