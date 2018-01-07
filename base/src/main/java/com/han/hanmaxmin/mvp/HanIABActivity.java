package com.han.hanmaxmin.mvp;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:19
 * @Description     作为一个有Actionbar 的Activity
 *
 */

public interface HanIABActivity extends HanIActivity {
    int actionbarLayoutId();

    void setActivityTitle(Object value, int code);
}
