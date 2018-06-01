package tran.nam.core.view;

import java.lang.ref.WeakReference;

public class WaitThread extends Thread {

    private final Object mObject = new Object();
    private WeakReference<BaseFragment> fragmentWeak;
    private boolean isStopped;

    WaitThread(BaseFragment fragment) {
        fragmentWeak = new WeakReference<>(fragment);
    }

    @Override
    public void run() {
        BaseFragment fragment = fragmentWeak.get();
        if (fragment != null) {
            while (!fragment.isViewCreated() && !isStopped) {
                try {
                    synchronized (mObject) {
                        mObject.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (isStopped)
                return;

            final BaseFragment finalFragment = fragmentWeak.get();
            if (finalFragment != null) {
                finalFragment.getBaseActivity().runOnUiThread(() -> {
                    if (finalFragment.isInitialized())
                        finalFragment.initialized();
                    else
                        finalFragment.handleAfterVisible();
                });
            }
        }
    }

    void continueProcessing() {
        synchronized (mObject) {
            mObject.notifyAll();
        }
    }

    void stopProcessing() {
        isStopped = true;
        synchronized (mObject) {
            mObject.notifyAll();
        }
    }
}
