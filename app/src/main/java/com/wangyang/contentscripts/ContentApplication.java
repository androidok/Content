package com.wangyang.contentscripts;

import android.app.Application;

import kotlin.wangyang.com.baselibiary.CrashExceptionHandler;

/**
 * Created by qq1440214507 on 2017/6/21.
 */

public class ContentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashExceptionHandler.getInstance().init(this);
    }
}
