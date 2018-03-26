package com.han.hanmaxmin.common.greendao.model;

import com.han.hanmaxmin.hantext.httptext.model.BaseModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ptxy on 2018/3/21.
 */
@Entity(nameInDb = "UserInfo")
public class UserInfo extends BaseModel{
    @Id
    private long id;
    private String name;
    private String sex;
    private String age;
    private String number;
    private String like;
    private String like1;

    private String love;
    private String speak;

    private String want;

    //升级
    private String teacher_tag_t1;
    private String teacher_tag_t2;
    private String teacher_tag_t3;
    //升级 6
    private String teacher_tag_t4;

    // 7
    private String update;


    // update Db  5
    private String title;

    // update Db  9
    private String wokao;

    @Generated(hash = 1364223744)
    public UserInfo(long id, String name, String sex, String age, String number,
            String like, String like1, String love, String speak, String want,
            String teacher_tag_t1, String teacher_tag_t2, String teacher_tag_t3,
            String teacher_tag_t4, String update, String title, String wokao) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.number = number;
        this.like = like;
        this.like1 = like1;
        this.love = love;
        this.speak = speak;
        this.want = want;
        this.teacher_tag_t1 = teacher_tag_t1;
        this.teacher_tag_t2 = teacher_tag_t2;
        this.teacher_tag_t3 = teacher_tag_t3;
        this.teacher_tag_t4 = teacher_tag_t4;
        this.update = update;
        this.title = title;
        this.wokao = wokao;
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
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getLike() {
        return this.like;
    }
    public void setLike(String like) {
        this.like = like;
    }
    public String getLove() {
        return this.love;
    }
    public void setLove(String love) {
        this.love = love;
    }
    public String getSpeak() {
        return this.speak;
    }
    public void setSpeak(String speak) {
        this.speak = speak;
    }
    public String getWant() {
        return this.want;
    }
    public void setWant(String want) {
        this.want = want;
    }
    public String getTeacher_tag_t1() {
        return this.teacher_tag_t1;
    }
    public void setTeacher_tag_t1(String teacher_tag_t1) {
        this.teacher_tag_t1 = teacher_tag_t1;
    }
    public String getTeacher_tag_t2() {
        return this.teacher_tag_t2;
    }
    public void setTeacher_tag_t2(String teacher_tag_t2) {
        this.teacher_tag_t2 = teacher_tag_t2;
    }
    public String getTeacher_tag_t3() {
        return this.teacher_tag_t3;
    }
    public void setTeacher_tag_t3(String teacher_tag_t3) {
        this.teacher_tag_t3 = teacher_tag_t3;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTeacher_tag_t4() {
        return this.teacher_tag_t4;
    }
    public void setTeacher_tag_t4(String teacher_tag_t4) {
        this.teacher_tag_t4 = teacher_tag_t4;
    }
    public String getUpdate() {
        return this.update;
    }
    public void setUpdate(String update) {
        this.update = update;
    }
    public String getLike1() {
        return this.like1;
    }
    public void setLike1(String like1) {
        this.like1 = like1;
    }
    public String getWokao() {
        return this.wokao;
    }
    public void setWokao(String wokao) {
        this.wokao = wokao;
    }

}
