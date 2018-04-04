package com.han.hanmaxmin.common.config;

import android.content.res.Resources;
import android.text.TextUtils;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.stream.StreamCloseUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Properties;


/**
 * Created by ptxy on 2018/3/31.
 * Property  :主要用于读取Java的配置文件
 * 其配置文件常为.properties文件，格式为文本文件，文件的内容的格式是“键=值”的格式，文本注释信息可以用"#"来注释
 * 提供了几个方法：getProperty（String key）
 * load（InputStream inStream）：从输入流中读取属性列表（键和元素），通过对指定文件（例如test.properties）进行装载，来获取该文件的所有键值对。
 * setProperty（String key， String value）：调用hashtable的方法put，设置键值对。
 * store（OutputStream stream, String comments）：与load方法相反，该方法将键值对写入到指定文件中。
 * clear ：清楚所有键值对。
 */

public abstract class HanProperties {

    private static final String DEFAULT_CODE = "utf-8";
    private static final String DEFAULT_ANNOTATION_VALUE = "";
    private static final String EXTENSION = ".properties";
    private final Properties mProperties = new Properties();
    private static final int OPEN_TYPE_ASSETS = 1;
    private static final int OPEN_TYPE_DATA = 2;

    private String mPropertiesFileName;
    private File propertyFilePath;
    private PropertyCallback propertyCallback;


    public HanProperties() {
        this("config");
    }

    public abstract String initTag();

    public abstract int initType();


    public HanProperties(String propertiesFileName) {
        this.mPropertiesFileName = propertiesFileName;
        propertyFilePath = HanHelper.getInstance().getApplication().getFilesDir();

        switch (initType()) {
            case OPEN_TYPE_ASSETS:
                InputStream inputStream = null;
                Resources resources = HanHelper.getInstance().getApplication().getResources();
                try {
                    inputStream = resources.getAssets().open(mPropertiesFileName + EXTENSION);
                    mProperties.load(inputStream);
                    loadPropertiesValues();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    StreamCloseUtils.close(inputStream);
                }
                break;
            case OPEN_TYPE_DATA:
                InputStream in = null;

                    try {
                        String stringBuilder = mPropertiesFileName +EXTENSION;
                        File file = new File(propertyFilePath, stringBuilder);
                        if(!file.exists()) {
                            boolean success = file.createNewFile();
                            L.i(initTag(), "create properties file " + (success ? "success" : "fail") + " !  file:" + file.getAbsolutePath());
                        }
                        in = new BufferedInputStream(new FileInputStream(file));
                        mProperties.load(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        StreamCloseUtils.close(in);
                    }
                loadPropertiesValues();
                break;
        }


    }


    public void setPropertyCallback(PropertyCallback propertyCallback) {
        this.propertyCallback = propertyCallback;
    }


    private void loadPropertiesValues() {
        Class<? extends HanProperties> thisClass = this.getClass();
        Field[] fields = thisClass.getDeclaredFields();//获取自己声明的各种字段，包括public，protected，private
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {// 是否使用了某个注解
                field.setAccessible(true);//将一个类的成员变量设置为private
                String fieldName = field.getName();// 得到名字
                Property annotation = field.getAnnotation(Property.class);

                if (annotation.value().equals(DEFAULT_ANNOTATION_VALUE)) {
                    setFieldValue(field, fieldName);
                } else {
                    setFieldValue(field, annotation.value());
                }
            }
        }
    }

    /**
     * 设置属性值
     */
    private void setFieldValue(Field field, String propertiesName) {
        Object value = getPropertyValue(field.getType(), propertiesName);
        if(value == null) return;
        try {
            field.set(this, value);
        } catch (IllegalAccessException e) {
            L.e(initTag(), field.getName() + propertiesName);
        }
    }


    /**
     * 获取类型
     */
    public Object getPropertyValue(Class<?> clazz, String key) {
        if (clazz == String.class) {
            return getString(key, "");
        } else if (clazz == float.class || clazz == Float.class) {
            return getFloat(key, 0);
        } else if (clazz == double.class || clazz == Double.class) {
            return getDouble(key, 0);
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return getBoolean(key, false);
        } else if (clazz == int.class || clazz == Integer.class){
            return getInt(key, 0);
        } else if (clazz == long.class || clazz == Long.class){
            return getLong(key, 0);
        } else {
            return null;
        }
    }




    private long getLong(String key, int defaultValue) {
        String value;
        try {
            value = mProperties.getProperty(key);
            if(TextUtils.isEmpty(value))return 0;
            L.e(initTag(), "Long current fileName : " + mPropertiesFileName + "current key : " + key +"current value :" + value);
            return Long.parseLong(value);
        }catch (Exception e){
            return defaultValue;
        }
    }

    private double getDouble(String key, double defaultValue) {
        String value;
        try {
            value = mProperties.getProperty(key);
            if(TextUtils.isEmpty(value))return 0;
            L.e(initTag(), "Double current fileName : " + mPropertiesFileName + "current key : " + key +"current value :" + value);
            return Double.parseDouble(value);
        }catch (Exception e){
            return defaultValue;
        }
    }


