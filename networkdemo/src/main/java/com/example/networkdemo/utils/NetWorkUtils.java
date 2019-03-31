package com.example.networkdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.networkdemo.NetType;

/**
 * Created by Administrator on 2019/3/30.
 */

public class NetWorkUtils {


    /**
     * 网络状态是否可用
     * @return
     */
    public static boolean isNetWorkAvailable(){

        try {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                if(null == connectivityManager) return false;
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                if(null != networkInfos) {
                    for (NetworkInfo networkInfo : networkInfos) {
                        if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static NetType getNetType(){
        ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null == connectivityManager) return NetType.NONE;

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();//获取当前激活的网络状态
        if(null == activeNetworkInfo) return NetType.NONE;

        int type = activeNetworkInfo.getType();
        if(type == ConnectivityManager.TYPE_MOBILE) { // 移动网络状态
            if(activeNetworkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                return NetType.CMNET;
            }else {
                return NetType.CMWAP;
            }
        }else if(type == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
