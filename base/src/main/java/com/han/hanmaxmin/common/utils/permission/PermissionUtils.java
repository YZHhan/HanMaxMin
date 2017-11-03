package com.han.hanmaxmin.common.utils.permission;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/3/7 12:36
 * @Description
 */

public class PermissionUtils {
    private static final String TAG = "PermissionUtils";
    private static PermissionUtils util;
    private        int             requestCode;
    private HashMap<String, PermissionBuilder> maps = new HashMap<>();

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (util == null) {
            util = new PermissionUtils();
        }
        return util;
    }

    public PermissionBuilder createBuilder() {
        return new PermissionBuilder();
    }

    void startRequestPermission(PermissionBuilder builder) {
        if (builder == null) return;
        if (maps.containsValue(builder)) {
            L.e(TAG, "current permission is requesting, please don't request again....");
            return;
        }
        if (builder.getActivity() == null) {
            L.e(TAG, "activity can not be null, please setActivity()");
            return;
        }
        if (builder.getWantPermissionArr().size() == 0) {
            L.e(TAG, "you has not addWantPermission(String)");
            return;
        }
        L.i(TAG, "startRequestPermission:" + builder.toString());
        ArrayList<String> unGrantedPermission = getUnGrantedPermissionArr(builder.getWantPermissionArr());
        if (unGrantedPermission.size() > 0) {
            if (builder.getActivity() != null) {
                requestCode++;
                L.i(TAG, "start request permission  requestCode=" + requestCode + "   wantPermission=" + unGrantedPermission.toString());
                builder.setRequestCode(requestCode);
                maps.put(String.valueOf(requestCode), builder);
                ActivityCompat.requestPermissions(builder.getActivity(), unGrantedPermission.toArray(new String[unGrantedPermission.size()]), requestCode);
            }
        } else {
            L.i(TAG, "all permission is granted....");
            if (builder.getListener() != null) {
                builder.getListener().onPermissionCallback(-1, true);
            }
        }
    }

    private ArrayList<String> getUnGrantedPermissionArr(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String permission : list) {
            if (ContextCompat.checkSelfPermission(HanHelper.getInstance().getApplication(), permission) != PackageManager.PERMISSION_GRANTED) {//用户未授权
                arrayList.add(permission);
                L.i(TAG,"permission"+permission);
            }
        }
        return arrayList;
    }



}
