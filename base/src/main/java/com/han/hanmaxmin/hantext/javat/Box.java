package com.han.hanmaxmin.hantext.javat;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description  泛型
 */

public class Box {
    private String object;
    public void set(String object) { this.object = object; }
    public String get() { return object; }

    public class BoxT<T> {
        // T stands for "Type"
        private T t;
        public void set(T t) { this.t = t; }
        public T get() { return t; }
    }
}


