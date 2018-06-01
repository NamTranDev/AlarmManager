package tran.nam.alarmtimer.application.view.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.application.view.adapter.AlarmAdapter;
import tran.nam.alarmtimer.application.view.dialog.DeleteAlarmDialog;
import tran.nam.alarmtimer.application.viewmodel.AlarmViewModel;
import tran.nam.alarmtimer.biding.FragmentDataBindingComponent;
import tran.nam.alarmtimer.databinding.FragmentAlarmBinding;
import tran.nam.common.AutoClearedValue;
import tran.nam.core.view.mvvm.BaseFragmentMVVM;

public class AlarmFragment extends BaseFragmentMVVM<FragmentAlarmBinding, AlarmViewModel> implements AlarmAdapter.OnItemClick, DeleteAlarmDialog.OnDeleteAlarmCallback, AlarmViewModel.onLoadingDialog {

    @Inject
    NavigatorApp mNavigatorApp;

    @Inject
    PreferenceModel mPref;

    private DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private AutoClearedValue<AlarmAdapter> adapter;

    public static AlarmFragment getInstance() {
        return new AlarmFragment();
    }

    @Override
    public void initViewModel(ViewModelProvider.Factory factory) {
        mViewModel = ViewModelProviders.of(this, factory).get(AlarmViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_alarm;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mViewModel.setOnLoadingDialog(this);
        AlarmAdapter adapter = new AlarmAdapter(dataBindingComponent,mPref);
        adapter.setOnItemClick(this);
        this.adapter = new AutoClearedValue<>(this, adapter);
        binding.get().rvAlarm.setAdapter(adapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initAlarmList(mViewModel);
    }

    private void initAlarmList(AlarmViewModel mViewModel) {
        if (mViewModel == null)
            return;
        mViewModel.getAlarms().removeObservers(this);
        mViewModel.getAlarms().observe(this, listResource -> {
            mViewDataBinding.setResource(listResource);
            mViewDataBinding.executePendingBindings();
            if (listResource != null && listResource.data != null) {
                adapter.get().replace(listResource.data);
            }
        });
    }

    @Override
    public void onItemClick(AlarmModel alarmModel) {
        mNavigatorApp.goToAddAlarm(getBaseActivity(),alarmModel);
    }

    @Override
    public void onItemSwitchClick(AlarmModel alarmModel) {
        mViewModel.updateAlarm(alarmModel);
    }

    @Override
    public void onItemLongClick(AlarmModel alarmModel) {
        DeleteAlarmDialog dialog = DeleteAlarmDialog.getInstance(alarmModel);
        dialog.setOnDeleteAlarmCallback(this);
        dialog.show(getBaseActivity().getSupportFragmentManager(),"Delete Alarm Dialog");
    }

    @Override
    public void onDeleteItem(AlarmModel alarmModel) {
        mViewModel.deleteAlarm(alarmModel);
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
}
