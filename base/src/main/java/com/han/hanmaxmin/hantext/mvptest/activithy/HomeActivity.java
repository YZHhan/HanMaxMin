package com.han.hanmaxmin.hantext.mvptest.activithy;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hubert.library.Controller;
import com.app.hubert.library.HighLight;
import com.app.hubert.library.NewbieGuide;
import com.app.hubert.library.OnGuideChangedListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.Common;
import com.han.hanmaxmin.common.aspect.permission.Permission;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.CommonUtils;
import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.common.widget.refreshHeader.BeautyCircleDrawable;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.hantext.mvptest.MineActivity;
import com.han.hanmaxmin.hantext.mvptest.fragment.DataBaseListFragment;
import com.han.hanmaxmin.hantext.mvptest.fragment.HomePullListFragment;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;
import com.han.hanmaxmin.mvp.common.UserConfig;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;
import com.han.hanmaxmin.proxy.doingproxy.HanSubject;
import com.han.hanmaxmin.pushvideo.PushLiveActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomeActivity extends HanABActivity <HomePullPresenter> {

//    @Bind(R.id.tv_title)
//    TextView ll_linear;
//    @Bind(R.id.thread_http)
//    TextView thread_http;

//    @Override
//    public void initData(Bundle savedInstanceState) {
//        L.i("HanMaxMin", "我是一个Activity");
//         Controller controller1 = NewbieGuide.with(this)
//                .setOnGuideChangedListener(new OnGuideChangedListener() {
//                    @Override
//                    public void onShowed(Controller controller) {// 引导层显示
//                        HanToast.show("nihao");
//                    }
//
//                    @Override
//                    public void onRemoved(Controller controller) {//引导层消失
//                        HanToast.show("removed");
//                    }
//                })
//                .alwaysShow(true)
//                .addHighLight(thread_http, HighLight.Type.RECTANGLE)//
//                .setLayoutRes(R.layout.text_null1)//
//                .setLabel("Text")
//                .build();
//        Controller controllerJiaoCai = NewbieGuide.with(this)
//                .setOnGuideChangedListener(new OnGuideChangedListener() {
//                    @Override
//                    public void onShowed(Controller controller) {// 引导层显示
//                        HanToast.show("nihao");
//                    }
//
//                    @Override
//                    public void onRemoved(final Controller controller) {//引导层消失
//
//                        final Controller wocao = NewbieGuide.with(HomeActivity.this)
//                                .setLabel("WOCAO")//
//                                .addHighLight(thread_http, HighLight.Type.RECTANGLE)//
//                                .alwaysShow(true)
//                                .setEveryWhereCancelable(true)
//                                .setLayoutRes(R.layout.text_null1)//
//                                .show();
//                    }
//                })
//                .alwaysShow(true)
//                .addHighLight(ll_linear, HighLight.Type.RECTANGLE)//
//                .setLayoutRes(R.layout.text_null)//
//                .setLabel("Title")
//                .build();
//        controllerJiaoCai.show();

//        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
//        View inflate = LayoutInflater.from(this).inflate(R.layout.text_null, null);
//        decorView.addView(inflate);
//
//
//        commitFragment(new DataBaseListFragment());
//    }

    @Override
    public int layoutId() {
        return R.layout.activity_main2;
    }

    @Permission({Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void initData(Bundle savedInstanceState) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_image);

//        Picasso.with(this)
//                .load("http://www.jcodecraeer.com/uploads/20150327/1427445293711143.png")
//                .into(imageView);

        //--------------------Glide4------------------
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .override(400,200)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.iv_madiaview_question);
        Glide.with(this)
//                .load("http://www.jcodecraeer.com/uploads/20150327/1427445293711143.png")
                .asBitmap()
                .load("http://guolin.tech/test.gif")
                .apply(options)
                .into(imageView);

//        UserConfig.getInstance().UserLike = "111";
//        commitFragment(new HomePullListFragment());

    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override
    public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }

//    @OnClick({R.id.thread_http,R.id.thread_main, R.id.thread_work, R.id.thread_single})
//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.thread_http:
//                HanToast.show("http");
//                L.i("UserConfig",UserConfig.getInstance().UserName);
//                L.i("UserConfig", "CommonUtils.getLocalTime()"+CommonUtils.getLocalTime());
//                CommonUtils.isTimeOut("2018-01-01 23:59:59");
//                break;
//            case R.id.thread_main:
//                HanToast.show("main");
//                UserConfig.getInstance().UserName = "YinZiHan";
//                UserConfig.getInstance().UserAge = "18";
//                UserConfig.getInstance().UserPhone = "0120";
//                UserConfig.getInstance().UserHeight = "177";
//                UserConfig.getInstance().UserSex = "MAN";
//                UserConfig.getInstance().UserLike = "I Like You";
//                UserConfig.getInstance().commit();
//                break;
//            case R.id.thread_work:
//                HanToast.show("work");
//                break;
//            case R.id.thread_single:
//                HanToast.show("single");
//                break;
//        }
//    }




    @OnClick({R.id.iv_image})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_image:
                HanToast.show("hah");
//                initGeneric(this);

                intent2Activity(PushLiveActivity.class);
                break;
        }
    }


    /**
     *
     * @param mView
     */
    private void initGeneric(HomeActivity mView){
        Class<? extends HomeActivity> aClass = mView.getClass();
        L.i(initTag(),"mView.getClass = " + aClass);
        Type superclassType = aClass.getGenericSuperclass();//反射获取带泛型的class
        L.i(initTag(),"aClass.getGenericSuperclass() = " + superclassType);
        if(superclassType instanceof ParameterizedType){//ParameterizedType  是一个接口，用来检验泛型是否被参数化
            Type rawType = ((ParameterizedType) superclassType).getRawType();
            L.i(initTag(),".getRawType() = " + rawType);

            Type[] actualTypeArguments = ((ParameterizedType) superclassType).getActualTypeArguments();
            for (Type type : actualTypeArguments){

                if (type instanceof  ParameterizedType){
                    Type rawType1 = ((ParameterizedType) type).getRawType();
                    Type ownerType = ((ParameterizedType) type).getOwnerType();
                    L.i(initTag(),".getRawType(1) = " + rawType1);
                    L.i(initTag(),".getOwnerType(1) = " + ownerType);

                }

                if(actualTypeArguments != null && actualTypeArguments.length > 0){
                    Class tempImpl = (Class) type;
                    try {
                        Object instance = tempImpl.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                L.i(initTag(), "actualTypeArguments = " + type);
            }
        }



    }





}
