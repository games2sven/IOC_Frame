package com.highgreat.sven.ioc;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//运行时
@Target(ElementType.TYPE)//类
public @interface ContentView {
    int value();
}
