package com.han.hanmaxmin.common.widget.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStructure;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.widget.recyclerview.HeaderFooterRecyclerAdapter;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  17:12
 * @Description  ListView/GridView/RecyclerView分页加载时用到的FooterView
 */

public class LoadingFooter extends RelativeLayout implements HeaderFooterRecyclerAdapter.OnRecyclerViewAdapterBindViewHolder{

    private static final String TAG = "LoadingFooter";

    private State mState;
    private View  mNormalView;
    private View  mLoadingView;
    private View  mNetWorkErrorView;
    private View  mTheEndView;



    public LoadingFooter(Context context) {
        super(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        inflate(context, R.layout.han_layout_footer_main, this);
        setState(State.Normal);
    }

    @Override
    public void onAdapterBindViewHolder() {

    }


    public void setState(State states) {
        L.i(TAG, "setState  state:  " +states);
        if(states == null){
            return;
        }
        this.mState = states;
        setViewState(states);//  ------
    }

    public State getState(){
        return mState;
    }
    /**
     * 设置View  状态
     */
    public void setViewState(State status) {
       switch (status){
           case Normal:
               if(mTheEndView != null)mTheEndView.setVisibility(GONE);
               if(mNetWorkErrorView != null)mNetWorkErrorView.setVisibility(VISIBLE);
               if(mLoadingView != null)mLoadingView.setVisibility(GONE);
               if(mNormalView != null){
                   mNormalView.setVisibility(VISIBLE);
               } else {
                   ViewStub viewStub = (ViewStub) findViewById(R.id.normal_viewStub);
                   mNormalView = viewStub.inflate();
               }
               break;

           case Loading:
               if (mTheEndView != null) mTheEndView.setVisibility(GONE);
               if (mNetWorkErrorView != null) mNetWorkErrorView.setVisibility(GONE);
               if (mNormalView != null) mNormalView.setVisibility(GONE);
               if (mLoadingView != null) {
                   mLoadingView.setVisibility(VISIBLE);
               } else {
                   ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewStub);
                   mLoadingView = viewStub.inflate();//  空指针出现过
               }
               break;

           case TheEnd:
               if (mLoadingView != null) mLoadingView.setVisibility(GONE);
               if (mNetWorkErrorView != null) mNetWorkErrorView.setVisibility(GONE);
               if (mNormalView != null) mNormalView.setVisibility(GONE);
               if (mTheEndView != null) {
                   mTheEndView.setVisibility(VISIBLE);
               } else {
                   ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewStub);
                   mTheEndView = viewStub.inflate();
               }
               break;

           case NetWorkError:
               if (mLoadingView != null) mLoadingView.setVisibility(GONE);
               if (mTheEndView != null) mTheEndView.setVisibility(GONE);
               if (mNormalView != null) mNormalView.setVisibility(GONE);
               if (mNetWorkErrorView != null) {
                   mNetWorkErrorView.setVisibility(VISIBLE);
               } else {
                   ViewStub viewStub = (ViewStub) findViewById(R.id.network_error_viewStub);
                   mNetWorkErrorView = viewStub.inflate();
               }
               break;
           default:
               break;
       }
    }

    public enum State {
        Normal,
        TheEnd,
        Loading,
        NetWorkError
    }
}
