package com.han.hanmaxmin.common.widget.cameraview.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.han.hanmaxmin.common.widget.cameraview.utils.CameraUtils;
import com.han.hanmaxmin.common.widget.toast.HanToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/6/20 15:21
 * @Description: 自定义相机
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    private static final String TAG = "CameraPreview";

    private int viewWidth = 0;
    private int viewHeight = 0;

    /**
     * 监听接口
     */
    private OnCameraStatusListener listener;

    private SurfaceHolder holder;
    private Camera camera;
    private FocusView mFocusView;

    public CameraPreview(Context context) {
        super(context);

    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得SurfaceHolder对象
        holder = getHolder();
        // 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
        holder.addCallback(this);
        // 设置SurfaceHolder对象的类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setOnTouchListener(onTouchListener);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //创建一个PictureCallback对象，并实现其中的onPictureTaken方法
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 停止照片拍摄
            try {
                camera.stopPreview();
            } catch (Exception e) {
            }
            // 调用结束事件
            if (null != listener) {
                listener.onCameraStopped(data);
            }
        }
    };

    @Override
    public void onAutoFocus(boolean b, Camera camera) {

    }

    /**
     * 在Surface创建时激发
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (!CameraUtils.checkCameraHardware(getContext())) {
            HanToast.show("摄像头打开失败！");
            return;
        }
        // 获得Camera对象
        camera = getCameraInstance();
        if (camera == null) {
            Log.i("TakePhoto==", "我擦，这是空");
        }
        try {
            // 设置用于显示拍照摄像的SurfaceHolder对象
            camera.setPreviewDisplay(holder);// 为空
        } catch (IOException e) {
            e.printStackTrace();
            // 释放手机摄像头
            camera.release();
            camera = null;
        }
        updateCameraParameters();
        if (camera != null) {
            camera.startPreview();
        }
        setFocus();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        updateCameraParameters();
        // start preview with new settings
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
        setFocus();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // 释放手机摄像头
        camera.release();
        camera = null;
    }

    // 进行拍照，并将拍摄的照片传入PictureCallback接口的onPictureTaken方法
    public void takePicture() {
        if (camera != null) {
            try {
                camera.takePicture(null, null, pictureCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 设置监听事件
    public void setOnCameraStatusListener(OnCameraStatusListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (camera != null) {
            camera.startPreview();
        }
    }

    public void stop() {
        if (camera != null) {
            camera.stopPreview();
        }
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        viewWidth = MeasureSpec.getSize(widthSpec);
        viewHeight = MeasureSpec.getSize(heightSpec);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
    }

    /**
     * 获取Camera实例
     */
    private Camera getCameraInstance() {
        Camera mCamera = null;
        try {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();// get camera number

            for (int camIndex = 0; camIndex < cameraCount; camIndex++) {
                Camera.getCameraInfo(camIndex, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    try {
                        mCamera = Camera.open(camIndex);
                    } catch (Exception e) {
                        HanToast.show("摄像头打开失败！");
                    }

                }
            }

            if (mCamera == null) {
                mCamera = Camera.open(0); // attempt to get a Camera instance
            }

        } catch (Exception e) {
            HanToast.show("摄像头打开失败！");

        }

        return mCamera;
    }


    /**
     * 更新Camera 的参数
     */
    private void updateCameraParameters() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            try {// 设置参数
                setParameters(parameters);
                camera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    //遇到上面所说的情况，只能设置一个最小的预览尺寸
                    parameters.setPreviewSize(1920, 1080);
                    camera.setParameters(parameters);

                } catch (Exception e1) {

                    //到这里还有问题，就是拍照尺寸的锅了，同样只能设置一个最小的拍照尺寸
                    e1.printStackTrace();
                    try {
                        parameters.setPictureSize(1920, 1080);
                        camera.setParameters(parameters);
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }

    /**
     * 设置参数
     */
    private void setParameters(Camera.Parameters parameters) {
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes
                .contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        long time = new Date().getTime();
        parameters.setGpsTimestamp(time);
        parameters.setPictureFormat(PixelFormat.JPEG);//设置图片格式

        Camera.Size previewSize = findPreviewSizeByScreen(parameters);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        parameters.setPictureSize(previewSize.width, previewSize.height);

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        }
    }


    /**
     * @param parameters
     */
    private Camera.Size findPreviewSizeByScreen(Camera.Parameters parameters) {
        if (viewWidth != 0 && viewHeight != 0) {
            return camera.new Size(Math.max(viewWidth, viewHeight),
                    Math.min(viewWidth, viewHeight));
        } else {
            return camera.new Size(CameraUtils.getScreenWH(getContext()).heightPixels,
                    CameraUtils.getScreenWH(getContext()).widthPixels);
        }
    }

    /**
     * 设置焦点和测光区域
     *
     * @param event
     */
    public void focusOnTouch(MotionEvent event) {

        int[] location = new int[2];
        RelativeLayout relativeLayout = (RelativeLayout) getParent();
        relativeLayout.getLocationOnScreen(location);

        Rect focusRect = CameraUtils.calculateTapArea(mFocusView.getWidth(),
                mFocusView.getHeight(), 1f, event.getRawX(), event.getRawY(),
                location[0], location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());
        Rect meteringRect = CameraUtils.calculateTapArea(mFocusView.getWidth(),
                mFocusView.getHeight(), 1.5f, event.getRawX(), event.getRawY(),
                location[0], location[0] + relativeLayout.getWidth(), location[1],
                location[1] + relativeLayout.getHeight());

        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (parameters.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
            focusAreas.add(new Camera.Area(focusRect, 1000));

            parameters.setFocusAreas(focusAreas);
        }

        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            meteringAreas.add(new Camera.Area(meteringRect, 1000));

            parameters.setMeteringAreas(meteringAreas);
        }

        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
        }
        camera.autoFocus(this);
    }


    /**
     * 设置聚焦的图片
     *
     * @param focusView
     */
    public void setFocusView(FocusView focusView) {
        this.mFocusView = focusView;
    }

    /**
     * 设置自动聚焦，并且聚焦的圈圈显示在屏幕中间位置
     */
    public void setFocus() {
        if (!mFocusView.isFocusing()) {
            try {
                camera.autoFocus(this);
                mFocusView.setX((CameraUtils.getWidthInPx(getContext()) - mFocusView.getWidth()) / 2);
                mFocusView.setY((CameraUtils.getHeightInPx(getContext()) - mFocusView.getHeight()) / 2);
                mFocusView.beginFocus();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 相机拍照监听接口
     */
    public interface OnCameraStatusListener {
        // 相机拍照结束事件
        void onCameraStopped(byte[] data);
    }

    /**
     * 点击显示焦点区域
     */
    OnTouchListener onTouchListener = new OnTouchListener() {
        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int width = mFocusView.getWidth();
                int height = mFocusView.getHeight();
                mFocusView.setX(event.getX() - (width / 2));
                mFocusView.setY(event.getY() - (height / 2));
                mFocusView.beginFocus();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                focusOnTouch(event);
            }
            return true;
        }
    };


}
