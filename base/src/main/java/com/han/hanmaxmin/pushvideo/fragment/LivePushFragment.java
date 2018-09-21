package com.han.hanmaxmin.pushvideo.fragment;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.SurfaceView;
import android.view.View;

import com.alivc.component.custom.AlivcLivePushCustomDetect;
import com.alivc.component.custom.AlivcLivePushCustomFilter;
import com.alivc.live.filter.TaoBeautyFilter;
import com.alivc.live.pusher.AlivcLivePushBGMListener;
import com.alivc.live.pusher.AlivcLivePushError;
import com.alivc.live.pusher.AlivcLivePushErrorListener;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePushNetworkListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.pushvideo.PushLiveActivity;

import static com.aliyun.vodplayer.utils.EncodeUtils.getMD5;

/*
 * @Author yinzh
 * @Date   2018/9/19 17:53
 * @Description
 */
public class LivePushFragment extends HanFragment implements Runnable{

    private static final String URL_KEY = "url_key";
    private static final String ASYNC_KEY= "async_key";
    private static final String AUDIO_ONLY_KEY = "audio_only_key";
    private static final String VIDEO_ONLY_KEY = "video_only_key";
    private static final String QUALITY_MODE_KEY = "quality_mode_key";
    private static final String CAMERA_ID = "camera_id";
    private static final String FLASH_ON = "flash_on";
    private static final String AUTH_TIME = "auth_time";
    private static final String PRIVACY_KEY = "privacy_key";
    private static final String MIX_EXTERN = "mix_extern";
    private static final String MIX_MAIN = "mix_main";
    private final long REFRESH_INTERVAL = 2000;

    private AlivcLivePusher mLivePusher = null;
    private String mPushUrl = null;
    private SurfaceView mSurfaceView = null;
    private boolean mAsync = false;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean isPushing;


    @Override
    public void run() {

    }



