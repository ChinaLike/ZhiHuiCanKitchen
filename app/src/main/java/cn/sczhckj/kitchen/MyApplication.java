package cn.sczhckj.kitchen;

import android.app.Application;

import cn.sczhckj.kitchen.data.constant.FileConstant;
import cn.sczhckj.kitchen.manage.ExceptionPush;
import cn.sczhckj.kitchen.mode.StorageImpl;

/**
 * @ describe:
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class MyApplication extends Application {

    public static StorageImpl share;

    @Override
    public void onCreate() {
        super.onCreate();
        share = new StorageImpl(getApplicationContext(), FileConstant.USER);
        /**异常提交*/
        ExceptionPush.init(this).openCrashHandler(Config.HOST, "p");
    }
}
