package com.example.library.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/3/26.
 */
 //对value数组中表示的控件 事件类型方法名是setOnClickListener  事件类型为View.OnClickListener 事件类型回调方法为onClick
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener",listenerType = View.OnClickListener.class,callBack = "onClick")
public @interface OnClick {
    int[] value();
}
