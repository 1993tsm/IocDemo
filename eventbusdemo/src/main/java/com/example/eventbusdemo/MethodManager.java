package com.example.eventbusdemo;

import com.example.eventbusdemo.annocation.Subscriber;

import java.lang.reflect.Method;

/**
 * 封裝方法的管理类
 */
public class MethodManager {



    private Method mMethod; //方法对象
    private Class<?> mClazz; // 方法参数类型

    Subscriber mSubscriber;//方法的注注解 表示线程模式

    public MethodManager(Method method, Class<?> clazz, Subscriber subscriber) {
        this.mMethod = method;
        this.mClazz = clazz;
        this.mSubscriber = subscriber;
    }

    public Method getmMethod() {
        return mMethod;
    }

    public Class<?> getmClazz() {
        return mClazz;
    }

    public Subscriber getmSubscriber() {
        return mSubscriber;
    }

    @Override
    public String toString() {
        return "MethodManager{" +
                "mMethod=" + mMethod +
                ", mClazz=" + mClazz +
                ", mSubscriber=" + mSubscriber +
                '}';
    }
}
