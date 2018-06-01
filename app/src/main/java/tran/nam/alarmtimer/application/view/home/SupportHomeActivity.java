package tran.nam.alarmtimer.application.view.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.viewmodel.SupportHomeViewModel;
import tran.nam.alarmtimer.callback.SupportHomeItemClick;
import tran.nam.alarmtimer.databinding.ActivitySupportHomeBinding;
import tran.nam.core.view.mvvm.BaseActivityMVVM;
import tran.nam.util.StatusBarUtil;

public class SupportHomeActivity extends BaseActivityMVVM<ActivitySupportHomeBinding, SupportHomeViewModel> implements SupportHomeItemClick {

    @Inject
    NavigatorApp mNavigatorApp;

    @Override
    public int getLayoutId() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SupportHomeViewModel.class);
        return R.layout.activity_support_home;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.toolbar_color));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.setToolbarModel(mViewModel.toolbarModel);
        mViewDataBinding.setItemToolbarClick(type -> {
            onBackPressed();
        });

        mViewDataBinding.setSupportHomeClick(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        mNavigatorApp.finish(this);
    }

    @Override
    public void onPhoneClick(String phone) {
        mNavigatorApp.openPhone(this, phone);
    }

    @Override
    public void onWebClick(String web) {
        mNavigatorApp.viewWeb(this, web);
    }
}
