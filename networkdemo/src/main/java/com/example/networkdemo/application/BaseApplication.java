package com.example.networkdemo.application;

import android.app.Application;

import com.example.networkdemo.utils.NetWorkManager;

/**
 * Created by Administrator on 2019/3/31.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getDefault().initApplication(this);
    }
}
