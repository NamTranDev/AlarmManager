package tran.nam.alarmtimer.application.view.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.permission.PermissionHelper;
import tran.nam.core.view.BaseActivity;
import tran.nam.util.StatusBarUtil;

public class SplashActivity extends BaseActivity {

    @Inject
    NavigatorApp mNavigatorApp;

    private PermissionHelper mPermission;
    private Handler mHandler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mNavigatorApp.goToMain(SplashActivity.this);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.baclground_splash));
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mHandler = new Handler();
        mPermission = new PermissionHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    private void checkPermission(){
        if (mPermission != null){
            if (mPermission.checkPermission())
                goToMain();
            else
                mPermission.requestAllPermission();
        }
    }

    private void goToMain() {
        mHandler.postDelayed(runnable,1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermission != null && mPermission.onRequestPermissionsResult(requestCode,grantResults)){
            goToMain();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
