package com.han.hanmaxmin.common.exception;

/**
 * @CreateBy Administrator
 * @Date 2017/11/14  11:48
 * @Description
 */

public class HanException extends RuntimeException {
    private final HanExceptionType mType;
    private final Object requestTag;

    public HanException(HanExceptionType type,Object requestTag,String message) {
        super(message);
        this.mType=type;
        this.requestTag=requestTag;
    }

    public HanExceptionType getExceptionType() {
        return mType;
    }

    public Object getRequestTag() {
        return requestTag;
    }
}
