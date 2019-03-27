package com.example.library.annotations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Administrator on 2019/3/27.
 */

public class ListenerInvocationHandler implements InvocationHandler {

    private Object target; // 需要拦截的方法所在的类 也就是哪个activity
    //key表示需要拦截的方法名称，Method表示自定义执行的方法
    private HashMap<String,Method> hashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }
    public ListenerInvocationHandler() {
    }

    /**
     *
     * @param proxy
     * @param method 本应该执行的方法  也就是现在我们需要拦截的方法
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target != null){
            String name = method.getName();
            method = hashMap.get(name); //获取我们自定义的方法  直接赋值给method

        }else{
            Exception e = new Exception("未设置调用的方法的目标类");
            e.printStackTrace();
        }
        if(method!= null){
            return method.invoke(target,args); // 这里的参数对应的是类中用户自定义的方法的参数 为一个数组，所以
        }
      return method.invoke(proxy,args);
    }
    public void addMethod(String methodName,Method method){
        hashMap.put(methodName,method);
    }
}
