package com.han.hanmaxmin.mvp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2017/11/14  15:41
 * @Description
 */

public abstract class HanFragment<P extends HanPresenter> extends Fragment implements HanIFragment, View.OnTouchListener {



}
