package com.han.hanmaxmin.common.http;

import android.text.TextUtils;

import com.han.hanmaxmin.common.aspect.network.method.DELETE;
import com.han.hanmaxmin.common.aspect.network.method.GET;
import com.han.hanmaxmin.common.aspect.network.method.HEAD;
import com.han.hanmaxmin.common.aspect.network.method.PATCH;
import com.han.hanmaxmin.common.aspect.network.method.POST;
import com.han.hanmaxmin.common.aspect.network.method.PUT;
import com.han.hanmaxmin.common.aspect.network.method.TERMINAL;
import com.han.hanmaxmin.common.aspect.network.parameter.Body;
import com.han.hanmaxmin.common.aspect.network.parameter.Path;
import com.han.hanmaxmin.common.aspect.network.parameter.Query;
import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.exception.HanExceptionType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.proxy.HttpHandler;
import com.han.hanmaxmin.common.utils.HanHelper;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @CreateBy Administrator
 * @Date 2017/11/15  21:17
 * @Description 设置网络请求的默认值。
 *
 *
 */

public class HttpAdapter {
    private static final String TAG          = "HttpAdapter";
    private static final String PATH_REPLACE = "\\{\\w*\\}";
    private static final int    timeOut      = 10;
    private OkHttpClient  client;
    private HttpConverter converter;

    public HttpAdapter() {
        initDefaults();
    }


    /**
     * 获取默认值
     */
    private void initDefaults() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(timeOut, TimeUnit.SECONDS);//连接超时
            builder.readTimeout(timeOut, TimeUnit.SECONDS);//读取超时
            builder.writeTimeout(timeOut, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(true);
            client = builder.build();
        }

