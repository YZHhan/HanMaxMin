package com.han.hanmaxmin.hantext.proxy.doingproxy;



import com.han.hanmaxmin.common.aspect.network.method.TERMINAL;
import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.exception.HanExceptionType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ptxy on 2018/2/6.
 */

public class RealAdapter {

    public <T> T createApi(Class<T> clazz){
        return createApi(clazz, null);
    }

    public <T> T createApi(Class<T> clazz, String reuqestTag){
        ProxyHandler proxyHandler = new ProxyHandler(this);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxyHandler);
    }

    public Object startRequest(Method method, Object [] args, Object requestTag){
        Annotation[] annotations = method.getAnnotations();
        //  代码严谨性，对annotation的进行
        if(annotations != null && annotations.length < 1 ){
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag , "Annotations error ...  the method :"+ method.getName() + "must have one annotation at least ! @GET @POST or @PUT");
        }

        Annotation pathAnnotation = null;

        String terminal = null;
        for(Annotation annotation : annotations){
            if(annotation instanceof TERMINAL){
                terminal = ((TERMINAL) annotation).value();
            } else {
                pathAnnotation = annotation;
            }
        }

        if (pathAnnotation == null){
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "Annotation erro ... the method : "+ method.getName() + "");
        }


return null;
    }

}
