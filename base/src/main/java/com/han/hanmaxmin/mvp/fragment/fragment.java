package com.han.hanmaxmin.mvp.fragment;


import com.han.hanmaxmin.common.exception.HanException;

import org.aspectj.lang.ProceedingJoinPoint;

public class fragment{


/**
 * Fragment 封装
 */
//--------------------------页面的异常处理-------------------------------
            // Http请求中的错误。
            //页面数据错误
    /**
     * showContent
     * showError
     * showLoad
     * showEmpty
     * {@link com.han.hanmaxmin.common.aspect.ThreadAspect#startOriginalMethod(ProceedingJoinPoint)}
     * 在线程Aspect中会引用methodError。并且在展示error页面。
     * {@link com.han.hanmaxmin.mvp.presenter.HanPresenter#methodError(HanException)}
     *
     * {@link HanFragment#setViewState(int)}
     * 在View层都会有， 对data的add set remove get，然后进行updateAdapter。{@link HanFragment#showEmptyView() showContentView}
     *
     *
     */



//--------------------------HanIView-------------------------------------
        //View 的顶层父类
        //initTag  layoutId
        // getPresenter getContext
        // initData  isDelayData initDataWhenDelay
        // isOpenEventBus isOpenViewState isShowBackButtonInDefaultView
        //activityFinish
        //loading showErrorView showEmptyView  showContentView showLoadingView currentViewState
        //intentActivity   commitFragment commitBackStackFragment


//--------------------------HanIFragment-------------------------------------
        //setActivityTitle onActionbar 对Activity进行操作
        //shouldInterceptTouchEvent
        //onKeyDown 返回键
        //emptyLayoutId  loadLayoutId  errorLayoutId getBackgroundColorId 布局



//--------------------------HanFragment--------------------------------------
                        //Fragment 的父类。
        //extends Fragment  implements HanIFragment OnTouchListener
        // ViewAnimator  是FrameLayout的一个子类，用来做View的切换。它是一个变换控件的元素，帮助我们的View之间(如TextView  和  ImageView和其他的Layout)
        //添加变换。他属于在屏幕VIew添加动画。ViewAnimator可以在两个以及以上Views上平滑的切换，通过合适的动画，提供一个View到另一个View变换的方式。
        //主要参数 P dialog  ViewAnimator hasInitData backgroundColorId
        //initData:onActivityCreate 会进行initData， initView的initDefaultView会进行， initDataWhenDelay   setViewState.
        //setActivity instanceof HanIABActivity
        //onCreateView  initView初始化
        //         初始化的判断条件（isOpenViewState() && loadingLayoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0）
        //               inflater rootViewLayout()<第一个参数是xml布局，第二个参数是返回的父布局控件 该XMl只有一个ViewAnimator>
        //               初始化组件ViewAnimator的对象，对loadingLayoutId errorLayoutId emptyLayoutId 进行inflater,将ViewAnimator作为父布局控件
        //                对LayoutId进行inflater，是否设置背景。Fragment的背景。考虑性能。View的绘制层级。
        //                          1，窗口按层级划分，最底层是windowBackground，所有Activity都会绘制先绘制这一层
        //                          2，往上一层是Activity的setContentView，如果这个Activity的layout设置了背景，则改页面被过度绘制一次
        //                          3，再往上就是Activity commit了的Fragment
        //                          xml嵌套太深会造成布局读取时间延长，但不会导致过度绘制，过度绘制的本质是两个View重叠了，
        //                          上面的View盖住下面的View，假如这两个View都设置background，就会导致两个View重叠部分被绘制两次，
        //                           而下面的View'是看不见的，所以就导致了没有意义的Gpu消耗
        //              initDefaultView
        //              false :仅仅inflater layoutId设置背景色。
        // initDataWhenDelay:对hasInitData 和 isDelayData进行判断，true  进行iniData（）；
        //loadingLayoutId   errorLayoutId  emptyLayoutId  由Application统一处理。
        // loading 封装。注意线程。
        //showLoadView  showEmptyView   showErrorView  showContentView  最后都有setViewState决定。
        //          首先判断View的状态是否打开。ViewAnimator是否为空  getDisplayedChild   得到当前的索引。
        //intent2Activity   commitFragment




//

//---------------------------HanIListFragment--------------------------------
    /**
     * getHeaderLayout getFooterLayout  getTopLayout getBottomLayout   布局
     * getViewTypeCount  getItemViewType ListView的复杂布局实现
     * getListAdapterItem 得到适配器的item，真正的适配器封装在Fragment里面
     * setData  addData  delete  getData 对数据进行操作，可以是集合也可与是泛型
     * updateAdapter onCreateAdapter getAdapter 适配器
     *
     */



//---------------------------HanListFragment----------------------------------

            // 泛型<P extends HanPresenter, D>
            //extends HanFragment
            //implements HanListFragment AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener,InnerScrollerContainer

/**
 * 这是一个Fragment和ListView的结合封装。数据和View的封装。
 * initView 方法体里面  借用super调用父类的的initView  对View进行初始化
 *         1.初始化的判断条件（isOpenViewState() && loadingLayoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0）等等。主要是根据ViewAnimator作为跟布局
 *         2.initTopBottomView（）将 getTopLayout 和 getBottomLayout  添加到view中。
 *         initListView：添加headerView  和  footerView。设置监听（item 和longItem 和滑动监听）设置适配器。
 *         MyAdapter：此处的有点，将ListView的适配器封装，对外暴露操作的API。
 *         主要有两块：复杂ListView 和 getView的适配。
 *              A:在HanListFragment里面，写入getItemViewType  和  getViewTypeCount .在适配器的方法里面调用Fragment的方法，关联。
 *              B.getView中。声明单条Item的Adapter。（HanListAdapterItem）
 *         3.setData  addData  delete  updateAdapter——对数据进行操作的时候，数据改变。adapter刷新。ViewAnimator进行显示。说白了也就是，数据的改变，view的刷新。
 *         4.
 *
 *
 *
 *
 *
 */
}