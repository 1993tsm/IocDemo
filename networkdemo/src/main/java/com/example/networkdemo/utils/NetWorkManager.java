package com.example.networkdemo.utils;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.example.networkdemo.NetChangeObserver;
import com.example.networkdemo.NetStateReceiver;
import com.example.networkdemo.core.NetWorkCallBackImp;

/**
 * Created by Administrator on 2019/3/30.
 */

public class NetWorkManager {

    private NetStateReceiver netStateReceiver;
    private static volatile NetWorkManager instance;

    private Application application;
    private NetWorkManager(){
        netStateReceiver = new NetStateReceiver();
    }


    public static NetWorkManager getDefault() {
        if(null == instance){
            synchronized (NetWorkManager.class){
                instance = new NetWorkManager();
                return instance;
            }
        }
        return instance;
    }

    public void initApplication(Application application){
        if(null == application )return;
        this.application = application;

//        //注册广播
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
//        application.registerReceiver(netStateReceiver,intentFilter);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){  //版本21以上
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            ConnectivityManager.NetworkCallback networkCallback = new NetWorkCallBackImp();

            NetworkRequest netWorkRequest = new NetworkRequest.Builder().build();
            if(null != connectivityManager) {
                connectivityManager.registerNetworkCallback(netWorkRequest, networkCallback);
            }

        }
    }

    public Application getApplication() {
        if(null== application){
            throw new RuntimeException("未初始化application");
        }
        return  application;
    }

    public void setNetChangeListener(NetChangeObserver netChangeListener){
        if(netStateReceiver != null){
            netStateReceiver.setmNetChangeObserver(netChangeListener);
        }
    }


    public void unRegisterReceiver(Object object){
        netStateReceiver.unRegisterReceiver(object);
    }

    public void unRegisterAllReceiver(Object object){
        netStateReceiver.unRegisterAllReceiver(object);
    }
    public void registerReceiver(Object object){
        netStateReceiver.registerReceiver(object);
    }

}
