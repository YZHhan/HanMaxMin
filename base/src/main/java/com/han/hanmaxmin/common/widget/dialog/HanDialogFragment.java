package com.han.hanmaxmin.common.widget.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @CreateBy Administrator
 * @Date 2017/11/11  17:50
 * @Description
 */

public abstract class HanDialogFragment extends DialogFragment {
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,getDialogTheme());
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        final Window window = getDialog().getWindow();
        if(window!=null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getDecorView().setPadding(0,0,0,0);
            WindowManager.LayoutParams params = window.getAttributes();
            setAttributes(params);
            window.setAttributes(params);
        }
        return getDialogView(inflater,container);
    }



    protected abstract int getDialogTheme();

    protected abstract View getDialogView(LayoutInflater inflater,ViewGroup viewGroup);

    protected void setAttributes(WindowManager.LayoutParams params){
        params.gravity= Gravity.BOTTOM;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
    }

}
