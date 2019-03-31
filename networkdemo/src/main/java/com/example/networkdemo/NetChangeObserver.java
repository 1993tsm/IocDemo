package com.example.networkdemo;

/**
 * Created by Administrator on 2019/3/30.
 */

public interface NetChangeObserver {

    void onConnect(NetType netType);
    void disOnConnect();

}
