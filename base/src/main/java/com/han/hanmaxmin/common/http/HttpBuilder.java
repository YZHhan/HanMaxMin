package com.han.hanmaxmin.common.http;

import android.text.TextUtils;

import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.exception.HanExceptionType;

import okhttp3.Headers;

/**
 * @CreateBy Administrator
 * @Date 2017/11/15  21:17
 * @Description  Builder 完成啦两种操作
 *  一个是配置终端地址（terminal）
 *  一个是添加请求头（Headers）
 */

public class HttpBuilder {
    private final Object   requestTag;
    private final String   path;
    private final Object[] args;
    private       String   terminal;

    private Headers.Builder headBuilder = new Headers.Builder();

    HttpBuilder(Object requestTag, String path, Object[] args) {
        this.requestTag = requestTag;
        this.path = path;
        this.args = args;
    }

    public HttpBuilder addHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            headBuilder.add(key, value);
        }
        return this;
    }

    public Headers.Builder getHeadBuilder() {
        return headBuilder;
    }

    public HttpBuilder setTerminal(String terminal) {
        if (!TextUtils.isEmpty(terminal)) {
            if (terminal.endsWith("/")) {
                terminal = terminal.substring(0, terminal.length() - 1);
            }
            this.terminal=terminal;
        }else{
            throw new HanException(HanExceptionType.UNEXPECTEN,requestTag,"Dear! The terminal is emtpty...");
        }
        return this;
    }

    public String getPath() {
        return path;
    }

    public Object getRequestTag() {
        return requestTag;
    }

    public Object[] getArgs() {
        return args;
    }

    String getTerminal() {
        return terminal;
    }
}
