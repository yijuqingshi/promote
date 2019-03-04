package com.ztdh.promote;

import android.app.Activity;
import android.app.Application;

import com.common.app.ActivityLifecycleManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.ztdh.promote.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class PromoteApp extends Application {

    private static PromoteApp mInstance;

    private static List<Activity> activities = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SharePreferenceUtils.getInstance(this,"promote_sharepreference");
        registerActivityLifecycleCallbacks(new ActivityLifecycleManager());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    public static void  addActivity(Activity activity){
            activities.add(activity);
    }

    public static void  removeActivity(Activity activity){
         activities.remove(activity);
    }

    public static void  fiinishAll(){
        for (Activity activity : activities) {
            activity.finish();
        }
    }
}
