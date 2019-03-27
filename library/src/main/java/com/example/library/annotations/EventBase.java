package com.example.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/3/26.
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    String listenerSetter(); // 类似setOnClickListener  方法名

    Class<?> listenerType(); // 类似View.OnClickListener  事件类型

    String callBack();   //回调方法名 类似onClick()
}
