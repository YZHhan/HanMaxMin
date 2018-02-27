package com.han.hanmaxmin.hantext.httptext;

import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.mvp.HanIView;
import com.han.hanmaxmin.mvp.fragment.hanifragment.HanIPullListFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/2/27.
 */

public class HttpPresenter <V extends HanIView> extends HanPresenter<V>{
    @Override
    public void methodError(HanException exception) {
        super.methodError(exception);
    }




}
