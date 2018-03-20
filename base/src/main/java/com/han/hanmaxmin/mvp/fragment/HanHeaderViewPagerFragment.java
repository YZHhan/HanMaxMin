package com.han.hanmaxmin.mvp.fragment;

import com.han.hanmaxmin.mvp.fragment.HanIHeaderViewPagerFragment;
import com.han.hanmaxmin.mvp.fragment.HanViewPagerFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/3/20.
 */

public abstract class HanHeaderViewPagerFragment <P extends HanPresenter> extends HanViewPagerFragment<P> implements HanIHeaderViewPagerFragment {
}
