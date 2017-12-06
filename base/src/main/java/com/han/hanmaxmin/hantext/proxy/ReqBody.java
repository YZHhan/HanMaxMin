package com.han.hanmaxmin.hantext.proxy;

import com.han.hanmaxmin.common.log.L;

/**
 * @CreateBy Administrator
 * @Date 2017/11/24  11:26
 * @Description
 */

public class ReqBody implements Body {
    @Override public void print(String string) {
        L.i("Proxy","String ="+string);
    }

    @Override public void bodySize(int size) {
        L.i("Proxy","size ="+size);
    }

}