    private void initPusher(){
        /**
         * 设置推流错误事件
         */
        mLivePusher.setLivePushErrorListener(new AlivcLivePushErrorListener() {
            @Override
            public void onSystemError(AlivcLivePusher alivcLivePusher, AlivcLivePushError alivcLivePushError) {
                if(alivcLivePushError != null) {
                    HanToast.show(alivcLivePushError.getMsg());
                    L.i(initTag(), "getPushUrl==="+alivcLivePusher.getPushUrl() + "getMsg==="+alivcLivePushError.getMsg() + "getCode==" + alivcLivePushError.getCode());
                    //添加UI提示或者用户自定义的错误处理
                }
            }

            @Override
            public void onSDKError(AlivcLivePusher alivcLivePusher, AlivcLivePushError alivcLivePushError) {
                HanToast.show(alivcLivePushError.getMsg());
                L.i(initTag(), "getPushUrl==="+alivcLivePusher.getPushUrl() + "getMsg==="+alivcLivePushError.getMsg() + "getCode==" + alivcLivePushError.getCode());
            }
        });


        /**
         * 设置推流通知事件
         */
        mLivePusher.setLivePushInfoListener(new AlivcLivePushInfoListener() {

            // 预览开始通知
            @Override
            public void onPreviewStarted(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPreviewStarted = 预览开始");
            }

            //预览结束通知
            @Override
            public void onPreviewStoped(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPreviewStarted = 预览结束");

            }

            // 推流开始
            @Override
            public void onPushStarted(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPushStarted = 推流开始");

            }

            //推流暂停通知
            @Override
            public void onPushPauesed(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPushPauesed = 推流暂停");

            }

            //推流恢复通知
            @Override
            public void onPushResumed(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPushResumed = 推流恢复");
            }

            //推流停止通知
            @Override
            public void onPushStoped(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPushStoped = 推流停止");
            }

            //推流重启通知
            @Override
            public void onPushRestarted(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onPushRestarted = 推流重启");
            }

            //首帧渲染通知
            @Override
            public void onFirstFramePreviewed(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onFirstFramePreviewed = 首帧渲染");
            }

            //丢帧通知
            @Override
            public void onDropFrame(AlivcLivePusher alivcLivePusher, int i, int i1) {
                L.i(initTag(), "onDropFrame = 丢帧通知");
            }

            //调整码率通知
            @Override
            public void onAdjustBitRate(AlivcLivePusher alivcLivePusher, int i, int i1) {
                L.i(initTag(), "onAdjustBitRate = 调整码率通知");
            }

            //调整帧率通知
            @Override
            public void onAdjustFps(AlivcLivePusher alivcLivePusher, int i, int i1) {
                L.i(initTag(), "onAdjustFps = 调整帧率通知");
            }
        });


        /**
         * 设置推流播放器  的网路监听
         */
        mLivePusher.setLivePushNetworkListener(new AlivcLivePushNetworkListener() {
            @Override
            public void onNetworkPoor(AlivcLivePusher alivcLivePusher) {
                HanToast.show("网络差，请注意");
                L.i(initTag(), "onNetworkPoor ==  网络差");
            }

            //网络恢复通知
            @Override
            public void onNetworkRecovery(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onNetworkRecovery ==  网络恢复");
            }

            //重连开始通知
            @Override
            public void onReconnectStart(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onReconnectStart ==  重连开始");
            }

            //重连失败通知
            @Override
            public void onReconnectFail(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onReconnectFail ==  重连失败");
            }

            //重连成功通知
            @Override
            public void onReconnectSucceed(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onReconnectSucceed ==  重连成功");
            }

            //发送数据超时通知
            @Override
            public void onSendDataTimeout(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onSendDataTimeout ==  发送数据超时通知");
            }

            //连接失败通知
            @Override
            public void onConnectFail(AlivcLivePusher alivcLivePusher) {
                L.i(initTag(), "onConnectFail ==  连接失败通知");
            }

            //推流地址鉴权即将过期会回调
            @Override
            public String onPushURLAuthenticationOverdue(AlivcLivePusher alivcLivePusher) {
                return null;
                //return "新的推流地址 rtmp://";
            }

            @Override
            public void onSendMessage(AlivcLivePusher alivcLivePusher) {

            }
        });


        mLivePusher.setLivePushBGMListener(new AlivcLivePushBGMListener() {

            //播放开始通知
            @Override
            public void onStarted() {
                L.i(initTag(),"onStarted = 播放开始通知");
            }

            //播放停止通知
            @Override
            public void onStoped() {
                L.i(initTag(),"onStoped = 播放停止通知");
            }

            //播放暂停通知
            @Override
            public void onPaused() {
                L.i(initTag(),"onPaused = 播放暂停通知");
            }

            //播放恢复通知
            @Override
            public void onResumed() {
                L.i(initTag(),"onPaused = 播放恢复通知");
            }

            //播放进度
            @Override
            public void onProgress(long l, long l1) {
                L.i(initTag(),"onPaused = 播放进度通知");
            }

            //播放结束通知
            @Override
            public void onCompleted() {
                L.i(initTag(),"onCompleted = 播放结束通知");
            }

            //播放器超时事件
            @Override
            public void onDownloadTimeout() {
                L.i(initTag(),"onDownloadTimeout = 播放器超时事件");
            }

            //流无效通知，在这里提示流不可访问
            @Override
            public void onOpenFailed() {
                L.i(initTag(),"onOpenFailed = 流无效通知");
            }
        });

//        mLivePusher.startPreview(mSurfaceView);//开始预览，也可根据需求调用异步接口startPreviewAysnc来实现
//        mLivePusher.startPush(mPushUrl);// 开始推流

    }

    @Override
    public int layoutId() {
        return R.layout.fragment_push;
    }

    @OnClick({R.id.push_live})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.push_live:
                if (mAsync) {
                    mLivePusher.startPushAysnc("");
                } else {
                    mLivePusher.startPush("");
                }
                break;
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        showContentView();
        if(mLivePusher != null) {
            initPusher();
            isPushing = mLivePusher.isPushing();
        }
    }

    @Override
    public void loading(int resId) {

    }

    @Override
    public void loading(int resId, boolean cancelAble) {

    }


    public static LivePushFragment newInstance(String url, boolean async, boolean mAudio, boolean mVideoOnly , int cameraId, boolean isFlash, int mode, String authTime, String privacyKey, boolean mixExtern, boolean mixMain) {
        LivePushFragment livePushFragment = new LivePushFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, url);
        bundle.putBoolean(ASYNC_KEY, async);
        bundle.putBoolean(AUDIO_ONLY_KEY, mAudio);
        bundle.putBoolean(VIDEO_ONLY_KEY, mVideoOnly);
        bundle.putInt(QUALITY_MODE_KEY,mode);
        bundle.putInt(CAMERA_ID, cameraId);
        bundle.putBoolean(FLASH_ON, isFlash);
        bundle.putString(AUTH_TIME, authTime);
        bundle.putString(PRIVACY_KEY, privacyKey);
        bundle.putBoolean(MIX_EXTERN, mixExtern);
        bundle.putBoolean(MIX_MAIN, mixMain);
        livePushFragment.setArguments(bundle);
        return livePushFragment;
    }

    public void setAlivcLivePusher(AlivcLivePusher alivcLivePusher) {
        this.mLivePusher = alivcLivePusher;
    }

    public AlivcLivePusher getLivePusher() {
        return mLivePusher;
    }

}
