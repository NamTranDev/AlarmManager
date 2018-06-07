package tran.nam.alarmtimer.application;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.ListRingToneModel;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.alarmtimer.application.view.alarm.AddAlarmActivity;
import tran.nam.alarmtimer.application.view.alarm.AlarmActivity;
import tran.nam.alarmtimer.application.view.main.MainActivity;
import tran.nam.alarmtimer.application.view.home.RingToneActivity;
import tran.nam.alarmtimer.application.view.home.SettingHomeActivity;
import tran.nam.alarmtimer.application.view.home.SupportHomeActivity;
import tran.nam.alarmtimer.type.RingToneType;
import tran.nam.core.view.BaseActivity;
import tran.nam.util.Constant;

import static tran.nam.alarmtimer.type.RingToneType.TONE;

/**
 * Provides methods to navigate to the different activities in the application.
 */
@Singleton
public class NavigatorApp {

    @Inject
    NavigatorApp() {
    }

    public void goToMain(BaseActivity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        finish(activity);
    }

    public void goToSettingHome(BaseActivity activity) {
        Intent intent = new Intent(activity, SettingHomeActivity.class);
        activity.startActivityForResult(intent,Constant.REQUEST_CODE.SETTING_HOME);
        animationTransition(activity);
    }

    public void goToSupportHome(BaseActivity activity) {
        Intent intent = new Intent(activity, SupportHomeActivity.class);
        activity.startActivity(intent);
        animationTransition(activity);
    }

    public void goToAddAlarm(BaseActivity activity,AlarmModel alarmModel) {
        Intent intent = new Intent(activity, AddAlarmActivity.class);
        intent.putExtra(Constant.KEY_INTENT.ALARM_DATA,new Gson().toJson(alarmModel));
        activity.startActivity(intent);
        animationTransition(activity);
    }

    public void goToRingTonePick(BaseActivity activity, boolean isSetting,@RingToneType int type, ListRingToneModel ringToneModel) {
        Intent intent = new Intent(activity, RingToneActivity.class);
        intent.putExtra(Constant.KEY_INTENT.FROM_SETTING,isSetting);
        intent.putExtra(Constant.KEY_INTENT.RING_TONE,ringToneModel);
        intent.putExtra(Constant.KEY_INTENT.RING_TONE_TYPE,type);
        activity.startActivityForResult(intent,type == TONE ? Constant.REQUEST_CODE.PICK_RING_TONE : Constant.REQUEST_CODE.PICK_RING_MUSIC);

        animationTransition(activity);
    }

    public void openPhone(BaseActivity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        activity.startActivity(intent);
    }

    public void viewWeb(BaseActivity activity, String web) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(web));
        activity.startActivity(i);
    }

    public void goToAlarm(BaseActivity activity, boolean isPreview, AlarmModel alarmModel) {
        Intent intent = new Intent(activity, AlarmActivity.class);
        intent.putExtra(Constant.KEY_INTENT.ALARM_DATA,new Gson().toJson(alarmModel));
        intent.putExtra(Constant.KEY_INTENT.IS_PREVIEW_ALARM,isPreview);
        activity.startActivity(intent);
        animationTransition(activity);
    }

    //base
    public void finish(BaseActivity activity) {
        activity.finish();
        activity.overridePendingTransition(tran.nam.core.R.anim.slide_in_left, tran.nam.core.R.anim.slide_out_right);
    }

    private void animationTransition(BaseActivity activity) {
        activity.overridePendingTransition(tran.nam.core.R.anim.slide_in_right, tran.nam.core.R.anim.slide_out_left);
    }

    public void exit(BaseActivity activity) {
        activity.finish();
    }
}
