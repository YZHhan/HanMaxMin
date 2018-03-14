package com.han.hanmaxmin.common.widget.ptr;

/**
 * Created by ptxy on 2018/3/12.
 * Run a hook runnable, the runnable will run only once.(运行一个钩子runnable, runnable只运行一次。)
 * After the runnable is done, call resume to resume.(在完成runnable之后，调用resume恢复。)
 * Once run, call takeover will directory call the resume action.(一旦运行，呼叫接管将目录呼叫恢复行动。)
 */

public abstract class PtrUIHandlerHook implements Runnable {
    private Runnable mResumeAction;
    private static final byte STATUS_PREPARE = 0;
    private static final byte STATUS_IN_HOOK = 1;
    private static final byte STATUS_RESUMED = 2;
    private byte mStatus = STATUS_PREPARE;

    public void takeOver() {
        takeOver(null);
    }

    public void takeOver(Runnable resumeAction){
        if (resumeAction != null) {
            mResumeAction = resumeAction;
        }
        switch (mStatus) {
            case STATUS_PREPARE:
                mStatus = STATUS_IN_HOOK;
                run();
                break;
            case STATUS_IN_HOOK:
                break;
            case STATUS_RESUMED:
                resume();
                break;
        }
    }

    public void reset() {
        mStatus = STATUS_PREPARE;
    }

    public void resume() {
        if (mResumeAction != null) {
            mResumeAction.run();
        }
        mStatus = STATUS_RESUMED;
    }

    /**
     * Hook should always have a resume action, which is hooked by this hook.
     *
     * @param runnable
     */
    public void setResumeAction(Runnable runnable) {
        mResumeAction = runnable;
    }
}
