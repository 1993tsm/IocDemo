package com.example.networkdemo.utils;

import com.example.networkdemo.NetType;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/3/30.
 */

public class MethodManager {

    private Class<?>[] aClass;   //参数类型
    private Method method; //方法

    private NetType netType; // 网络类型 //监听某种网络状态

    public MethodManager(Class<?>[] aClass, Method method, NetType netType) {
        this.aClass = aClass;
        this.method = method;
        this.netType = netType;
    }

    public Class<?>[] getaClass() {
        return aClass;
    }

    public void setaClass(Class<?>[] aClass) {
        this.aClass = aClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    @Override
    public String toString() {
        return "MethodManager{" +
                "aClass=" + aClass +
                ", method=" + method +
                ", netType=" + netType +
                '}';
    }
}
