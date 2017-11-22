package com.han.hanmaxmin.mvp.presenter;

import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.mvp.HanIView;

/**
 * @CreateBy Administrator
 * @Date 2017/11/7  10:37
 * @Description
 */

public class HanPresenter<V extends HanIView> {

    protected String initTag() {
        return getClass().getSimpleName();
    }

    /**
     * 自定义异常处理。
     */
    public void methodError(HanException exception) {
        L.e(initTag(),"methodError... errorType："+exception.getExceptionType()+"errorMessage:"+exception.getMessage());


    }
}
