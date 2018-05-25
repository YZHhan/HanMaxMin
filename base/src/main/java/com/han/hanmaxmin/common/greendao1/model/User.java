package com.han.hanmaxmin.common.greendao1.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "User")
public class User {
    @Id(autoincrement = true)
    private long id;
    private String name;
    private String age;
    private String like;
    private String vip;
    @Generated(hash = 253562575)
    public User(long id, String name, String age, String like, String vip) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.like = like;
        this.vip = vip;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getLike() {
        return this.like;
    }
    public void setLike(String like) {
        this.like = like;
    }
    public String getVip() {
        return this.vip;
    }
    public void setVip(String vip) {
        this.vip = vip;
    }
}
