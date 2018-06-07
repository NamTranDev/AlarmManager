package tran.nam.alarmtimer.application.view.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;

import tran.nam.alarmtimer.application.view.dialog.AlarmDialog;
import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.viewmodel.HomeViewModel;
import tran.nam.alarmtimer.callback.MainHomeItemClick;
import tran.nam.alarmtimer.databinding.FragmentHomeBinding;
import tran.nam.core.view.mvvm.BaseFragmentMVVM;

import static tran.nam.alarmtimer.type.AlarmType.ALERT;
import static tran.nam.alarmtimer.type.AlarmType.BELL;
import static tran.nam.alarmtimer.type.AlarmType.EVAC;

public class HomeFragment extends BaseFragmentMVVM<FragmentHomeBinding, HomeViewModel> implements MainHomeItemClick, HomeViewModel.onLoadingDialog, HomeViewModel.onUpdateData {

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void initViewModel(ViewModelProvider.Factory factory) {
        mViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getRightInAnimId() {
        return R.anim.anim_no_duration;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mViewModel.setOnLoadingDialog(this);
        mViewModel.setOnUpdateData(this);
        binding.get().setItemClick(this);
        binding.get().setViewModel(mViewModel);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.updateTime();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.cancelTime();
    }

    @Override
    public void onProgramButtonClick() {
        if (getBaseActivity() instanceof MainActivity)
            ((MainActivity) getBaseActivity()).tabAlarm();
    }

    @Override
    public void onAlertButtonClick() {
//        Toast.makeText(getBaseActivity(), "onAlertButtonClick", Toast.LENGTH_SHORT).show();
        AlarmDialog.getInstance(ALERT).show(getBaseActivity().getSupportFragmentManager(),"Dialog Alert");
    }

    @Override
    public void onAvacButtonClick() {
        AlarmDialog.getInstance(EVAC).show(getBaseActivity().getSupportFragmentManager(),"Dialog Alert");
    }

    @Override
    public void onBellButtonClick() {
        AlarmDialog.getInstance(BELL).show(getBaseActivity().getSupportFragmentManager(),"Dialog Alert");
    }

    @Override
    public void onShowLoadingDialog() {
        showLoadingDialog();
    }

    @Override
    public void onHideLoadingDialog() {
        hideLoadingDialog();
    }

    @Override
    public void onShowError(String error) {
        Toast.makeText(getBaseActivity(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateData() {
        if (getBaseActivity() instanceof MainActivity){
            ((MainActivity)getBaseActivity()).updateAlarm();
        }
    }

    public void updateWetMode() {
        mViewModel.mPreferenceModel.updateWetMode();
    }
}
