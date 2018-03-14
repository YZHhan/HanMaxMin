package com.han.hanmaxmin.common.widget.viewpager.headerpager.base;

/**
 * Created by ptxy on 2018/3/8.
 * InnerScroller's Container,which Fragment that contains a InnerScroller must implements.
 */

public interface InnerScrollerContainer {
    void setMyOuterScroller(OuterScroller outerScroller, int myPosition);

}
