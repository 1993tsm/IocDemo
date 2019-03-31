package com.example.networkdemo.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * Created by tsm on 2019/3/31. 网络监听的回调
 * onAvailable  onLost 成对出现的
 */

public class NetWorkCallBackImp extends ConnectivityManager.NetworkCallback {


    /**
     * 网络连接了
     * @param network
     */
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
    }

    /**
     * 网络中断了
     *
     * @param network
     */
    @Override
    public void onLost(Network network) {
        super.onLost(network);
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
    }

    /**
     * 网络状态改变了
     * @param network
     * @param networkCapabilities
     */
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){ //wifi网络状态

            }else {

            }
        }
    }
}