        if (converter == null) {
            converter = new HttpConverter();
        }
    }

    private HttpBuilder getHttpBuidler(Object requestTag, String path, Object[] args) {
        HttpBuilder httpBuilder = new HttpBuilder(requestTag, path, args);
        HanHelper.getInstance().getApplication().initHttpAdapter(httpBuilder);
        return httpBuilder;
    }


    public <T> T create(Class<T> clazz) {
        return create(clazz, null);
    }

    public <T> T create(Class<T> clazz, Object requestType) {
        L.i("proxy","我是HTTPAdapter的  create");
        validateIsInterface(clazz, requestType);
        validateIsExtendInterface(clazz, requestType);
        HttpHandler handler = new HttpHandler(this, requestType);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, handler);
    }


    /**
     * 判断传入的是够是接口
     *
     * @param service
     * @param requestType
     */
    private static <T> void validateIsInterface(Class<T> service, Object requestType) {
        if (service == null || !service.isInterface()) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestType, String.valueOf(service) + "is not interface");
        }
    }


    /**
     * 判断是否是有其他接口
     *
     * @param service
     * @param requestType
     * @param <T>
     */
    private static <T> void validateIsExtendInterface(Class<T> service, Object requestType) {
        if (service.getInterfaces().length > 0) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestType, String.valueOf(service) + "can not extened interface");
        }
    }

    public Object startRequest(Method method, Object[] args, Object requestTag) {
        L.i("proxy", "HttpAdapter  ...  startRequest....");
        Annotation[] annotations = method.getAnnotations();//   获取注解。
        if (annotations == null || annotations.length < 1) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "Annotation error... the method:" + method.getName() + " must have one annotation at least!! @GET @POST or @PUT");
        }
        Annotation pathAnnotation = null;
        String terminal = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof TERMINAL) {
                terminal = ((TERMINAL) annotation).value();
            } else {
                pathAnnotation = annotation;
            }
        }
        if (pathAnnotation == null) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "Annotation error... the method:" + method.getName() + " create(Object.class) the method must has an annotation,such as:@PUT @POST or @GET...");
        }
        if (pathAnnotation instanceof POST) {
            String path = ((POST) pathAnnotation).value();
            return executeWithOkHttp(terminal, method, args, path, requestTag, "POST");

        } else if (pathAnnotation instanceof GET) {
            String path = ((GET) pathAnnotation).value();
            return executeWithOkHttp(terminal, method, args, path, requestTag, "GET");

        } else if (pathAnnotation instanceof PUT) {
            String path = ((PUT) pathAnnotation).value();
            return executeWithOkHttp(terminal, method, args, path, requestTag, "PUT");

        } else if (pathAnnotation instanceof DELETE) {
            String path = ((DELETE) pathAnnotation).value();
            return executeWithOkHttp(terminal, method, args, path, requestTag, "DELETE");

        } else if (pathAnnotation instanceof HEAD) {
            String path = ((HEAD) pathAnnotation).value();
            return executeWithOkHttp(terminal, method, args, path, requestTag, "HEAD");

        } else if (pathAnnotation instanceof PATCH) {
            String path = ((PATCH) pathAnnotation).value();
            return executeWithOkHttp(terminal, method, args, path, requestTag, "PATCH");

        } else {
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "Annotation error... the method:" + method.getName() + "create(Object.class) the method must has an annotation, such as:@PUT @POST or @GET...");
        }
    }


    private Object executeWithOkHttp(String terminal, Method method, Object[] args, String path, Object reqeustTag, String requestType) {
        Annotation[][] annotations = method.getParameterAnnotations();//参数可以有多个注解，但是这里不允许

        checkParameterAnnotation(annotations, args, method.getName(), reqeustTag);

        HttpBuilder httpBuilder = new HttpBuilder(requestType, path, args);
        StringBuilder url = getUrl(TextUtils.isEmpty(terminal) ? httpBuilder.getTerminal() : terminal, path, method, args, reqeustTag);

        if (TextUtils.isEmpty(url)) throw new HanException(HanExceptionType.UNEXPECTED, reqeustTag, "The url error... method :" + method.getName() + " request url is null...");

        RequestBody requestBody = null;
        Object body = null;
        HashMap<String, Object> params = null;
        String mimeType = null;

        for (int i = 0; i < annotations.length; i++) {
            Annotation[] annotationArr = annotations[i];
            Annotation annotation = annotationArr[i];
            if (annotation instanceof Body) {
                body = args[i];
                mimeType = ((Body) annotation).mimeType();
                if (TextUtils.isEmpty(mimeType)) throw new HanException(HanExceptionType.UNEXPECTED, reqeustTag, "The reqeust body exception ...method:" + method.getName() + " the annotaation @Body not have mimeType value");
                break;
            } else if (annotation instanceof Query) {
                if (params == null) params = new HashMap<>();
                Object arg = args[i];
                String key = ((Query) annotation).value();
                params.put(key, arg);
            }
        }
        if ((!"GET".equals(requestType)) && (!"HEAD".equals(requestType))) {
            if (body != null) {
                if (body instanceof File) {
//                    requestBody= converter
                } else if (body instanceof byte[]) {

                } else {

                }
            }
        }

        if (params != null && params.size() > 0) {
            int i = 0;
            for (String key : params.keySet()) {
                Object object = params.get(key);
                url.append(i == 0 ? "?" : "&").append(key).append("=").append(object);
                i++;
            }
        }
        L.i(TAG, "The url is append after :" + url);

        Request.Builder requestBuidler = new Request.Builder();
        requestBuidler.headers(httpBuilder.getHeadBuilder().build());
        if (reqeustTag != null) requestBuidler.tag(reqeustTag);

        Request request = requestBuidler.url(url.toString()).method(requestType, requestBody).build();
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            return createResult(method, response, reqeustTag);
        } catch (IOException e) {
            throw new HanException(HanExceptionType.HTTP_ERROR,reqeustTag,"This is IOException... method:"+method.getName()+" message"+e.getMessage());
        }


    }

    private Object createResult(Method method, Response response, Object reqeustTag) {
        Class<?> returnType = method.getReturnType();
        if (returnType == void.class || response == null) return null;
        int responseCode = response.code();

        HanHelper.getInstance().getApplication().onCommonHttpResponse(response);

        if (responseCode < 200 || responseCode >= 300) {
            response.close();
            throw new HanException(HanExceptionType.HTTP_ERROR, reqeustTag, "This is http error... method:" + method.getName() + " http response code = " + responseCode);
        }

        if (returnType.equals(Response.class)) {
            return response;
        }

        ResponseBody body = response.body();

        if (body == null) {
            response.close();
            throw new HanException(HanExceptionType.HTTP_ERROR,reqeustTag,"This is Http response error... method:"+method.getName()+" response body is null!!");
        }

        return null;
    }


    private StringBuilder getUrl(String terminal, String path, Method method, Object[] args, Object requestTag) {
        if (TextUtils.isEmpty(terminal)) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The url terminal error... method:" + method.getName() + " termial is null ...");
        }

        if (TextUtils.isEmpty(path)) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The url path error... method:" + method.getName() + "path is null...");
        }

        if (!path.endsWith("/")) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The url path error... method:" + method.getName() + " path=" + path + " (path is not start with '/')");
        }

        Annotation[][] annotations = method.getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            Annotation ann = annotations[i][0];
            if (ann instanceof Path) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] split = path.split(PATH_REPLACE);
                Object arg = args[i];
                if (!(arg instanceof String[])) {
                    throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The params error... method:" + method.getName() + " @Path annotation only fix String[] arg !");
                }
                String[] param = (String[]) arg;
                if (split.length - param.length > 1) {
                    throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The params error... method:" + method.getName() + " the path with '{xx}' is more than @Path annotation arg length!");
                }

                for (int index = 0; index < split.length; index++) {
                    if (index < param.length) {
                        stringBuilder.append(split[index]).append(param[index]);
                        L.i(TAG, "When 'index < param' ,stringBuilder:" + stringBuilder.toString());
                    } else {
                        stringBuilder.append(split[index]);
                        L.i(TAG, "When 'index > param' ,stringBuilder:" + stringBuilder.toString());
                    }
                    path = stringBuilder.toString();
                    L.i(TAG, "The path is :" + path);
                }
            }
        }

        StringBuilder url = new StringBuilder(terminal);
        url.append(path);
        L.i(TAG, "The method :" + method.getName() + " Http request url :" + url.toString());
        return url;
    }


    /**
     * Check the parameter annotation
     * One and only one note for each parameter
     */
    private void checkParameterAnnotation(Annotation[][] annotations, Object[] args, String methodName, Object requestTag) {
        if (annotations != null && args != null && annotations.length > 0 && args.length > 0) {
            if (annotations.length == args.length) throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "params error method :" + methodName + " params have to have one annotaiton, such as @Query @Path");
            for (Annotation[] annotationsArr : annotations) {
                if (annotationsArr.length != 1) throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "params error method :" + methodName + " params have to have one annotation, but there is more than one!");
            }

        }
    }
}
