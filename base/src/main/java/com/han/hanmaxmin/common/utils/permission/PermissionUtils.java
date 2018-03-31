package com.han.hanmaxmin.common.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import java.util.ArrayList;
import java.util.Arrays;
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
    private HashMap<String, PermissionBuilder> maps = new HashMap<>();// 集合权限和字符串

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
        if (maps.containsValue(builder)) {// 如果包含请求的权限就不要再次请求
            L.e(TAG, "current permission is requesting, please don't request again....");
            return;
        }
        if (builder.getActivity() == null) {//
            L.e(TAG, "activity can not be null, please setActivity()");
            return;
        }
        if (builder.getWantPermissionArr().size() == 0) {//
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


        /*---------------------------------------------------------以下是申请数据解析—————————————*/
            public void parsePermissionResultData(int requestCode, String[] permissions, int [] grantResults, Activity activity){
                PermissionBuilder builder = maps.remove(String.valueOf(requestCode));
                if(builder == null)return;
                boolean grantedAll =true;
                ArrayList<String> unGrantedArr = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){// 用户不同意时，向用户展示该权限作用
                        grantedAll = false;
                        if(i < permissions.length && i >= 0){
                            L.i(TAG, "User no granted Permission ："+permissions[i]);
                            unGrantedArr.add(permissions[i]);
                        }
                    }
                }


                if(grantedAll){// 判断是否同意所有。
                    L.i(TAG, "User granted all Permission");
                    if(builder.getListener() != null){
                        builder.getListener().onPermissionCallback(requestCode, true);
                    }
                } else {// 否则的话
                    if(builder.getListener() != null){
                        builder.getListener().onPermissionCallback(requestCode, false);
                    }
                    if(builder.isShowCustomDialog()){// 遍历那些不同意的权限
                        boolean shouldShowDialog = false;
                        ArrayList<String> shouldShowDialogArr = new ArrayList<>();
                        for (String unGrantedStr : unGrantedArr){
                            if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, unGrantedStr)){
                                shouldShowDialog =true;
                                shouldShowDialogArr.add(unGrantedStr);
                            }
                        }

                        if(shouldShowDialog){
                                showPermissionTipsDialog(activity, shouldShowDialogArr);
                        }
                    }
                }
            }



    /**
     * 当系统提醒请求权限的对话框勾选不再提醒时，弹出的自定义对话框
     */
    private void showPermissionTipsDialog(Activity activity, ArrayList<String> showDialogPermission){

                if(activity == null && showDialogPermission == null && showDialogPermission.size() < 1){
                    return;
                }
        String message = getPermissionDialogMessage(showDialogPermission);
                if(TextUtils.isEmpty(message))return;
                L.i(TAG, "勾选了不在提醒所以弹出自定义对话框："+showDialogPermission.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("注意")//
                .setMessage(message)//
                .setPositiveButton(HanHelper.getInstance().getApplication().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 设置启动模式。。
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);//
                        intent.setData(Uri.fromParts("package", HanHelper.getInstance().getApplication().getPackageName(), null));
                        HanHelper.getInstance().getApplication().startActivity(intent);
                        dialog.cancel();
                    }
                }).setNegativeButton(HanHelper.getInstance().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           dialog.cancel();
            }
        }).show();
    }


    /**
     *
     * @param permission
     * @return
     */
    private String getPermissionDialogMessage(ArrayList<String> permission){

        if(permission == null || permission.size() < 1 ) return null;
        StringBuilder stringbuilder =new StringBuilder();

        stringbuilder.append("(");
        for (int i = 0, size = permission.size(); i < size; i++){
            switch (permission.get(i)){
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    stringbuilder.append("定位").append(i == size - 1 ? "" :  ",") ;
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    stringbuilder.append("读取外部储存").append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    stringbuilder.append(HanHelper.getInstance().getApplication().getString(R.string.request_write_external_storage_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.READ_CONTACTS:
                    stringbuilder.append(HanHelper.getInstance().getApplication().getString(R.string.request_constants_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.CALL_PHONE:
                    stringbuilder.append(HanHelper.getInstance().getApplication().getString(R.string.request_call_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.CAMERA:
                    stringbuilder.append(HanHelper.getInstance().getApplication().getString(R.string.request_camera_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    stringbuilder.append(HanHelper.getInstance().getApplication().getString(R.string.request_record_audio_permission)).append((i == size - 1) ? "" : "，");
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                    stringbuilder.append(HanHelper.getInstance().getApplication().getString(R.string.request_read_phone_state_permission)).append((i == size - 1) ? "" : "，");
                    break;
            }
        }
        return stringbuilder.append(")").append("权限被禁止啦，需要开启才能执行后续操作").toString();
    }


}
