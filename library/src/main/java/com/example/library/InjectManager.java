package com.example.library;

import android.app.Activity;
import android.view.View;

import com.example.library.annotations.ContentView;
import com.example.library.annotations.EventBase;
import com.example.library.annotations.InjectView;
import com.example.library.annotations.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by Administrator on 2019/3/25.
 */

public class InjectManager {

    public static void inject(Activity activity){
        injectLayout(activity);
        injectViews(activity);
        injectEvent(activity);
    }
    //注解布局
    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        int layoutId = 0;
        if(contentView != null) {
            layoutId = contentView.value();
        }

        try {
            Method setContentView = clazz.getMethod("setContentView", int.class);
            setContentView.invoke(activity,layoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields
             ) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            int viewId = 0;
            if(injectView != null){

                viewId = injectView.value();

            }
            Object view = null;
            try {
                Method findViewById = clazz.getMethod("findViewById", int.class);
                view = findViewById.invoke(activity, viewId);
                field.setAccessible(true);
                field.set(activity,view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void injectEvent(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods) {
            Annotation[] annotations = method.getAnnotations(); //获取方法上的所有注解
            for (Annotation annotation: annotations
                 ) {
                Class<? extends Annotation> annotationType = annotation.annotationType(); // 获取OnClick注解上的注解类型
                EventBase eventBase = null;//获取到注解上的注解
                if(annotationType != null) {
                    eventBase = annotationType.getAnnotation(EventBase.class);
                }
                if(eventBase == null) continue;

                //获取元注解的值
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callBack = eventBase.callBack();
                try {
                    Method value = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) value.invoke(annotation);//表示OnClick调用value()方法获取到 value方法的返回值 ({R.id.tv,R.id.bt})
                    for (int viewId : viewIds){
                        //通过activity找到对应viewId的控件
                        View view = activity.findViewById(viewId);
                        //找到view设置的方法 相当于setOnClickListener
                        Method setter = view.getClass().getMethod(listenerSetter, listenerType);//listenerSetter方法名  listenerType 方法的参数
                        //此时需要通过代理 去执行listenerType (也就是 View.OnClickListener) 同时需要InvocationHandler去拦截OnClickListener的回调方法onClick
                        //去执行自定义的方法
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethod(callBack,method); //callBack回调的方法也就是被拦截的方法 类似onClick  method用户在activity中自己定义的方法
                        Object proxyInstance = null;
                        try {
                            proxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        setter.invoke(view,proxyInstance); //view-调用setter方法的控件  proxyInstance-方法参数回调
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }


    }
}
