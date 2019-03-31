package com.example.networkdemo;

/**
 * Created by Administrator on 2019/3/30.
 */

public enum NetType {


    AUTO, // 只要有网络不关心WiFi还是移动网络
    WIFI,
    CMNET, //PC  只是根据业务来区分的网络状态
    CMWAP, // 手机上网 只是根据业务来区分的网络状态
    NONE
}
