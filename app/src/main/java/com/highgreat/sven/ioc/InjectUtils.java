package com.highgreat.sven.ioc;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {

    public static void inject(Object object){
        injectLayout(object);
        injectView(object);
        injectClick(object);
    }

    /**
     * 事件注入
     * @param object
     */
    private static void injectClick(Object object) {
        Class<?> aClass = object.getClass();
        //得到类的所有方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            //得到方法上的所有注解(避免写死)
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //创建一个事件类的超类，所有事件都是一个EventBase
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                //如果没有eventBase，则表示当前方法不是一个处理事件的方法
                if(eventBase==null) { 
                    continue;
                }
                // 用于确定是哪种事件(onClick还是onLongClick)以及由谁来处理
                //事件监听的类型
                Class<?> listenerType = eventBase.listenerType();//View.OnClickListener.class
                //订阅
                String listenerSetter = eventBase.listenerSetter();//setOnClickListener
                //事件处理   事件被触发之后，执行的回调方法的名称
                String callBackMethod=eventBase.callbackMethod();

                try {
                    //反射得到id，再根据id得到对应的View
                    Method valueMethod = annotationType.getDeclaredMethod("value");
                    int[] viewId = (int[])valueMethod.invoke(annotation);
                    for (int id : viewId) {
                        //根据id反射获取对应的view
                        Method findViewById=aClass.getMethod("findViewById",int.class);
                        View view = (View)findViewById.invoke(object, id);
                        if(view==null)
                        {
                            continue;
                        }
                        //得到ID对应的VIEW以后
                        //开始在这个VIEW上执行监听  (使用动态代理)
                        //需要执行activity上的onClick方法
                        ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(object, method);
                        Object instance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, listenerInvocationHandler);
                       //setOnClickListener(new View.OnClickListener());
                        Method onclickMethod = view.getClass().getMethod(listenerSetter,listenerType);
                        onclickMethod.invoke(view,instance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }

    /**
     * 控件注入
     * @param object
     */
    private static void injectView(Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if(viewInject != null){
                int value = viewInject.value();
                try {
                    Method method = aClass.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(object,value);
                    field.setAccessible(true);//设置为可访问的权限。即将private修饰的字段变成可公共访问的
                    field.set(object,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 布局注入
     * @param object
     */
    private static void injectLayout(Object object) {
        int layoutId = 0;
        Class<?> aClass = object.getClass();
        ContentView annotation = aClass.getAnnotation(ContentView.class);
        if(annotation != null){
            layoutId = annotation.value();
            try {
                //反射Activity的setContentView方法
                Method method = aClass.getMethod("setContentView", int.class);
                method.invoke(object,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
