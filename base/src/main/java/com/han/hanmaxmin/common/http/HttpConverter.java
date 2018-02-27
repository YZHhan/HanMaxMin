package com.han.hanmaxmin.common.http;

import com.google.gson.Gson;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.stream.StreamCloseUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @CreateBy Administrator
 * @Date 2017/11/15  21:17
 * @Description HTTP 的 Response 解析
 */

public class HttpConverter {
    private final String TAG = "HttpConverter";
    private final Gson gson;


    HttpConverter() {
        this.gson = new Gson();
    }

    /**
     *
     * @param body  ResponseBody  对象中可以获取到  body.byteStream();流，字节数组，字符串
     *
     * @param type
     * @param methdName
     * @return
     * @throws IOException
     */
    Object jsonFromBody(ResponseBody body, Type type, String methdName) throws IOException {
        if (body == null) return null;
        Charset charset = Charset.forName("UTF-8");//  字符编码。格式
        MediaType mediaType = body.contentType();// 设置字符编码的。
        if (mediaType != null) {
            charset = mediaType.charset(charset);
        }
        InputStream is = body.byteStream();//从ResponseBody中得到stream。
        if (is != null && charset != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(is, charset);
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                String json = result.toString();
                L.i(TAG, "methodName" + methdName + "    响应体 Json：" + result.toString());
                return gson.fromJson(json, type);
            } finally {
                StreamCloseUtils.close(inputStreamReader, is, bufferedReader);
            }

        }
        return null;//
    }

    RequestBody jsonToBody(String methodName, String mimeType, Object object, Type type) {
        String json = gson.toJson(object, type);
        L.i(TAG, "methodName: " + methodName + "   请求体 mimeType：" + ", json" + json);
        return RequestBody.create(MediaType.parse(mimeType), json);
    }

    RequestBody fileToBody(String methodName, String mimeType, File file) {
        L.i(TAG, "methodName: " + methodName + "   请求体 mimeType： " + ", File " + file.getPath());
        return RequestBody.create(MediaType.parse(mimeType), file);
    }

    RequestBody byteToBody(String methodName, String mimeType, byte[] bytes) {
        L.i(TAG, "methodName : " + methodName + "   请求体 mimeType ： " + mimeType + ", byte length:" + bytes.length);
        return RequestBody.create(MediaType.parse(mimeType), bytes);
    }


}
