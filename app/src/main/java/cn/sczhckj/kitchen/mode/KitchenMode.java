package cn.sczhckj.kitchen.mode;

import android.content.Context;

import java.util.List;

import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.RequestCommonBean;
import cn.sczhckj.kitchen.data.bean.ResponseCommonBean;
import cn.sczhckj.kitchen.data.bean.device.VersionBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DebookBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DoneBean;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;
import cn.sczhckj.kitchen.data.constant.OP;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.platform.rest.io.RestRequest;
import cn.sczhckj.platform.rest.io.json.JSONRestRequest;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @ describe: 后厨数据请求
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class KitchenMode {

    private Context mContext;

    public KitchenMode() {
    }

    public KitchenMode(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取待加工菜品列表
     *
     * @param callback 回调
     */
    public void todo(Callback<Bean<List<TodoBean>>> callback) {
        RequestCommonBean bean = new RequestCommonBean();
        bean.setDeviceId(AppSystemUntil.getAndroidID(mContext));
        RestRequest<RequestCommonBean> restRequest = JSONRestRequest.Builder.build(RequestCommonBean.class)
                .op(OP.KITCHEN_TODO)
                .time()
                .bean(bean);
        Call<Bean<List<TodoBean>>> call = RetrofitRequest.service().todo(restRequest.toRequestString());
        call.enqueue(callback);
    }

    /**
     * 菜品完成
     *
     * @param bean     参数对象
     * @param callback 回调
     */
    public void finish(RequestCommonBean bean, Callback<Bean<ResponseCommonBean>> callback) {
        RestRequest<RequestCommonBean> restRequest = JSONRestRequest.Builder.build(RequestCommonBean.class)
                .op(OP.KITCHEN_FINISH)
                .time()
                .bean(bean);
        Call<Bean<ResponseCommonBean>> call = RetrofitRequest.service().finish(restRequest.toRequestString());
        call.enqueue(callback);
    }

    /**
     * 刷新已完成菜品补打记录
     *
     * @param callback 回调
     */
    public void done(Callback<Bean<List<DoneBean>>> callback) {
        RequestCommonBean bean = new RequestCommonBean();
        bean.setDeviceId(AppSystemUntil.getAndroidID(mContext));
        RestRequest<RequestCommonBean> restRequest = JSONRestRequest.Builder.build(RequestCommonBean.class)
                .op(OP.KITCHEN_DONE)
                .time()
                .bean(bean);
        Call<Bean<List<DoneBean>>> call = RetrofitRequest.service().done(restRequest.toRequestString());
        call.enqueue(callback);
    }

    /**
     * 补打
     *
     * @param bean     参数对象
     * @param callback 回调
     */
    public void print(RequestCommonBean bean, Callback<Bean<ResponseCommonBean>> callback) {
        RestRequest<RequestCommonBean> restRequest = JSONRestRequest.Builder.build(RequestCommonBean.class)
                .op(OP.KITCHEN_PRINT)
                .time()
                .bean(bean);
        Call<Bean<ResponseCommonBean>> call = RetrofitRequest.service().print(restRequest.toRequestString());
        call.enqueue(callback);
    }

    /**
     * 获取退菜清单
     * @param callback
     */
    public void debook(Callback<Bean<List<DebookBean>>> callback) {
        RequestCommonBean bean = new RequestCommonBean();
        bean.setDeviceId(AppSystemUntil.getAndroidID(mContext));
        RestRequest<RequestCommonBean> restRequest = JSONRestRequest.Builder.build(RequestCommonBean.class)
                .op(OP.KITCHEN_DEBOOK)
                .time()
                .bean(bean);
        Call<Bean<List<DebookBean>>> call = RetrofitRequest.service().debook(restRequest.toRequestString());
        call.enqueue(callback);
    }

    /**
     * 版本更新
     *
     * @param bean     参数对象
     * @param callback 回调
     */
    public void version(RequestCommonBean bean, Callback<Bean<VersionBean>> callback) {
        RestRequest<RequestCommonBean> restRequest = JSONRestRequest.Builder.build(RequestCommonBean.class)
                .op(OP.DEVICE_UPDATE)
                .time()
                .bean(bean);
        Call<Bean<VersionBean>> call = RetrofitRequest.service().version(restRequest.toRequestString());
        call.enqueue(callback);
    }

}
