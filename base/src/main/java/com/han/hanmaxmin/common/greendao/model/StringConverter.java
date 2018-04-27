package com.han.hanmaxmin.common.greendao.model;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ptxy on 2018/4/24.
 */

public class StringConverter implements PropertyConverter<List<String>, String> {

    /**
     * 将数据库中的值，转化为实体Bean类对象(比如List<String>)
     */
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if(databaseValue == null){
            return null;
        } else {
            List<String> list = Arrays.asList(databaseValue.split(","));
            return list;
        }
    }

    /**
     *将实体Bean类(比如List<String>)转化为数据库中的值(比如String)
     */
    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        if(entityProperty == null){
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : entityProperty){
                stringBuilder.append(str);
                stringBuilder.append(",");
            }
            return stringBuilder.toString();
        }
    }
}
