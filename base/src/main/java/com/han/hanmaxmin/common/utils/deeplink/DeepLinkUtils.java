package com.han.hanmaxmin.common.utils.deeplink;

import android.net.Uri;
import android.text.TextUtils;

import com.han.hanmaxmin.common.log.L;

import java.util.Arrays;

/**
 * Created by ptxy on 2018/1/31.
 *DeepLink连接跳转 。包括内部WebView，第三方连接跳转
 */

public class DeepLinkUtils {
    public static final String TAG = "DeepLinkUtils";
    public static final String CUSTOM_SCHEME="http";
    public static final String HOME = "home";


    /**
     *适用于原生内部跳转
     * @param type 跳转类型，用于区分跳转那个页面
     * @param value 跳转时携带的参数
     */
    public static boolean applyLink(String type, String... value){
            return apply(type,value);
    }

    /**
     * 适用于H5跳转native
     */
    public static boolean applyLink(Uri uri){
        if(uri == null){
            return false;
        }
        L.i(TAG,"applylink... Uri :" +uri.toString());
        String scheme = uri.getScheme();
        if(CUSTOM_SCHEME.equals(scheme)){
            String path = uri.getPath();
            String[] value = getParamValue(uri);
                return apply(path, value);
        } else {
            intent2WebView(new String[]{uri.toString()});
            return true;
        }
    }


    public static boolean applyLink(Uri uri, String description){
        if(uri == null){
            return false;
        }
        L.i(TAG, "applylink... uri: "+uri.toString());
        if(CUSTOM_SCHEME.equals(uri.getScheme())){
            String path = uri.getPath();
            String[] value = getParamValue(uri);
         return  apply(path, value);
        }else {
            if(description != null){
                intent2WebView(new String[]{uri.toString()}, description);
            }else {
                intent2WebView(new String[]{uri.toString()});
            }
                return  true;
        }



        }private static void intent2WebView(String[] strings, String description) {

    }


    private static void intent2WebView(String[] strings) {

    }

    private static boolean apply(String path, String[] value) {
L.i(TAG, "apply  path:"+path+"value"+ Arrays.toString(value));
value = value == null ? new String[0] : value;
            switch (path){
                case HOME:

                    break;
            }
        return false;
    }

    private static String[] getParamValue(Uri uri) {
        String index = uri.getQueryParameter("index");
        if(!TextUtils.isEmpty(index)) return new String []{index};
        String id1 = uri.getQueryParameter("id1");
        String id2 = uri.getQueryParameter("id2");
        if(!TextUtils.isEmpty(id1) && !TextUtils.isEmpty(id2)){
            return new String[]{id1,id2};
        }else if(!TextUtils.isEmpty(id1)){
            return new String[]{id1};
        }else if(!TextUtils.isEmpty(id2)){
            return new String[]{id2};
        }

        String id = uri.getQueryParameter("id");
        if (!TextUtils.isEmpty(id)) return new String[]{id};

        String webUrl = uri.getQueryParameter("webUrl");
        if (!TextUtils.isEmpty(webUrl)) return new String[]{webUrl};

        String teacherId = uri.getQueryParameter("teacherId");
        if (!TextUtils.isEmpty(teacherId)) return new String[]{teacherId};
        return new String[0];
    }


}
