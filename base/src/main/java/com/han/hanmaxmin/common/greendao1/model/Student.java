package com.han.hanmaxmin.common.greendao1.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Student")
public class Student {
    @Id
    private long id;
    private String studentName;
    private String studentAge;
    private String like;
    private String music;
    private String play;
    private String study;
    @Generated(hash = 236945597)
    public Student(long id, String studentName, String studentAge, String like,
            String music, String play, String study) {
        this.id = id;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.like = like;
        this.music = music;
        this.play = play;
        this.study = study;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getStudentName() {
        return this.studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentAge() {
        return this.studentAge;
    }
    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }
    public String getLike() {
        return this.like;
    }
    public void setLike(String like) {
        this.like = like;
    }
    public String getMusic() {
        return this.music;
    }
    public void setMusic(String music) {
        this.music = music;
    }
    public String getPlay() {
        return this.play;
    }
    public void setPlay(String play) {
        this.play = play;
    }
    public String getStudy() {
        return this.study;
    }
    public void setStudy(String study) {
        this.study = study;
    }


}
