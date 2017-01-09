package cn.sczhckj.kitchen.until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;

/**
 * @ Describe:获取系统有关数据
 * Created by Like on 2016/11/3.
 * @ Email: 572919350@qq.com
 */

public class AppSystemUntil {

    /**
     * 判断网络是否链接
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 获取收设备唯一标识
     *
     * @param mContext
     * @return
     */
    public static String getAndroidID(Context mContext) {
//        return Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);
        return "1234567890";
    }

    /**
     * 屏幕的宽度
     * @param mContext
     * @return
     */
    public static int width(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 屏幕的高度
     * @param mContext
     * @return
     */
    public static int height(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取软件版本号
     */
    public static int getVersionCode(Context mContext) {
        int verCode = -1;
        try {
            verCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }
}
