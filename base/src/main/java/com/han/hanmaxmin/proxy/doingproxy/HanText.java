package com.han.hanmaxmin.proxy.doingproxy;

import com.han.hanmaxmin.proxy.didproxy.Subject;

/**
 * @CreateBy Administrator
 * @Date 2018/2/4  21:14
 * @Description
 *
 *
 *
 *
 */

public class HanText {
    public static void main(String args[]) {

        HanProxyHandler proxy = new HanProxyHandler();
        //绑定该类实现的所有接口
        Subject subject = (Subject) proxy.bind(new HanRealSubject());
        subject.doingProxy();

    }
}
