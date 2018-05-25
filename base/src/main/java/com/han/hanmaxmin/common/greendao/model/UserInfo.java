package com.han.hanmaxmin.common.greendao.model;

import com.han.hanmaxmin.hantext.httptext.model.BaseModel;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * Created by ptxy on 2018/4/24.
 */

@Entity(nameInDb = "UserInfoNew")
public class UserInfo extends BaseModel{
    @Id
    private long id;

    private String name;
    private String sex;
    private String age;
    private String like;

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> userInfoVips;

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> userUpdateVipCard;

    @Generated(hash = 1107500778)
    public UserInfo(long id, String name, String sex, String age, String like,
            List<String> userInfoVips, List<String> userUpdateVipCard) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.like = like;
        this.userInfoVips = userInfoVips;
        this.userUpdateVipCard = userUpdateVipCard;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
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
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
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
    public List<String> getUserInfoVips() {
        return this.userInfoVips;
    }
    public void setUserInfoVips(List<String> userInfoVips) {
        this.userInfoVips = userInfoVips;
    }
    public List<String> getUserUpdateVipCard() {
        return this.userUpdateVipCard;
    }
    public void setUserUpdateVipCard(List<String> userUpdateVipCard) {
        this.userUpdateVipCard = userUpdateVipCard;
    }
}
