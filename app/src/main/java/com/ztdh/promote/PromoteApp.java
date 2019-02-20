package com.ztdh.promote;

import android.app.Application;

import com.common.app.ActivityLifecycleManager;
import com.ztdh.promote.utils.SharePreferenceUtils;

public class PromoteApp extends Application {

    private static PromoteApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SharePreferenceUtils.getInstance(this,"promote_sharepreference");
        registerActivityLifecycleCallbacks(new ActivityLifecycleManager());
    }
}
