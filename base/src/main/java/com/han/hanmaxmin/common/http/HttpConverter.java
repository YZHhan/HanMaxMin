package com.han.hanmaxmin.common.http;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
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

    Object jsonFromBody(ResponseBody body, Type type, String methdName) {
        if (body == null) return null;
        Charset charset = Charset.forName("UTF-8");
        MediaType mediaType = body.contentType();
        if (mediaType != null) {
            charset = mediaType.charset(charset);
        }
        InputStream inputStream = body.byteStream();
        if (inputStream != null && charset != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
            BufferedReader bufferedReader = null;


            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;//
    }
}
