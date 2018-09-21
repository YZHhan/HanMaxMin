package com.han.hanmaxmin.pushvideo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.alivc.component.custom.AlivcLivePushCustomDetect;
import com.alivc.component.custom.AlivcLivePushCustomFilter;
import com.alivc.live.filter.TaoBeautyFilter;
import com.alivc.live.pusher.AlivcAudioAACProfileEnum;
import com.alivc.live.pusher.AlivcBeautyLevelEnum;
import com.alivc.live.pusher.AlivcFpsEnum;
import com.alivc.live.pusher.AlivcLivePushBGMListener;
import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushError;
import com.alivc.live.pusher.AlivcLivePushErrorListener;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePushNetworkListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.AlivcPreviewDisplayMode;
import com.alivc.live.pusher.AlivcPreviewOrientationEnum;
import com.alivc.live.pusher.AlivcQualityModeEnum;
import com.alivc.live.pusher.AlivcResolutionEnum;
import com.alivc.live.pusher.SurfaceStatus;
import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;
import com.han.hanmaxmin.pushvideo.fragment.LivePushFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/9/19 14:48
 * @Description
 */
public class PushLiveActivity extends HanABActivity{


    // 推流参数类
    private AlivcLivePushConfig mLivePushConfig;
    // 推流器
    private AlivcLivePusher mLivePusher;
    //
    private TaoBeautyFilter mTaoBeautyFilter;
    //视频入口
    @Bind(R.id.preview_view)
    SurfaceView mSurfaceView ;
    @Bind(R.id.tv_pager)
    ViewPager mViewPager;

    private LivePushFragment mLivePushFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;



    private SurfaceStatus mSurfaceStatus = SurfaceStatus.UNINITED;
    private boolean mAsync = false;


    @Override
    public int layoutId() {
        return R.layout.activity_push_live;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initSurfaceView();
        initPushConfig();
        initPusher();
        mLivePushFragment = new LivePushFragment().newInstance("mPushUrl", mAsync, true, true, 1, true, 2, "", "", true, true);
        mLivePushFragment.setAlivcLivePusher(mLivePusher);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.tv_pager);
        mFragmentList.add(mLivePushFragment);
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList) ;
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() >= 2) {
//                    mScaleDetector.onTouchEvent(motionEvent);
                } else if (motionEvent.getPointerCount() == 1) {
//                    mDetector.onTouchEvent(motionEvent);
                }
