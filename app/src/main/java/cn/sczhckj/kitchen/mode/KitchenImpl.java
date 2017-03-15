package cn.sczhckj.kitchen.mode;

import android.content.Context;

import cn.sczhckj.kitchen.data.bean.Bean;
import cn.sczhckj.kitchen.data.bean.RequestCommonBean;
import cn.sczhckj.kitchen.data.bean.ResponseCommonBean;
import cn.sczhckj.kitchen.data.bean.device.VersionBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DoneBean;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;
import cn.sczhckj.kitchen.data.constant.FileConstant;
import cn.sczhckj.kitchen.data.response.ResponseCode;
import cn.sczhckj.kitchen.listenner.OnVersionCheckListenner;
import cn.sczhckj.kitchen.manage.DownLoadManager;
import cn.sczhckj.kitchen.until.AppSystemUntil;
import cn.sczhckj.kitchen.until.show.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ describe:  菜品完成、补打实现
 * @ author: Like on 2017-01-09.
 * @ email: 572919350@qq.com
 */

public class KitchenImpl {

    private Context mContext;

    private KitchenMode mKitchenMode;
    /**
     * 版本类型0-点菜端 1-后厨端
     */
    private final int VERSION_TYPE = 1;
    /**
     * 版本更新内容
     */
    private VersionBean mVersionBean;

    public KitchenImpl(Context mContext) {
        this.mContext = mContext;
        mKitchenMode = new KitchenMode();
    }

    /**
     * 请求数据请求
     *
     * @param todoBean Item项
     * @param index    餐桌下标
     * @param callback 回调参数
     */
    public void foodFinish(TodoBean todoBean, int index, Callback<Bean<ResponseCommonBean>> callback) {
        RequestCommonBean bean = new RequestCommonBean();
//        bean.setDeviceId(AppSystemUntil.getAndroidID(mContext));
//        bean.setFoodId(todoBean.getFoodId());
//        bean.setCateId(todoBean.getCateId());
//        bean.setDetail(todoBean.getDetails().get(index));
        bean.setTaskSubId(todoBean.getDetails().get(index).getTaskSubId());
        mKitchenMode.finish(bean, callback);
    }

    /**
     * 补打请求
     *
     * @param doneBean 参数
     * @param callback 回调
     */
    public void print(DoneBean doneBean, Callback<Bean<ResponseCommonBean>> callback) {
        RequestCommonBean bean = new RequestCommonBean();
        bean.setDeviceId(AppSystemUntil.getAndroidID(mContext));
        bean.setFoodId(doneBean.getFoodId());
        bean.setCateId(doneBean.getCateId());
        bean.setRecordId(doneBean.getRecordId());
        bean.setDetailId(doneBean.getDetailId());
        bean.setName(doneBean.getName());
        bean.setTableId(doneBean.getTableId());
        bean.setTableName(doneBean.getTableName());
        bean.setTaskSubId(doneBean.getTaskSubId());
        mKitchenMode.print(bean, callback);
    }

    /**
     * 检查版本
     */
    public void checkVersion(final OnVersionCheckListenner onVersionCheckListenner) {
        RequestCommonBean bean = new RequestCommonBean();
        bean.setDeviceId(AppSystemUntil.getAndroidID(mContext));
        bean.setType(VERSION_TYPE);
        mKitchenMode.version(bean, new Callback<Bean<VersionBean>>() {
            @Override
            public void onResponse(Call<Bean<VersionBean>> call, Response<Bean<VersionBean>> response) {
                Bean<VersionBean> bean = response.body();
                L.d("测试===" + bean);
                if (bean != null && bean.getCode() == ResponseCode.SUCCESS) {
                    if (bean.getResult() != null && bean.getResult().getCode() != null &&
                            bean.getResult().getCode() > AppSystemUntil.getVersionCode(mContext)) {
                        mVersionBean = bean.getResult();
                        onVersionCheckListenner.onSuccess("初始化成功，系统有更新...", onVersionCheckListenner.INIT_UPDATA);
                    } else {
                        onVersionCheckListenner.onSuccess("初始化成功，正在进入中...", onVersionCheckListenner.INIT_SUCCESS);
                    }
                } else {
                    onVersionCheckListenner.onSuccess("初始化成功，正在进入中...", onVersionCheckListenner.INIT_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<Bean<VersionBean>> call, Throwable t) {
                onVersionCheckListenner.onFail();
            }
        });
    }

    /**
     * 判断名字是否符合规范
     *
     * @param name
     * @return
     */
    public String judgeName(String name) {
        if (name != null && name.endsWith(".apk")) {
            return name;
        } else {
            return FileConstant.APK_NAME;
        }
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public VersionBean getVersionBean() {
        return mVersionBean;
    }
}
