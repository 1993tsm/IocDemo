package com.example.networkdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.networkdemo.annotation.NetWork;
import com.example.networkdemo.utils.Constants;
import com.example.networkdemo.utils.MethodManager;
import com.example.networkdemo.utils.NetWorkManager;
import com.example.networkdemo.utils.NetWorkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2019/3/30.
 */

public class NetStateReceiver extends BroadcastReceiver {

    private NetType mNetType;

    private NetChangeObserver mNetChangeObserver;

    private HashMap<Object,List<MethodManager>> hashMap;
    public NetStateReceiver() {
        hashMap = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(null == intent || intent.getAction() == null){
            mNetType = NetWorkUtils.getNetType();

            if(NetWorkUtils.isNetWorkAvailable()){
//                mNetChangeObserver.onConnect(NetWorkUtils.getNetType());
            }else{
//                mNetChangeObserver.disOnConnect();
            }
            postNetType(mNetType);
        }
    }

    public void postNetType(NetType mNetType) {
        if(null != hashMap){
            Set<Object> keySet = hashMap.keySet();
            for (Object object:keySet) {
                List<MethodManager> methodManagers = hashMap.get(object);
                if(methodManagers != null){
                    for (MethodManager m:methodManagers) {
                        Method method = m.getMethod();
                        Class<?>[] aClass = m.getaClass();
                        NetType netType = m.getNetType();
                        try {
                            if((m.getNetType()).getClass().isAssignableFrom(mNetType.getClass())) { //判断方法的参数类型是否跟我们拿到的网络类型匹配
                                switch (netType){  //判断方法上注解NetWork的值 表明我们的方法需要监听的网络类型
                                    case AUTO:
                                        if(mNetType != NetType.NONE){
                                            Log.e(Constants.LOG_TAG,"连接上了网络，不区分网络类型");
                                        }
                                        break;
                                    case WIFI:
                                        if(mNetType == NetType.WIFI){
                                            Log.e(Constants.LOG_TAG,"连接上了WIFI");
                                        }
                                        break;
                                    case CMNET:
                                    case CMWAP:
                                        if(mNetType == NetType.CMNET || mNetType == NetType.CMWAP){
                                            Log.e(Constants.LOG_TAG,"连接上了移动网络，并非WIFI");
                                        }
                                        break;
                                }
                                method.invoke(object, mNetType);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    //在netWorkManager中进行初始化 并注册广播
    public void setmNetChangeObserver(NetChangeObserver mNetChangeObserver) {
        this.mNetChangeObserver = mNetChangeObserver;
    }

    public void setmNetType(NetType mNetType) {
        this.mNetType = mNetType;
    }


    public void unRegisterReceiver(Object object){
        if(null != object && !hashMap.isEmpty()) {
            hashMap.remove(object);
        }
    }

    public void unRegisterAllReceiver(Object object){
        if(!hashMap.isEmpty()){
            hashMap.clear();

        }
        NetWorkManager.getDefault().getApplication().unregisterReceiver(this);
    }

    /**
     * 需要注册广播接收者的类  activity 或者是fragment都可
     * @param object
     */
    public void registerReceiver(Object object){

        List<MethodManager> methodManagers = hashMap.get(object); // 获取到所有被netWork注解的方法的集合

        if(methodManagers == null){  //如果是首次注册
            methodManagers = new ArrayList<>();
            //将所有被注解所标记的方法全部找出来
                    Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {  //遍历 类中所有的方法
            NetWork methodAnnotation = method.getAnnotation(NetWork.class);
            if(null == methodAnnotation){
                continue;
            }
            //获取方法的返回值   如果返回值不是void 那么 继续 continue
            Type returnType = method.getGenericReturnType();
            if(!"void".equalsIgnoreCase(returnType.toString())){
                Log.e(Constants.LOG_TAG,"方法返回值不是void");
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length != 1){
                Log.e(Constants.LOG_TAG,"方法不是唯独只有1个参数的类型方法");
                continue;
            }
            //假设我们需要监听的方法是有且只有一个参数的方法
            MethodManager methodManager = new MethodManager(parameterTypes,method,methodAnnotation.netType());
            methodManagers.add(methodManager);
        }


        hashMap.put(object,methodManagers);
        }
    }
}
