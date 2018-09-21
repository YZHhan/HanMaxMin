package com.han.hanmaxmin.common.widget.cameraview.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.common.widget.cameraview.CropperImage;
import com.han.hanmaxmin.common.widget.cameraview.camera.CameraPreview;
import com.han.hanmaxmin.common.widget.cameraview.camera.FocusView;
import com.han.hanmaxmin.common.widget.cameraview.cropper.CropImageView;
import com.han.hanmaxmin.common.widget.cameraview.utils.CameraUtils;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.mvp.HanActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 * @Author yinzh
 * @Date   2018/6/19 15:07
 * @Description: 拍照界面
 */
public class TakePhotoActivity extends HanActivity implements CameraPreview.OnCameraStatusListener, SensorEventListener {

    private static final String TAG = "TakePhotoActivity";

    public static final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final String PATH = Environment.getExternalStorageDirectory()
            .toString() + "/AndroidMedia/";

    // 组件
    @Bind(R.id.CropImageView)
    CropImageView cropImageView;// 自定义视图，为图片提供裁剪功能
    @Bind(R.id.cameraPreview)
    CameraPreview mCameraPreview;//自定义相机
    @Bind(R.id.take_photo_layout)
    RelativeLayout mTakePhotoLayout;
    @Bind(R.id.cropper_layout)
    LinearLayout mCropperLayout;
    @Bind(R.id.view_focus)
    FocusView focusView;

    boolean isRotated = false;


    @Override
    public int layoutId() {
        return R.layout.activity_take_phote;
    }

    @Override
    public void initData(Bundle bundle) {
        L.i("Creash","我是Take  进来了");
        // Initialize components of the app
        mCameraPreview.setFocusView(focusView);
        mCameraPreview.setOnCameraStatusListener(this);
        cropImageView.setGuidelines(2);


        mSensorManager = (SensorManager) getSystemService(Context.
                SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.
                TYPE_ACCELEROMETER);
    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @OnClick({R.id.iv_photo_cancel, R.id.iv_takePhoto, R.id.iv_crop_photo_start, R.id.iv_crop_photo_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo_cancel:
                HanToast.show("取消相机");
                close(cropImageView);
                break;
            case R.id.iv_takePhoto:
                HanToast.show("照相");
                takePhoto(mCameraPreview);
                break;
//            case R.id.tv_photo_album:
//                HanToast.show("进入相册");
//                break;
            case R.id.iv_crop_photo_start:
                HanToast.show("开始剪裁");
                startCropper(mCameraPreview);
                break;
            case R.id.iv_crop_photo_cancel:
                HanToast.show("取消剪裁");
                closeCropper(mCameraPreview);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRotated) {//  对字体的 操作。
            TextView hint_tv = (TextView) findViewById(R.id.hint);
            ObjectAnimator animator = ObjectAnimator.ofFloat(hint_tv, "rotation", 0f, 90f);
            animator.setStartDelay(800);
            animator.setDuration(1000);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
            View view = findViewById(R.id.crop_hint);
            AnimatorSet animSet = new AnimatorSet();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "rotation", 0f, 90f);
            ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", 0f, -50f);
            animSet.play(animator1).before(moveIn);
            animSet.setDuration(10);
            animSet.start();
            isRotated = true;
        }
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    public void takePhoto(View view) {
        if (mCameraPreview != null) {
            mCameraPreview.takePicture();
        }
    }

    public void close(View view) {
        finish();
    }

    /**
     * 关闭截图界面
     *
     * @param view
     */
    public void closeCropper(View view) {
        showTakePhotoLayout();
    }


    /**
     * 开始截图，并保存图片
     *
     * @param view
     */
    public void startCropper(View view) {
        //获取截图并旋转90度
        CropperImage cropperImage = cropImageView.getCroppedImage();
        L.e(TAG, cropperImage.getX() + "," + cropperImage.getY());
        L.e(TAG, cropperImage.getWidth() + "," + cropperImage.getHeight());
        Bitmap bitmap = CameraUtils.rotate(cropperImage.getBitmap(), -90);
//        Bitmap bitmap = mCropImageView.getCroppedImage();
        // 系统时间
        long dateTaken = System.currentTimeMillis();
        // 图像名称
        String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken)
                .toString() + ".jpg";
        Uri uri = insertImage(getContentResolver(), filename, dateTaken, PATH,
                filename, bitmap, null);


//        cropperImage.getBitmap().recycle();
//        cropperImage.setBitmap(null);
//        Intent intent = new Intent(this, SettingActivity.class);
//        intent.setData(uri);
//        intent.putExtra("path", PATH + filename);
//        intent.putExtra("width", bitmap.getWidth());
//        intent.putExtra("height", bitmap.getHeight());
//        intent.putExtra("cropperImage", cropperImage);
//        startActivity(intent);
//        bitmap.recycle();
//        super.overridePendingTransition(R.anim.fade_in,
//                R.anim.fade_out);
//        doAnimation(cropperImage);
    }

//    private void doAnimation(CropperImage cropperImage) {
//        ImageView imageView = new ImageView(this);
//        View view = LayoutInflater.from(this).inflate(
//                R.layout.image_view_layout, null);
//        ((RelativeLayout) view.findViewById(R.id.root_layout)).addView(imageView);
//        RelativeLayout relativeLayout = ((RelativeLayout) findViewById(R.id.root_layout));
////        relativeLayout.addView(imageView);
//        imageView.setX(cropperImage.getX());
//        imageView.setY(cropperImage.getY());
//        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//        lp.width = (int)cropperImage.getWidth();
//        lp.height = (int) cropperImage.getHeight();
//        imageView.setLayoutParams(lp);
//        imageView.setImageBitmap(cropperImage.getBitmap());
//        try {
//            getWindow().addContentView(view, lp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        /*AnimatorSet animSet = new AnimatorSet();
//        ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", cropperImage.getX(), 0);
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", cropperImage.getY(), 0);*/
//
//        TranslateAnimation translateAnimation = new TranslateAnimation(
//                0, -cropperImage.getX(), 0, -(Math.abs(cropperImage.getHeight() - cropperImage.getY())));// 当前位置移动到指定位置
//        RotateAnimation rotateAnimation = new RotateAnimation(0, -90,
//                Animation.ABSOLUTE, cropperImage.getX() ,Animation.ABSOLUTE, cropperImage.getY());
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(translateAnimation);
//        animationSet.addAnimation(rotateAnimation);
//        animationSet.setFillAfter(true);
//        animationSet.setDuration(2000L);
//        imageView.startAnimation(animationSet);
////        finish();
//    }


