package cn.sczhckj.kitchen;

/**
 * @ describe:  接口配置
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class Config {

    /**
     * 地址
     */
    public static String ADDRESS = "pad/";

    /**
     * 主机
     */
    public static String HOST = "http://" + host() + ADDRESS;
    /**
     * 后厨推送
     */
    public static String URL_KITCHEN_SERVICE = "ws://" + host() + ADDRESS + "ws/kitchen?username=";

    /**
     * 心跳检测
     */
    public static String URL_HEART_SERVICE = "ws://" + host() + ADDRESS + "ws/heart?username=";

    /**
     * 获取IP
     *
     * @return
     */
    public static String ip() {
        return (String) MyApplication.share.getData("ip", "192.168.0.25");
    }

    public static String port() {
        return ":" + MyApplication.share.getData("port", "8080") + "/";
    }

    public static String host() {
        return ip() + port();
    }

    public static void setHOST() {
        Config.HOST = "http://" + host() + ADDRESS;
    }

    public static void setUrlKitchenService() {
        URL_KITCHEN_SERVICE = "ws://" + host() + ADDRESS + "ws/kitchen?username=";
    }

    public static void setUrlHeartService() {
        URL_HEART_SERVICE = "ws://" + host() + ADDRESS + "ws/heart?username=";
    }
}