//                }
                return false;
            }
        });

    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    private void initSurfaceView(){
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if(mSurfaceStatus == SurfaceStatus.UNINITED) {
                    mSurfaceStatus = SurfaceStatus.CREATED;
                    if(mLivePusher != null) {
                        try {
                            if(mAsync) {
                                mLivePusher.startPreviewAysnc(mSurfaceView);
                            } else {
                                mLivePusher.startPreview(mSurfaceView);
                            }
                            if(mLivePushConfig.isExternMainStream()) {
//                                startYUV(getApplicationContext());
                            }
                        } catch (IllegalArgumentException e) {
                            e.toString();
                        } catch (IllegalStateException e) {
                            e.toString();
                        }
                    }
                } else if(mSurfaceStatus == SurfaceStatus.DESTROYED) {
                    mSurfaceStatus = SurfaceStatus.RECREATED;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    /**
     * 初始化  推流参数配置
     */
    public void initPushConfig(){
        mLivePushConfig = new AlivcLivePushConfig();
        mLivePushConfig.setResolution(AlivcResolutionEnum.RESOLUTION_540P);// //分辨率540P，最大支持720P
        mLivePushConfig.setFps(AlivcFpsEnum.FPS_20);//建议用户使用20fps
        mLivePushConfig.setEnableBitrateControl(true); // 打开码率自适应，默认为true
        mLivePushConfig.setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT);//默认为竖屏，可设置home键向左或向右横屏。
        mLivePushConfig.setAudioProfile(AlivcAudioAACProfileEnum.AAC_LC);//设置音频编码模式
        mLivePushConfig.setEnableBitrateControl(true);// 打开码率自适应，默认为true
        mLivePushConfig.setQualityMode(AlivcQualityModeEnum.QM_RESOLUTION_FIRST);//清晰度优先或流畅度优先模式
        mLivePushConfig.setEnableAutoResolution(true); // 打开分辨率自适应，默认为false
        mLivePushConfig.setBeautyOn(true); // 开启美颜
        mLivePushConfig.setBeautyLevel(AlivcBeautyLevelEnum.BEAUTY_Normal);//设定为基础美颜
        mLivePushConfig.setBeautyWhite(70);// 美白范围0-100
        mLivePushConfig.setBeautyBuffing(40);// 磨皮范围0-100
        mLivePushConfig.setBeautyRuddy(40);// 红润设置范围0-100
        mLivePushConfig.setPausePushImage("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=5&spn=0&di=24493386940&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2715947904%2C2962931779&os=771178767%2C3303560554&simid=4168438834%2C674433752&adpicid=0&lpn=0&ln=3638&fr=&fmq=1537345460054_R&fm=detail&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=wallpaper&bdtype=0&oriquery=&objurl=http%3A%2F%2Fwww.wallcoo.com%2Fcartoon%2Fabstract_colors_1920x1200_0917%2Fwallpapers%2F1920x1200%2Fabstract_lights_and_colors_seupt_red.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bowssv55_z%26e3Bv54AzdH3Fvw6p55gAzdH3Fwkfp6wvp_v5s56f_8ldax8daa_al80AzdH3Fowssrwrj6fAzdH3F8ldax8daaAzdH3FAkfp6wvp_Lt2ipf_wg1_v5s56f_Sj7rp_Rj1_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=");//设置用户后台推流的图片
        mLivePushConfig.setNetworkPoorPushImage("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%A3%81%E7%BA%B8&step_word=&hs=0&pn=5&spn=0&di=24493386940&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2715947904%2C2962931779&os=771178767%2C3303560554&simid=4168438834%2C674433752&adpicid=0&lpn=0&ln=3638&fr=&fmq=1537345460054_R&fm=detail&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=wallpaper&bdtype=0&oriquery=&objurl=http%3A%2F%2Fwww.wallcoo.com%2Fcartoon%2Fabstract_colors_1920x1200_0917%2Fwallpapers%2F1920x1200%2Fabstract_lights_and_colors_seupt_red.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bowssv55_z%26e3Bv54AzdH3Fvw6p55gAzdH3Fwkfp6wvp_v5s56f_8ldax8daa_al80AzdH3Fowssrwrj6fAzdH3F8ldax8daaAzdH3FAkfp6wvp_Lt2ipf_wg1_v5s56f_Sj7rp_Rj1_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=");//设置网络较差时推流的图片
//        mLivePushConfig.addWaterMark(waterPath, 0.1, 0.2, 0.3);//添加水印
        mLivePushConfig.setFocusBySensor(true);
        mLivePushConfig.setPreviewDisplayMode(AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FIT);
    }

    private void initPusher(){
        mLivePusher = new AlivcLivePusher();
        try {
            mLivePusher.init(getApplicationContext(),mLivePushConfig);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            HanToast.show(e.getMessage());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            HanToast.show(e.getMessage());
        }

        /**
         * 人脸识别回调
         */
        mLivePusher.setCustomDetect(new AlivcLivePushCustomDetect() {
            @Override
            public void customDetectCreate() {

            }

            @Override
            public long customDetectProcess(long l, int i, int i1, int i2, int i3, long l1) {
                return 0;
            }

            @Override
            public void customDetectDestroy() {

            }
        });


        /**
         * 美颜回调
         */
        mLivePusher.setCustomFilter(new AlivcLivePushCustomFilter() {
            @Override
            public void customFilterCreate() {
                mTaoBeautyFilter = new TaoBeautyFilter();
                mTaoBeautyFilter.customFilterCreate();

            }

            @Override
            public void customFilterUpdateParam(float v, float v1, float v2, float v3, float v4, float v5, float v6) {
                if (mTaoBeautyFilter != null) {
                    mTaoBeautyFilter.customFilterUpdateParam(v, v1, v2, v3, v4, v5, v6);
                }
            }

            @Override
            public void customFilterSwitch(boolean b) {
                if(mTaoBeautyFilter != null) {
                    mTaoBeautyFilter.customFilterSwitch(b);
                }
            }

            @Override
            public int customFilterProcess(int inputTexture, int textureWidth, int textureHeight, long extra) {
                if (mTaoBeautyFilter != null) {
                    return mTaoBeautyFilter.customFilterProcess(inputTexture, textureWidth, textureHeight, extra);
                }
                return inputTexture;
            }

            @Override
            public void customFilterDestroy() {
                if (mTaoBeautyFilter != null) {
                    mTaoBeautyFilter.customFilterDestroy();
                }
                mTaoBeautyFilter = null;
            }
        });


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
    public int actionbarLayoutId() {
        return 0;
    }

    public class FragmentAdapter extends FragmentPagerAdapter{

        List<Fragment> fragmentList = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