    /**
     * 拍照成功后回调
     * 存储图片并显示截图界面
     *
     * @param data
     */
    @Override
    public void onCameraStopped(byte[] data) {
        Log.i("TAG", "==onCameraStopped==");
        // 创建图像
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        // 系统时间
        long dateTaken = System.currentTimeMillis();
        // 图像名称
        String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken)
                .toString() + ".jpg";
        // 存储图像（PATH目录）
        Uri source = insertImage(getContentResolver(), filename, dateTaken, PATH, filename, bitmap, data);
        //准备截图
        try {
            cropImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), source));// NullPointerException: uri
//            mCropImageView.rotateImage(90);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        showCropperLayout();
    }

    /**
     * 存储图像并将信息添加入媒体数据库
     */
    private Uri insertImage(ContentResolver cr, String name, long dateTaken,
                            String directory, String filename, Bitmap source, byte[] jpegData) {
        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    source.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        ContentValues values = new ContentValues(7);
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, filePath);
        return cr.insert(IMAGE_URI, values);
    }

    private void showTakePhotoLayout() {
        mTakePhotoLayout.setVisibility(View.VISIBLE);
        mCropperLayout.setVisibility(View.GONE);
    }

    private void showCropperLayout() {
        mTakePhotoLayout.setVisibility(View.GONE);
        mCropperLayout.setVisibility(View.VISIBLE);
        mCameraPreview.start();   //继续启动摄像头
    }


    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private boolean mInitialized = false;
    private SensorManager mSensorManager;
    private Sensor mAccel;

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        }
        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if (deltaX > 0.8 || deltaY > 0.8 || deltaZ > 0.8) {
            mCameraPreview.setFocus();
        }
        mLastX = x;
        mLastY = y;
        mLastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
