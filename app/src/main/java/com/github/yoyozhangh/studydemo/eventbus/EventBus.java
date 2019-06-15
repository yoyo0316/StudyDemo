package com.github.yoyozhangh.studydemo.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventBus {

    private static volatile EventBus instance;

    private Map<Object, List<SubscribeMethod>> cacheMap;

    private static String TAG = EventBus.class.getSimpleName();

    private Handler mHandler;

    private EventBus() {
        cacheMap = new HashMap<>();
        mHandler = new Handler();
    }

    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object object) {
        List<SubscribeMethod> list = cacheMap.get(object);
        if (list == null) {
            list = findSubscribleMethods(object);
            cacheMap.put(object, list);
        }
    }

    private List<SubscribeMethod> findSubscribleMethods(Object object) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();


        while (clazz != null) {

            //找父类的时候 需要先判断一下是否是 系统级别的父类
            String name = clazz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                break;
            }

            for (Method method : methods) {
                //找到带有SubScribe 注解的方法
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                //判断带有Subscribe 注解方法中的参数类型
                Class<?>[] types = method.getParameterTypes();
                if (types.length != 1) {
                    Log.e("错误", "EventBus only accept one paramer ");
                }
                ThreadMode threadMode = subscribe.threadMode();

                SubscribeMethod subscribeMethod = new SubscribeMethod(method, threadMode, types[0]);
                list.add(subscribeMethod);
            }

            clazz = clazz.getSuperclass();
        }


        return list;
    }

    public void post(final Object type) {
        //直接循环 Map 里面的方法 找到对应的回调

        Set<Object> set = cacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final Object obj = iterator.next();
            List<SubscribeMethod> list = cacheMap.get(obj);
            for (final SubscribeMethod subscribeMethod : list) {

                //a(if 条件前面的对象) 对象所对应的类信息是不是 b (if 条件后面的对象)对应所对应的类信息的父类或接口
                if (subscribeMethod.getType().isAssignableFrom(type.getClass())) {

                    switch (subscribeMethod.getmThreadMode()) {
                        case MAIN:
                            // 主 --- 主
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(subscribeMethod, obj, type);
                            } else {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod, obj, type);
                                    }
                                });
                            }

                            // 子 --- 主
                            break;
                        case BACKGROUND:
                            //ExecutorService 从主线程到子线程的切换
                            break;
                    }

//                    invoke(subscribeMethod, obj, type);
                }
            }
        }
    }

    private void invoke(SubscribeMethod subscribeMethod, Object obj, Object type) {
        Method method = subscribeMethod.getmMethod();
        try {
            method.invoke(obj, type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object object) {
        // TODO: 2019/6/15  
    }
}
