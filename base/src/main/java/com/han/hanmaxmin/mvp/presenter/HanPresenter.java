package com.han.hanmaxmin.mvp.presenter;

import android.content.Context;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.constants.HanConstants;
import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.exception.HanExceptionType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.model.HanModel;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.mvp.HanIView;
import com.han.hanmaxmin.mvp.fragment.HanIPullListFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullListFragment;

/**
 * @CreateBy Administrator
 * @Date 2017/11/7  10:37
 * @Description
 */

public class HanPresenter<V extends HanIView> {
    private boolean isAttach ;//  是否附加。。
    private V  mView;

    protected String initTag() {
        return getClass().getSimpleName();
    }

    /**
     * 得到View的Context。
     */
    public Context getContext(){
        if(!isViewDetach())return mView.getContext();
        return null;
    }

    /**
     * 初始化Presenter  也就是在PresenterUtils里面调用。给次view赋值。
     * @param view
     */
    public void initPresenter(V  view){
       isAttach = true;
       mView = view;
    }

    /**
     * 会做一个判断。如果当前线程不属于自定义的线程的话，当前方法不能使用。。。。
     * @return
     */
    public V getView(){
        if(isViewDetach()){//判断View  是否分离。
            String threadName = Thread.currentThread().getName();
            switch (threadName){
                case HanConstants.NAME_HTTP_THREAD:
                case HanConstants.NAME_SINGLE_THREAD:
                case HanConstants.NAME_WORK_THREAD:
                    throw new HanException(HanExceptionType.CANCEL, null, "current thread :"+ threadName+ "execute" + getClass().getSimpleName() +".getView() return null, maybe view"+(mView == null ? "" : "("+mView.getClass().getSimpleName()+ ")") + "is  destory。。。。");
                default:
                    throw new HanException(HanExceptionType.CANCEL, null, "当前线程:" + threadName + "执行" + getClass().getSimpleName() + ".getView()方法返回null, 因为View层" + (mView == null ? "" : "(" + mView.getClass().getSimpleName() + ")") + "销毁了，为了规避这种风险，请不要在Presenter里面通过非@ThreadPoint注解的方式创建线程并在该线程中调用getView()方法...");
            }
        }
    return mView;
    }

    public void setDetach(){
        isAttach =false;
        cancelHttpRequest();
    }


    /**
     * 取消当前Presenter发出的HTTP 请求
     */
    protected void cancelHttpRequest() {
        try {
            HanHelper.getInstance().getHttpHelper().cancelRequest(getClass().getSimpleName());
        }catch (Exception e){
            L.e(initTag(), "cancel http request failed :" +e.getMessage());
        }
    }

    protected <T> T createHttpRequest(Class<T> clazz) {
        return createHttpRequest(clazz, clazz.getSimpleName());
    }

    protected <T> T createHttpRequest(Class <T> clazz, String requestTag){
        return HanHelper.getInstance().getHttpHelper().create(clazz, requestTag);
    }


    protected String getString(int stringId){
       return HanHelper.getInstance().getApplication().getString(stringId);
    }

    protected boolean isSuccess(HanModel baseModel){
            return  isSuccess(baseModel, false);
    }

    /**
     * 请求网络成功，判断数据的完整性。
     */
    protected boolean isSuccess(HanModel baseModel, boolean shouldToast){
        if(baseModel != null && baseModel.isResponseOK()){
            return true;
        }else if (!isViewDetach()){
            resetViewState();
            if(baseModel != null && shouldToast) HanToast.show(baseModel.getMessage());
        }
        return false;
    }

    /**
     * 还原View 的状态。。
     * 待完善、、、、
     */
    @ThreadPoint(ThreadType.MAIN) private void resetViewState() {
        if(!isViewDetach()){
            HanIView hanView = getView();
            if (hanView instanceof HanIPullListFragment){
                HanPullListFragment view = (HanPullListFragment) hanView;
                view.stopRefreshing();
                view.setLoadingState(LoadingFooter.State.NetWorkError);
            }
            hanView.loadingClose();
        }

    }



    /**
     * 自定义异常处理。
     */
    public void methodError(HanException exception) {
        L.e(initTag(), "methodError... errorType:" + exception.getExceptionType() + " requestTag:" + exception.getRequestTag() + " errorMessage:" + exception.getMessage());
        switch (exception.getExceptionType()) {
            case HTTP_ERROR:
            case NETWORK_ERROR:
            case UNEXPECTED:
            case CANCEL:
                break;
        }
        resetViewState();    }

    /**
     * 是否View 分离。
     */
    public boolean isViewDetach(){
        return !isAttach || mView == null;
    }

}
