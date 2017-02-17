package cn.sczhckj.kitchen.mode;

import java.util.List;

import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.ResponseCommonBean;
import cn.sczhckj.kitchen.data.bean.device.VersionBean;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DoneBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @describe: 接口定义
 * @author: Like on 2016/11/4.
 * @Email: 572919350@qq.com
 */

public interface RetrofitService {

    /**
     * 获取代加工菜品
     */
    @FormUrlEncoded
    @POST("rest/kitchen/todo")
    Call<Bean<List<TodoBean>>> todo(@Field("p") String p);

    /**
     * 菜品完成
     */
    @FormUrlEncoded
    @POST("rest/kitchen/finish")
    Call<Bean<ResponseCommonBean>> finish(@Field("p") String p);

    /**
     * 获取补打记录
     */
    @FormUrlEncoded
    @POST("rest/kitchen/done")
    Call<Bean<List<DoneBean>>> done(@Field("p") String p);

    /**
     * 打印
     */
    @FormUrlEncoded
    @POST("rest/kitchen/print")
    Call<Bean<ResponseCommonBean>> print(@Field("p") String p);

    /**
     * 版本管理信息
     */
    @FormUrlEncoded
    @POST("rest/device/update")
    Call<Bean<VersionBean>> version(@Field("p") String p);

    /**
     * 下载APK
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> download(@Url String url);

    /**
     * 异常信息提交
     */
    @FormUrlEncoded
    @POST("rest/device/exception")
    Call<Bean<ResponseCommonBean>> exception(@Field("p") String p);

}
