package com.example.eventbusdemo.annocation;

import com.example.eventbusdemo.ThreadMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscriber {


    ThreadMode threadMode() default ThreadMode.MAIN;
}
