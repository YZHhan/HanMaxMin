package com.han.hanmaxmin.mvp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:30
 * @Description
 */

public class HanViewPagerABActivity implements HanIViewPagerABActivity {
    @Override public int actionbarLayoutId() {
        return 0;
    }

    @Override public void setActivityTitle(Object value, int code) {

    }

    @Override public int emptyLayoutId() {
        return 0;
    }

    @Override public int loadingLayoutId() {
        return 0;
    }

    @Override public int errorLayoutId() {
        return 0;
    }

    @Override public boolean isTransparentStatusBar() {
        return false;
    }

    @Override public boolean isBlackIconStatusBar() {
        return false;
    }

    @Override public String initTag() {
        return null;
    }

    @Override public int layoutId() {
        return 0;
    }

    @Override public void initData(Bundle savedInstanceState) {

    }

    @Override public boolean isDelayDate() {
        return false;
    }

    @Override public void initDataWhenDelay() {

    }

    @Override public Object getPresenter() {
        return null;
    }

    @Override public Context getContext() {
        return null;
    }

    @Override public boolean isOpenEventBus() {
        return false;
    }

    @Override public boolean isOpenViewState() {
        return false;
    }

    @Override public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override public void activtyFinish() {

    }

    @Override public void activtyFinish(boolean finishAfterTransition) {

    }

    @Override public void loading() {

    }

    @Override public void loading(String message) {

    }

    @Override public void loading(boolean cancelAble) {

    }

    @Override public void loading(String message, boolean cancelAble) {

    }

    @Override public void loadingClose() {

    }

    @Override public void showLoadingView() {

    }

    @Override public void showEmptyView() {

    }

    @Override public void showErrorView() {

    }

    @Override public void showContentView() {

    }

    @Override public int currentViewState() {
        return 0;
    }

    @Override public void intent2Activity(Class clazz) {

    }

    @Override public void intent2Activity(Class clazz, int requestCode) {

    }

    @Override public void intent2Activity(Class clazz, Bundle bundle) {

    }

    @Override public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {

    }

    @Override public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {

    }

    @Override public void commitFragment(Fragment fragment) {

    }

    @Override public void commitFragment(Fragment fragment, String trg) {

    }

    @Override public void commitFragment(int layoutId, Fragment fragment) {

    }

    @Override public void commitFragment(int layoutId, Fragment fragment, String trg) {

    }

    @Override public void commitFragment(Fragment oldFragment, Fragment fragment) {

    }

    @Override public void commitFragment(Fragment oldFragment, Fragment fragment, String trg) {

    }

    @Override public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment) {

    }

    @Override public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment, String trg) {

    }

    @Override public void commitBackStackFragment(Fragment fragment) {

    }

    @Override public void commitBackStackFragment(Fragment fragment, String trg) {

    }

    @Override public void commitBackStackFragment(int layoutId, Fragment fragment) {

    }

    @Override public void commitBackStackFragment(int layoutId, Fragment fragment, String trg) {

    }
}
