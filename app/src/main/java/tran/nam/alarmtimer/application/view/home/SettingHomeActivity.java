package tran.nam.alarmtimer.application.view.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.viewmodel.SettingHomeViewModel;
import tran.nam.alarmtimer.callback.SettingHomeItemClick;
import tran.nam.alarmtimer.databinding.ActivitySettingHomeBinding;
import tran.nam.alarmtimer.type.RingToneType;
import tran.nam.core.view.mvvm.BaseActivityMVVM;
import tran.nam.util.Constant;
import tran.nam.util.StatusBarUtil;

import static tran.nam.alarmtimer.type.RingToneType.MUSIC;
import static tran.nam.alarmtimer.type.RingToneType.TONE;

public class SettingHomeActivity extends BaseActivityMVVM<ActivitySettingHomeBinding, SettingHomeViewModel> implements SettingHomeItemClick, SettingHomeViewModel.onLoadingDialog {

    @Inject
    NavigatorApp mNavigatorApp;

    @Override
    public int getLayoutId() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingHomeViewModel.class);
        return R.layout.activity_setting_home;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.toolbar_color));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.setViewModel(mViewModel);
        mViewDataBinding.setItemToolbarClick(type -> onBackPressed());
        mViewDataBinding.setItemClick(this);
        mViewModel.setOnLoadingDialog(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        if (mViewModel.isChange){
            setResult(RESULT_OK);
        }
        mNavigatorApp.finish(this);
    }

    @Override
    public void onSongItemClick() {
        mNavigatorApp.goToRingTonePick(this,true, TONE,mViewModel.mPreferenceModel.defaultRingtone);
    }

    @Override
    public void onSongItemMusicClick() {
        mNavigatorApp.goToRingTonePick(this,true, MUSIC,mViewModel.mPreferenceModel.defaultRingtoneMusic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Constant.REQUEST_CODE.PICK_RING_TONE:
                if (resultCode == RESULT_OK){
                    mViewModel.mPreferenceModel.notifyChange();
                }
                break;
            case Constant.REQUEST_CODE.PICK_RING_MUSIC:
                if (resultCode == RESULT_OK){
                    mViewModel.mPreferenceModel.notifyChange();
                }
                break;
        }
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
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
}
