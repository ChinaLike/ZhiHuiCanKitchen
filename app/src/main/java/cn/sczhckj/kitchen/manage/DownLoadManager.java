package cn.sczhckj.kitchen.manage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.sczhckj.kitchen.Config;
import cn.sczhckj.kitchen.data.constant.FileConstant;
import cn.sczhckj.kitchen.mode.RetrofitService;
import cn.sczhckj.kitchen.overwrite.ProgressDialog;
import cn.sczhckj.kitchen.until.FileUntils;
import cn.sczhckj.kitchen.until.show.L;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @describe: 下载管理
 * @author: Like on 2016/12/6.
 * @Email: 572919350@qq.com
 */

public class DownLoadManager {

    /**
     * 通过Retrofit下载文件
     *
     * @param apkName
     */
    public void retrofitDownload(String url, final String apkName, final Context mContext) {
        final ProgressDialog dialog = dialog(mContext);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.HOST);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        RetrofitService retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(RetrofitService.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                dialog.setProgressMax((int) (contentLength / 1024));
                dialog.setProgress((int) (bytesRead / 1024));
                dialog.setProgressText(String.format("%1s Kb/%2s Kb", (int) (bytesRead / 1024), (int) (contentLength / 1024)));
                if (done) {
                    File file = new File(FileUntils.getSdPath() + FileConstant.PATH, apkName);
                    install(mContext,dialog,file);
                }
            }
        });

        Call<ResponseBody> call = retrofit.download(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(FileUntils.getSdPath() + FileConstant.PATH, apkName);
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 自定义进度弹窗
     * @param mContext
     * @return
     */
    private ProgressDialog dialog(Context mContext){
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setTitle("版本更新");
        dialog.setProgressVisibility();
        dialog.setContextText("正在更新中...");
        dialog.setAloneButton("等待下载", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        dialog.show();
        return dialog;
    }

    /**
     * 安装应用
     * @param mContext
     * @param dialog
     * @param file
     */
    private void install(final Context mContext, final ProgressDialog dialog, final File file) {
        dialog.setContextText("下载已完成，请确定安装应用！");
        dialog.setAloneButton("安装", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                autoInstall(mContext, file);
            }
        });
    }

    /**
     * 自动安装
     */
    private void autoInstall(Context mContext, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

}
