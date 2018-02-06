package com.han.hanmaxmin.proxy.didproxy;

/**
 * @CreateBy Administrator
 * @Date 2018/2/4  20:18
 * @Description
 *
 * 静态代理：
 *  可以看到SubjectProxy（代理对象） 实现了Subject（抽象对象）接口（和RealSubject实现相同的接口）
 *  并持有的是Suject接口类型的引用。这样调用的依然是doingPorxy方法，只是实例化的对象过程变了，结果来看，
 *  代理类SubjectProxy可以自动为我们加上了before和after等我们需要的动作。
 *  如果将来需要一个新接口，就需要在代理类在写该接口的实现方法 ，会导致代理类的代码变得臃肿；另一方面，当需要
 *  改变抽象接口接口时，无疑真是角色和代理角色也需要改变。
 *
 */

public class DidProxyText {
   public static void main(String [] arg) throws Exception{
       Subject subject = new SubjectProxy();
       subject.doingProxy();
   }
}
