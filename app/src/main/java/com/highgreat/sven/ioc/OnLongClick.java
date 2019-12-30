package com.highgreat.sven.ioc;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerType = View.OnLongClickListener.class
        ,listenerSetter =  "setOnLongClickListener",callbackMethod = "onLongClick")
public @interface OnLongClick {
    int[] value() default -1;
}
