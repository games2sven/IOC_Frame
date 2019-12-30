package com.highgreat.sven.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {

    //  setOnClickListener  订阅
    String listenerSetter();

    /**
     * 事件监听的类型
      */
    Class<?> listenerType();

    /**
     * 事件处理
     * @return
     */
    String callbackMethod();

}
