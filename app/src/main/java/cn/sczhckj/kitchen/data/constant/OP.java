package cn.sczhckj.kitchen.data.constant;

/**
 * @ describe:  业务请求参数
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public interface OP {

    /**
     * 后厨菜品
     */
    String KITCHEN_TODO = "todo";
    /**
     * 后厨菜品完成
     */
    String KITCHEN_FINISH = "finish";
    /**
     * 后厨补打记录刷新
     */
    String KITCHEN_DONE = "done";
    /**
     * 后厨补打
     */
    String KITCHEN_PRINT = "print";
    /**
     * 后厨Apk版本更新
     */
    String DEVICE_UPDATE = "update";

    /**
     * 刷新后厨
     */
    String PUSH_REFRESH = "refresh";

    /**
     * APP推送 心跳
     */
    String PUSH_HEART = "heart";
    /**
     * 异常收集
     */
    String EXCEPTION = "exception";

}
