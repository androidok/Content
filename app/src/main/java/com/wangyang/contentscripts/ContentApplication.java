package com.wangyang.contentscripts;

import android.app.Application;

import com.wangyangLibrary.hotfix.HotFixManager;

import kotlin.wangyang.com.baselibiary.CrashExceptionHandler;

/**
 * Created by qq1440214507 on 2017/6/21.
 */

public class ContentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashExceptionHandler.getInstance().init(this);
        try {
            HotFixManager.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
