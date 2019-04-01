package com.example.eventbusdemo;

import android.util.Log;

import com.example.eventbusdemo.annocation.Subscriber;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventBus {
    private final String TAG = "EventBus";

    public static volatile EventBus instance = null;

    private Map<Object,List<MethodManager>> objectListMap;

    private EventBus(){
        objectListMap = new HashMap<>();
    }

    public static EventBus getDefault(){
        if(instance == null){
            synchronized (EventBus.class){
                instance = new EventBus();
            }
        }
        return instance;
    }


    public void register(Object object) {

        List<MethodManager> list = objectListMap.get(object);
        if(list == null){
            list = findAnnotationMethod(object);
            objectListMap.put(object,list);
        }

    }

    private List<MethodManager> findAnnotationMethod(Object object) {
        List<MethodManager> list = new ArrayList<>();
        // 沒有注册
        Class<?> objectClass = object.getClass(); //获取要注解的类的class对象
        Method[] declaredMethods = objectClass.getDeclaredMethods();// 获取该类的所有方法
        for (Method method:declaredMethods) { //循环所有的方法

            Subscriber methodAnnotation = method.getAnnotation(Subscriber.class);
            if(methodAnnotation == null){ //如果方法没有Subscriber 注解 循环下一个方法
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length != 1){
                throw new RuntimeException("注解方法只允许1个参数"); //在一个条件语句中抛出异常，则程序能被编译，但后面的语句不会被执行
            }
            //满足了以上两个条件之后才是我们需要的方法  此时将方法添加到集合
            MethodManager methodManager = new MethodManager(method,parameterTypes[0],methodAnnotation);

            list.add(methodManager);
        }

        return list;
    }


    /**
     * 向注册了EventBus的类的符合要求的方法派发消息
     * @param object
     */
    public void postMessage(Object object) {
        if(objectListMap != null) {
            Set<Object> set = objectListMap.keySet(); //获取所有注册了EventBus的注冊类集合
            Iterator<Object> iterator = set.iterator();
            while (iterator.hasNext()){
                Object next = iterator.next(); // key值 也就是循环的注册EventBus的类
                List<MethodManager> list = objectListMap.get(next);// 获取需要派发消息的方法集合
                for (MethodManager methodManager:list) {
                    Method method = methodManager.getmMethod(); // 获取对应的方法
                    Class<?> clazz = methodManager.getmClazz(); // 获取方法的类型
                    if(clazz.isAssignableFrom(object.getClass())){ //判断我们发送的消息类型是否跟我们定义的方法的参数类型一致
                        try {
                            method.invoke(next,object); //获取到了需要派发的方法 直接执行即可
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG,e.toString());
                        }
                    }


                }
            }
        }

    }

    public void unRegister(Object object) {
        if(objectListMap != null){
            List<MethodManager> methodManagers = objectListMap.get(object);
            methodManagers.clear();
        }
    }
}
