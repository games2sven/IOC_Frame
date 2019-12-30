package com.highgreat.sven.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ListenerInvocationHandler implements InvocationHandler {

    private Object activity;
    private  Method activityMethod; //Onclick

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在这里调用Activity下面注解了的click方法
        return activityMethod.invoke(activity,args);
    }
}
