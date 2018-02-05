package com.han.hanmaxmin.common.utils.deeplink;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.han.hanmaxmin.mvp.fragment.HanFragment;

/**
 * Created by ptxy on 2018/1/31.
 *
 * 网页视图
 */

public class WebFragment extends HanFragment {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean shouldInterceptTouchEvent() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public int layoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override
    public void loading() {

    }

    @Override
    public void loading(String message) {

    }

    @Override
    public void loading(boolean cancelAble) {

    }

    @Override
    public void loading(String message, boolean cancelAble) {

    }

    @Override
    public void loadingClose() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showContentView() {

    }

    @Override
    public int currentViewState() {
        return 0;
    }

    @Override
    public void intent2Activity(Class clazz) {

    }

    @Override
    public void intent2Activity(Class clazz, int requestCode) {

    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle) {

    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {

    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {

    }

    @Override
    public void commitFragment(Fragment fragment) {

    }

    @Override
    public void commitFragment(Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(int layoutId, Fragment fragment) {

    }

    @Override
    public void commitFragment(int layoutId, Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment, String trg) {

    }

    @Override
    public void commitBackStackFragment(Fragment fragment) {

    }

    @Override
    public void commitBackStackFragment(Fragment fragment, String trg) {

    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment) {

    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment, String trg) {

    }
}
