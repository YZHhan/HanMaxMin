package com.han.hanmaxmin.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.dialog.HanProgressDialog;
import com.han.hanmaxmin.mvp.HanIActivity;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2017/11/14  15:41
 * @Description
 */

public abstract class HanFragment<P extends HanPresenter> extends Fragment implements HanIFragment, View.OnTouchListener {
    private P presenter;
    private boolean hasInitData;
    private boolean enableBackgroundColor;
    protected HanProgressDialog mProgressDialog;
    protected ViewAnimator mViewAnimator;

    /**
     * Get the name of the current class as a TAG
     * @return Class TAG
     */
    @Override public String initTag() {
        return getClass().getSimpleName();
    }

    /**
     * Set the current Activity of the title.
     * @param value
     */
    @Override public void setActivityTitle(Object value) {
        setActivityTitle(value,-1);
    }

    @Override public void setActivityTitle(Object value, int code) {
        FragmentActivity activity = getActivity();
        if (activity instanceof HanIActivity){//待完善
            L.i(initTag(),"setActivityTitle()  title="+String.valueOf(value)+" code="+code);
        }
    }

    @Override public void onActionBar() {

    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView=initView(inflater);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected  View initView(LayoutInflater inflater){
        View view;
        if(isOpenViewState() && loadingLayoutId()>0 && emptyLayoutId()>0 && errorLayoutId()>0){
            view=inflater.inflate(rootViewLayoutId(),null);
            mViewAnimator= (ViewAnimator) view.findViewById(android.R.id.home);
            inflater.inflate(loadingLayoutId(),mViewAnimator);
            inflater.inflate(emptyLayoutId(),mViewAnimator);
            inflater.inflate(errorLayoutId(),mViewAnimator);
            View inflate = inflater.inflate(layoutId(), null);
            if(enableBackgroundColor)view.setBackgroundColor(HanHelper.getInstance().getColor(getBackgroundColorId()));
        }else {
            view=inflater.inflate(layoutId(),null);
            if(enableBackgroundColor)view.setBackgroundColor(HanHelper.getInstance().getColor(getBackgroundColorId()));
        }
        return view;
    }

    @Override public P getPresenter() {
        if(presenter==null){
            synchronized (this){
                if(presenter==null){
//                    presenter= PresenterUtils.//待完善
                    L.i(initTag(), "Presenter初始化完成。。。");
                }
            }
        }
        return presenter;
    }

    @Override public void initDataWhenDelay() {
        if(!hasInitData && isDelayDate()){
            initData(getArguments());
            hasInitData=true;
        }
    }

    @Override public boolean isOpenEventBus() {
        return false;
    }

    @Override public boolean isOpenViewState() {
        return true;
    }

    @Override public boolean isDelayDate() {
        return false;
    }

    @Override public void activtyFinish() {
        activtyFinish(false);
    }

    @Override public void activtyFinish(boolean finishAfterTransition) {
        if(finishAfterTransition){
            ActivityCompat.finishAfterTransition(getActivity());
        }else{
            getActivity().finish();
        }
    }




    protected int rootViewLayoutId() {
        return R.layout.han_fragment_state;
    }



    @Override public int loadingLayoutId() {
        return HanHelper.getInstance().getApplication().loadingLayoutId();
    }

    @Override public int emptyLayoutId() {
        return HanHelper.getInstance().getApplication().emptyLayoutId();
    }


    @Override public int errorLayoutId() {
        return HanHelper.getInstance().getApplication().errorLayoutId();
    }

    @Override public int getBackgroundColorId() {
        return R.color.color_bg;
    }
}
