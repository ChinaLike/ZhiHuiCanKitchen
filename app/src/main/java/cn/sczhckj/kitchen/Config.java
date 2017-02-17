package cn.sczhckj.kitchen;

/**
 * @ describe:  接口配置
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public interface Config {

    /**
     * 地址
     */
//    String ADDRESS = "cn.sczhckj.pad.rest/";
    String ADDRESS = "pad/";
    /**
     * IP地址
     */
    String IP = "192.168.0.50";

    /**
     * 主机
     */
    String HOST = "http://" + IP + ":8080/"+ADDRESS;
    /**
     * 后厨推送
     */
    String URL_KITCHEN_SERVICE = "ws://" + IP + ":8080/"+ADDRESS+"ws/kitchen?username=";

    /**
     * 心跳检测
     */
    String URL_HEART_SERVICE = "ws://" + IP + ":8080/"+ADDRESS+"ws/heart?username=";

}
