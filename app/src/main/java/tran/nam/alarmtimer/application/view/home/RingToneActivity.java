package tran.nam.alarmtimer.application.view.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ringtone.util.RingtoneLoaderTask;
import ringtone.util.RingtoneTypes;
import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.model.ListRingToneModel;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.alarmtimer.application.view.adapter.RingToneAdapter;
import tran.nam.alarmtimer.application.viewmodel.RingtoneViewModel;
import tran.nam.alarmtimer.callback.ToolbarItemClick;
import tran.nam.alarmtimer.databinding.ActivityRingtonePickerBinding;
import tran.nam.alarmtimer.type.RingToneType;
import tran.nam.core.view.mvvm.BaseActivityMVVM;
import tran.nam.util.Constant;
import tran.nam.util.StatusBarUtil;

import static ringtone.util.RingtoneTypes.TYPE_MUSIC;

public class RingToneActivity extends BaseActivityMVVM<ActivityRingtonePickerBinding, RingtoneViewModel> implements ToolbarItemClick.OnIvOptionalStartClick, RingtoneLoaderTask.LoadCompleteListener, RingToneAdapter.OnItemRingToneClick, ToolbarItemClick.OnTvOptionalEndClick {

    @Inject
    NavigatorApp mNavigatorApp;

    @Inject
    RingToneAdapter mAdapter;

    private @RingtoneTypes
    int mRingtoneTypes = TYPE_MUSIC;

    @RingToneType int typeRingTone;

    private boolean isSetting;
    private List<RingToneModel> listRingTone = new ArrayList<>();

    @Override
    public int getLayoutId() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RingtoneViewModel.class);
        return R.layout.activity_ringtone_picker;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.toolbar_color));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.setToolbarModel(mViewModel.toolbarModel);
        mViewDataBinding.setIvOptionalStartClick(this);
        mViewDataBinding.setTvOptionalEndClick(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            isSetting = getIntent().getExtras().getBoolean(Constant.KEY_INTENT.FROM_SETTING);
            typeRingTone = getIntent().getExtras().getInt(Constant.KEY_INTENT.RING_TONE_TYPE);
            if (isSetting)
                mViewModel.fromSetting();
        }

        mAdapter.setOnItemRingToneClick(this);
        mViewDataBinding.rvRingTone.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mViewDataBinding.rvRingTone.setAdapter(mAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getExtras() != null) {
            ListRingToneModel listRingToneModel = getIntent().getExtras().getParcelable(Constant.KEY_INTENT.RING_TONE);
            if (listRingToneModel != null) {
                listRingTone = listRingToneModel.ringToneModels;
            }
        }

        prepareRingtoneList(mRingtoneTypes);
    }

    @Override
    public void onIvOptionalStartClick(int type) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        mNavigatorApp.finish(this);
    }

    @SuppressLint("MissingPermission")
    private void prepareRingtoneList(final int types) {
        mViewDataBinding.viewFlipper.setDisplayedChild(0);
        RingtoneLoaderTask mLoaderTask = new RingtoneLoaderTask(this, this);
        //noinspection unchecked
        mLoaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, types);
    }

    @Override
    public void onLoadComplete(@NonNull HashMap<String, Uri> ringtone) {
        mViewDataBinding.viewFlipper.setDisplayedChild(1);
        List<RingToneModel> ringToneModels = new ArrayList<>();
        RingToneModel defaultRingTone = new RingToneModel(getString(tran.nam.flatform.R.string.title_default_list_item), "R.raw.bell");
        defaultRingTone.isChoose = listRingTone.contains(defaultRingTone);
        ringToneModels.add(defaultRingTone);
        for (Map.Entry<String, Uri> entry : ringtone.entrySet()) {
            String name = entry.getKey();
            String uri = entry.getValue().toString();

            RingToneModel ringToneModel = new RingToneModel(name, uri);
            ringToneModel.isChoose = listRingTone.contains(ringToneModel);
            ringToneModels.add(ringToneModel);
        }

        mAdapter.replace(ringToneModels);
    }

    @Override
    public void onItemRingToneClick(RingToneModel item, int position) {
        item.isChoose = !item.isChoose;
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onTvOptionalEndClick(int type) {
        ListRingToneModel listRingToneModel = new ListRingToneModel();
        listRingToneModel.addData(mAdapter.getListChoose());
        if (listRingToneModel.ringToneModels.size() == 0){
            Toast.makeText(this,"Please Choose Ring",Toast.LENGTH_SHORT).show();
            return;
        }
        if (isSetting) {
            mViewModel.updateSetting(listRingToneModel,typeRingTone);
            setResult(RESULT_OK);
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constant.KEY_INTENT_RESULT.RING_TONE, listRingToneModel);
            setResult(RESULT_OK, returnIntent);
        }
        mNavigatorApp.finish(this);
    }
}
