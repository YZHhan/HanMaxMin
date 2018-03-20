package com.han.hanmaxmin.common.viewbind;

import android.text.TextUtils;
import android.view.View;

import com.han.hanmaxmin.common.viewbind.annotation.OnClick;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @CreateBy Administrator
 * @Date 2017/12/25  11:45
 * @Description
 */

final class EventListenerManager {
    public final static long QUICK_EVENT_TIME_SPAN = 300;
    public final static HashSet<String> AVOID_QUICK_EVENT_SET=new HashSet<>(2);

    static {
            AVOID_QUICK_EVENT_SET.add("onClick");
            AVOID_QUICK_EVENT_SET.add("onItemClick");
    }

    private EventListenerManager() {
    } /**
     * k1: viewInjectInfo
     * k2: interface Type
     * value: listener
     */
    private final static DoubleKeyValueMap<ViewInfo, Class<?>, Object> listenerCache = new DoubleKeyValueMap<>();


    static void addEventMethod(ViewFinder finder, ViewInfo info, OnClick annotation, Object handler, Method method) {
        try {
            View view = finder.findViewByInfo(info);
            if (view != null) {
                Class<?> listenerType = annotation.type();
                String listenerSetter = annotation.setter();
                if (TextUtils.isEmpty(listenerSetter)) {
                    listenerSetter = "set" + listenerType.getSimpleName();
                }
                String methodName = annotation.method();
                boolean addNewMethod = false;
                Object listener = listenerCache.get(info, listenerType);
                DynamicHandler dynamicHandler;
                if (listener != null) {
                    dynamicHandler = (DynamicHandler) Proxy.getInvocationHandler(listener);
                    addNewMethod = handler.equals(dynamicHandler.getHandler());
                    if (addNewMethod) {
                        dynamicHandler.addMethod(methodName, method);
                    }
                }
                if (!addNewMethod) {
                    dynamicHandler = new DynamicHandler(handler);
                    dynamicHandler.addMethod(methodName, method);
                    listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, dynamicHandler);
                    listenerCache.put(info, listenerType, listener);
                }
                Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                setEventListenerMethod.invoke(view, listener);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static class DynamicHandler implements InvocationHandler {
        private WeakReference<Object> handlerRef;
        private final HashMap<String, Method> methodMap     = new HashMap<>(1);
        private static long                    lastClickTime = 0;

        DynamicHandler(Object handler) {
            this.handlerRef = new WeakReference<>(handler);
        }

        void addMethod(String name, Method method) {
            methodMap.put(name, method);
        }

        Object getHandler() {
            return handlerRef.get();
        }

        @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object handler = handlerRef.get();
            if (handler != null) {
                String eventMethod = method.getName();
                if ("toString".equals(eventMethod)) {
                    return DynamicHandler.class.getSimpleName();
                }
                method = methodMap.get(eventMethod);
                if (method == null && methodMap.size() == 1) {
                    for (Map.Entry<String, Method> entry : methodMap.entrySet()) {
                        if (TextUtils.isEmpty(entry.getKey())) {
                            method = entry.getValue();
                        }
                        break;
                    }
                }

                if (method != null) {
                    if (AVOID_QUICK_EVENT_SET.contains(eventMethod)) {
                        long timeSpan = System.currentTimeMillis() - lastClickTime;
                        if (timeSpan < QUICK_EVENT_TIME_SPAN) {
                            return null;
                        }
                        lastClickTime = System.currentTimeMillis();
                    }
                    try {
                        return method.invoke(handler, args);
                    } catch (Throwable ex) {
                        throw new RuntimeException("invoke method error:" + handler.getClass().getName() + "#" + method.getName(), ex);
                    }
                }
            }
            return null;
        }
    }


}
