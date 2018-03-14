package com.han.hanmaxmin.common.widget.dialog;

import android.view.Gravity;
import android.view.WindowManager;

/**
 * @CreateBy Administrator
 * @Date 2017/11/12  12:40
 * @Description
 */

public abstract class HanProgressDialog extends HanDialogFragment {

    private CharSequence mMessage;

    public CharSequence getMessage() {
        return mMessage;
    }

    public void setMessage(CharSequence mMessage) {
        this.mMessage = mMessage;
    }

    @Override protected void setAttributes(WindowManager.LayoutParams params) {
        params.gravity= Gravity.CENTER;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