    private float getFloat(String key, float defaultValue) {
        String value;
        try {
            value = mProperties.getProperty(key);
            if (TextUtils.isEmpty(value)) return 0;
            L.e(initTag(), "Float current fileName : " + mPropertiesFileName + "current key : " + key +"current value :" + value);
            return Float.parseFloat(mProperties.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String getString(String key, String defaultValue) {
        String result = null;
        switch (initType()) {
            case OPEN_TYPE_ASSETS:
                try {
                    result = new String(mProperties.getProperty(key, defaultValue).getBytes("ISO-8859-1"), DEFAULT_CODE);
                } catch (UnsupportedEncodingException e) {
                    return defaultValue;
                }
                break;
            case OPEN_TYPE_DATA:
                result = mProperties.getProperty(key, defaultValue);
                break;
        }
        L.e(initTag(), "String current fileName : " + mPropertiesFileName + "current key : " + key + "current value : " + result);
        return result;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value;
        try {
            value = mProperties.getProperty(key);
            L.e(initTag(), "Boolean current fileName : " + mPropertiesFileName + "current key : " + key + "current value : " + value);
            return !TextUtils.isEmpty(value) && Boolean.parseBoolean(value); 
        } catch (Exception e){
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        String value;
        try {
            value = mProperties.getProperty(key);
            if(TextUtils.isEmpty(value))return 0;
            L.e(initTag(), "Int current fileName : " + mPropertiesFileName + "current key : " + key + "current value : " + value);
            return Integer.parseInt(value);
        }catch (Exception e){
            return  defaultValue;
        }
    }

    /**
     * 所有属性写到properties里面
     */
    private void writeDefaultPropertiesValues() {
        L.i(initTag(), "WritePropertiesValues() -- 写入所有值");
        Class<? extends HanProperties> thisClass = this.getClass();
        Field[] fields = thisClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Property annotation = field.getAnnotation(Property.class);
                if (annotation.value().equals(DEFAULT_ANNOTATION_VALUE)) {
                    try {
                        mProperties.put(fieldName, "");
                        setFieldDefaultValue(field, fieldName);
                    } catch (Exception e) {
                        L.e(initTag(), "Properties写入错误:" + e.toString());
                    }
                } else {
                    try {
                        mProperties.put(annotation.value(), "");
                        setFieldDefaultValue(field, annotation.value());
                    } catch (Exception e) {
                        L.e(initTag(), "Properties写入错误:" + e.toString());
                    }
                }
            }
        }



    }

    private void setFieldDefaultValue(Field field, String propertiesName) {
        Object value = getPropertyDefaultValue(field.getType());
        if (value == null) {
            return;
        }
        try {
            field.set(this, value);
        } catch (Exception e) {
            L.e(initTag(), "setFieldValue失败...属性名:" + propertiesName + " 文件名:" + field.getName());
        }
    }


    /**
     * 获取类型
     */
    private Object getPropertyDefaultValue(Class<?> clazz) {
        if (clazz == String.class) {
            return "";
        } else if (clazz == float.class || clazz == Float.class) {
            return 0;
        } else if (clazz == double.class || clazz == Double.class) {
            return 0;
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return false;
        } else if (clazz == int.class || clazz == Integer.class) {
            return 0;
        } else if (clazz == long.class || clazz == Long.class) {
            return 0;
        } else {
            return null;
        }
    }

    /**
     * 所有属性写到Properties 里面。
     */
    private void writePropertiesValues() {
        Class<? extends HanProperties> thisClass = this.getClass();
        Field[] fields = thisClass.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(Property.class)){//
                field.setAccessible(true);//
                String fieldName = field.getName();
                Property annotation = field.getAnnotation(Property.class);
                if(annotation.value().equals(DEFAULT_ANNOTATION_VALUE)){
                    try {
                        mProperties.put(fieldName, field.get(this) == null ? "" : String.valueOf(field.get(this)));
                    } catch (IllegalAccessException e) {
                        L.e(initTag(), "Properties写入错误:" + e.toString());
                    }
                } else {
                    try {
                        mProperties.put(annotation.value(), field.get(this) == null ? "" : String.valueOf(field.get(this)));
                    } catch (IllegalAccessException e) {
                        L.e(initTag(), "Properties写入错误:" + e.toString());
                    }

                }
            }
        }


    }


    /**
     * 提交
     */
    public void commit(){
        commit(propertyCallback);
    }

    public void commit(PropertyCallback callback){
        OutputStream out = null;
        try {
            File file = new File(propertyFilePath, mPropertiesFileName + EXTENSION);
            if(!file.exists()){
                boolean success = file.createNewFile();
                L.e(initTag(), "create new file isSuccess:" + success);
                if (!success){
                    L.e(initTag(), "create new file is Failed");
                    return;
                }
            }

            synchronized (mProperties){
                out = new BufferedOutputStream(new FileOutputStream(file));
                writePropertiesValues();
            }
        }catch (Exception e){

        }
    }

    public void clear(){
        OutputStream out = null;
        try {
            File file = new File(propertyFilePath, mPropertiesFileName + EXTENSION);
            if(!file.exists()){
                return;
            }
            synchronized (mProperties) {
                out = new BufferedOutputStream(new FileOutputStream(file));
                writeDefaultPropertiesValues();
                mProperties.store(out, "");
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            StreamCloseUtils.close(out);
        }
    }
}
