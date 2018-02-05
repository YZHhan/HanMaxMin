package com.han.hanmaxmin.proxy.didproxy;

/**
 * @CreateBy Administrator
 * @Date 2018/2/4  20:10
 * @Description   抽象角色
 */

/**
 * 抽象角色
 */
public interface Subject {

    public void  doingProxy();
}

/**
 * 真是角色
 */
class RealSubject implements Subject{

    @Override public void doingProxy() {
        System.out.print("doingProxy...   真事对象");
    }
}


/**
 * 代理角色
 */
class SubjectProxy implements Subject{

    //代理模式的作用：为其他对象提供一种代理以控制对这个对象的访问 。。
    Subject subject = new RealSubject();

    @Override public void doingProxy() {
        System.out.println("before"); //调用目标对象之前可以做相关操作
        subject.doingProxy();
        System.out.println("after");//调用目标对象之后可以做相关操作
    }
}






