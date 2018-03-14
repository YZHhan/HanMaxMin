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
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @CreateBy Administrator
 * @Date 2017/11/15  21:17
 * @Description 设置网络请求的默认值。
 * HttpAdapter :
 * 是对Annotation的解析。
 *
 *
 */

public class HttpAdapter {
    private static final String TAG          = "HttpAdapter";
    private static final String PATH_REPLACE = "\\{\\w*\\}";//正则表达式。    匹配包括下划线的任何单词字符。类似但不等价于“[A-Za-z0-9_]”，这里的"单词"字符使用Unicode字符集。
//    private static final String PATH_REPLACE = "\\{\\w*}";
    private static final int    timeOut      = 10;  //  超时时间
    private OkHttpClient  client;
    private HttpConverter converter;

    /**
     * 在构造函数里面，做初始化。
     */
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
            builder.writeTimeout(timeOut, TimeUnit.SECONDS);//写超时。
            builder.retryOnConnectionFailure(true);// 是否自动连接。
            client = builder.build();
        }

        if (converter == null) {
            converter = new HttpConverter();
        }
    }

    private HttpBuilder getHttpBuilder(Object requestTag, String path, Object[] args) {
        HttpBuilder httpBuilder = new HttpBuilder(requestTag, path, args);
        HanHelper.getInstance().getApplication().initHttpAdapter(httpBuilder);
        return httpBuilder;
    }


    /**
     *
     * @param clazz  传入的接口。
     * @param <T> 返回的泛型。
     * @return
     */
    public <T> T create(Class<T> clazz) {
        return create(clazz, null);
    }

    /**
     *
     * @param clazz
     * @param requestType
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> clazz, Object requestType) {
        L.i("proxy","我是HTTPAdapter的  create");
        validateIsInterface(clazz, requestType);// 判断传入的是否是接口。
        validateIsExtendInterface(clazz, requestType);//判断接口是否有继承。
        //  动态代理实现 InvocationHandler的类。
        HttpHandler handler = new HttpHandler(this, requestType);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, handler);
    }


    /**
     * 判断传入的是否是接口
     *
     * @param service
     * @param requestType
     * 解决的非接口的方式是，抛异常。
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
     *      解决的接口有继承的方式是，抛异常
     */
    private static <T> void validateIsExtendInterface(Class<T> service, Object requestType) {
        if (service.getInterfaces().length > 0) {
            throw new HanException(HanExceptionType.UNEXPECTED, requestType, String.valueOf(service) + "can not extened interface");
        }
    }

    /**
     *
     * @param method  java 反射中类的方法信息。
     * @param args
     * @param requestTag
     * @return
     */
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

        // 解析Annotation的注解信息来进行请求区分。。。。。
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

    public void cancelRequest(Object requestTag){
        if(client != null && requestTag != null){
            synchronized (client.dispatcher()){
                Dispatcher dispatcher = client.dispatcher();
                List<Call> queuedCalls = dispatcher.queuedCalls();
                for (Call call : queuedCalls){
                    Request request = call.request();
                    if(requestTag.equals(request.tag())){
                        L.e(TAG, "cancel queued request success... requestTag = " + requestTag + "  url = " +request.url());
                        call.cancel();
                    }
                }

                List<Call> runningCalls = dispatcher.runningCalls();
                for (Call call : runningCalls){
                    Request request = call.request();
                    if(requestTag.equals(request.tag())){
                        L.e(TAG, "cancel running reqest... requestTag = " + requestTag + "   url = "+ request.url());
                        call.cancel();
                    }
                }

            }
        }
    }

    public void cancelAllRequest(){
        if(client != null ){
            client.dispatcher().cancelAll();
        }
    }

    /**
     * \
     * @param terminal 终端地址
     * @param method 进行请求参数的获取。
     * @param args
     * @param path
     * @param reqeustTag  请求唯一标识
     * @param requestType  请求方式
     * @return
     */
    private Object executeWithOkHttp(String terminal, Method method, Object[] args, String path, Object reqeustTag, String requestType) {
        Annotation[][] annotations = method.getParameterAnnotations();//参数可以有多个注解，但是这里不允许

        //检查Annotation的参数是否是一个。
        checkParameterAnnotation(annotations, args, method.getName(), reqeustTag);

        //  进行参数的拼接。  请求地址的连接。
        HttpBuilder httpBuilder = getHttpBuilder(requestType, path, args);
        //  得到Url地址。
        StringBuilder url = getUrl(TextUtils.isEmpty(terminal) ? httpBuilder.getTerminal() : terminal, path, method, args, reqeustTag);

        if (TextUtils.isEmpty(url)) throw new HanException(HanExceptionType.UNEXPECTED, reqeustTag, "The url error... method :" + method.getName() + " request url is null...");

        RequestBody requestBody = null;//请求体
        Object body = null;
        HashMap<String, Object> params = null;
        String mimeType = null;

        for (int i = 0; i < annotations.length; i++) {
            Annotation[] annotationArr = annotations[i];
            Annotation annotation = annotationArr[i];
            if (annotation instanceof Body) {// 请求的body
                body = args[i];
                mimeType = ((Body) annotation).mimeType();
                if (TextUtils.isEmpty(mimeType)) throw new HanException(HanExceptionType.UNEXPECTED, reqeustTag, "The reqeust body exception ...method:" + method.getName() + " the annotaation @Body not have mimeType value");
                break;
            } else if (annotation instanceof Query) { // 请求的参数query
                if (params == null) params = new HashMap<>();
                Object arg = args[i];
                String key = ((Query) annotation).value();
                params.put(key, arg);
            }
        }

        //  可以带body的请求、
        if ((!"GET".equals(requestType)) && (!"HEAD".equals(requestType))) {
            if (body != null) {
                if (body instanceof File) {
                    requestBody = converter.fileToBody(method.getName(), mimeType, (File) body);
                } else if (body instanceof byte[]) {
                    requestBody = converter.byteToBody(method.getName(), mimeType, (byte[]) body);
                } else {
                    requestBody = converter.jsonToBody(method.getName(), mimeType, body, body.getClass());
                }
            }
        }

        //  get  拼接参数。
        if (params != null && params.size() > 0) {
            int i = 0;
            for (String key : params.keySet()) {
                Object object = params.get(key);
                url.append(i == 0 ? "?" : "&").append(key).append("=").append(object);//  因为gei的第一个拼接是有？之后是用&
                i++;
            }
        }
        L.i(TAG, "The url is append after :" + url);

         Request.Builder requestBuidler = new Request.Builder();
        requestBuidler.headers(httpBuilder.getHeadBuilder().build());
        if (reqeustTag != null) requestBuidler.tag(reqeustTag);

        //
        Request request = requestBuidler.url(url.toString()).method(requestType, requestBody).build();
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            return createResult(method, response, reqeustTag);//
        } catch (IOException e) {
            throw new HanException(HanExceptionType.HTTP_ERROR,reqeustTag,"This is IOException... method:"+method.getName()+" message"+e.getMessage());
        }


    }

    /**
     *  对response  进行策略。公共回调给Application。  responseCode策略
     * @param method
     * @param response
     * @param reqeustTag
     * @return
     */
    private Object createResult(Method method, Response response, Object reqeustTag) throws  IOException {
        Class<?> returnType = method.getReturnType();
        if (returnType == void.class || response == null) return null;
        int responseCode = response.code();//

        HanHelper.getInstance().getApplication().onCommonHttpResponse(response);//最后把response返回给   Applocation

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

        Object resutl = converter.jsonFromBody(body, returnType, method.getName());
        response.close();
        return resutl;
    }


    /**
     * 得到URL
     * @param terminal  终端地址，判断是否为空
     * @param path  地址，判断是否为空，是否以“/”
     * @param method Annoatation 注解字段
     * @param args
     * @param requestTag
     * @return
     */
    private StringBuilder getUrl(String terminal, String path, Method method, Object[] args, Object requestTag) {
        if (TextUtils.isEmpty(terminal)) {//判断终端地址是否为空
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The url terminal error... method:" + method.getName() + " termial is null ...");
        }

        if (TextUtils.isEmpty(path)) {//判断path是否为空
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The url path error... method:" + method.getName() + "path is null...");
        }

        if (!path.startsWith("/")) {//判断path是否以”/“结尾。
            throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "The url path error... method:" + method.getName() + " path=" + path + " (path is not start with '/')");
        }

        // 得到Annotation的注解。
        Annotation[][] annotations = method.getParameterAnnotations();

        //进行区分path。
        for (int i = 0; i < annotations.length; i++) {
            Annotation ann = annotations[i][0];
            if (ann instanceof Path) {// Path ....
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
            if (annotations.length != args.length) throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "params error method :" + methodName + " params have to have one annotaiton, such as @Query @Path");
            for (Annotation[] annotationsArr : annotations) {
                if (annotationsArr.length != 1) throw new HanException(HanExceptionType.UNEXPECTED, requestTag, "params error method :" + methodName + " params have to have one annotation, but there is more than one!");
            }

        }
    }
}
