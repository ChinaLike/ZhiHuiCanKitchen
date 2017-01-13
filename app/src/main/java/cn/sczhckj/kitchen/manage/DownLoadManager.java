package cn.sczhckj.kitchen.manage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.sczhckj.kitchen.Config;
import cn.sczhckj.kitchen.mode.RetrofitService;
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
                if (done) {
                    File file = new File(Environment.getExternalStorageDirectory() + "/ZHCDownload/", apkName);
                    autoInstall(mContext, file);
                }
            }
        });

        Call<ResponseBody> call = retrofit.download(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(Environment.getExternalStorageDirectory() + "/ZHCDownload/", apkName);
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
