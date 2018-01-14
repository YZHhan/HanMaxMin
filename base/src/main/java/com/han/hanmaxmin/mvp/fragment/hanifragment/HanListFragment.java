package com.han.hanmaxmin.mvp.fragment.hanifragment;

import android.widget.AbsListView;
import android.widget.AdapterView;

import com.han.hanmaxmin.common.widget.viewpager.headerviewpager.InnerScrollerContainer;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  16:31
 * @Description
 */

public abstract class HanListFragment<P extends HanPresenter, D> extends HanFragment<P> implements HanIListFragment<D>, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener,InnerScrollerContainer {

}
