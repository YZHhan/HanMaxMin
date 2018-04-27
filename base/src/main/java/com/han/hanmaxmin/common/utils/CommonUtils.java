package com.han.hanmaxmin.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.log.L;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 16:38
 * @Description
 */

public class CommonUtils {

    private static long lastClickTime;
    private static int screenWidth;
    private static int screenHeight;

    public static int dp2px(float dp) {
        return (int) dp2px(HanHelper.getInstance().getApplication().getResources(), dp);
    }

    public static int sp2px(float sp) {
        return (int) sp2px(HanHelper.getInstance().getApplication().getResources(), sp);
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Manifest中meta_data的字符串信息
     */
    public static String getMetaInfo(String metaKey, String defaultValue) {
        PackageManager pManager = HanHelper.getInstance().getApplication().getPackageManager();
        ApplicationInfo appInfo;
        String msg = defaultValue;
        try {
            appInfo = pManager.getApplicationInfo(HanHelper.getInstance().getApplication().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return msg;
        }
        if (appInfo != null && appInfo.metaData != null) {
            if (appInfo.metaData.get(metaKey) != null) {
                Object object = appInfo.metaData.get(metaKey);
                if (object != null) {
                    msg = object.toString();
                }
            }
        }
        return msg;
    }

    public static int getScreenWidth() {
        if (screenWidth > 0) return screenWidth;
        return getScreenWidth(HanHelper.getInstance().getScreenHelper().currentActivity());
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        if (screenWidth > 0) return screenWidth;
        if (activity == null) return 0;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        return screenWidth;
    }

    public static int getScreenHeight() {
        if (screenHeight > 0) return screenHeight;
        return getScreenHeight(HanHelper.getInstance().getScreenHelper().currentActivity());
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        if (screenHeight > 0) return screenHeight;
        if (activity == null) return 0;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        return screenHeight;
    }


    /**
     * 是否连击(在View的onClick回调中不断调用该方法来确定是否连击)
     *
     * @return true：连击，false：非连击  0.6秒
     */
    public static boolean isSeriesClick() {
        return isSeriesClick(600);
    }

    public static boolean isSeriesClick(int duration) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < duration) {
            lastClickTime = currentTime;
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    /**
     * 判断是否是数字
     *
     * @return true:是，false:不是
     */
    public static boolean isNumber(String str) {
        return !TextUtils.isEmpty(str) && Pattern.compile("[0-9]*").matcher(str).matches();
    }

    /**
     * 转换文件的大小，将文件的字节数转换为kb、mb、或gb
     */
    public static String formatterFileSize(long size) {
        return Formatter.formatFileSize(HanHelper.getInstance().getApplication(), size);
    }


    /**
     * 获取客户端名称
     */
    public static String getAppVersionName() {
        return getAppVersionName(HanHelper.getInstance().getApplication().getPackageName());
    }

    public static String getAppVersionName(String packageName) {
        String strVersion = "";
        try {
            strVersion = HanHelper.getInstance().getApplication().getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strVersion;
    }

    /**
     * get App versionCode
     */
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取SHA1
     */
    public static String getCertificateSHA1Fingerprint(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = new Signature[0];
        if (packageInfo != null) {
            signatures = packageInfo.signatures;
        }
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            if (cf != null) {
                c = (X509Certificate) cf.generateCertificate(input);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (c != null) {
                byte[] publicKey = md.digest(c.getEncoded());
                StringBuilder str = new StringBuilder(publicKey.length * 2);
                for (int i = 0; i < publicKey.length; i++) {
                    String h = Integer.toHexString(publicKey[i]);
                    int l = h.length();
                    if (l == 1) h = "0" + h;
                    if (l > 2) h = h.substring(l - 2, l);
                    str.append(h.toUpperCase());
                    if (i < (publicKey.length - 1)) str.append(':');
                }
                hexString = str.toString();
            }
        } catch (NoSuchAlgorithmException | CertificateEncodingException e1) {
            e1.printStackTrace();
        }
        return hexString;
    }

    /**
     * 判断 用户是否安装第三方客户端
     */
    public static boolean isOtherAppAvailable(String packageName) {
        final PackageManager packageManager = HanHelper.getInstance().getApplication().getPackageManager();// 获取packagemanager
        List<PackageInfo> infoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); i++) {
                String pn = infoList.get(i).packageName;
                if (!TextUtils.isEmpty(pn) && pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sdcard是否可用
     */
    public static boolean isSdcardAvailable() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }


    @ThreadPoint(ThreadType.MAIN)
    public static void restartApp() {
        HanHelper.getInstance().getScreenHelper().popAllActivityExceptMain(null);
        Intent intent = HanHelper.getInstance().getApplication().getPackageManager().getLaunchIntentForPackage(HanHelper.getInstance().getApplication().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(UtilsConstants.BUNDLE_KEY_RESET_APP_FLAG, 1);
        HanHelper.getInstance().getApplication().startActivity(intent);
    }

    public static String getPackageChannel() {
        return getMetaInfo("UMENG_CHANNEL", "DYT");
    }


    public static String getLocalTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String format = formatter.format(calendar.getTime());
        return format;
    }

    public static boolean isTimeOut(String dateTime) {
        String localTime = getLocalTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = formatter.parse(dateTime);
            L.i("UserConfig", "parse = " + parse);
            Date parse1 = formatter.parse(localTime);
            L.i("UserConfig", "parse1 = " + parse1);
            if (parse.getTime() > parse1.getTime()) {
                L.i("UserConfig", "我还没过期");
                return false;
            } else {
                L.i("UserConfig", "我已经过期了");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
