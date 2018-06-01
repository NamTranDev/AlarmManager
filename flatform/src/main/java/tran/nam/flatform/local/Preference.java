package tran.nam.flatform.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.flatform.R;
import tran.nam.flatform.model.RingToneData;

import static tran.nam.util.Constant.EMPTY;

@Singleton
public class Preference implements IPreference {

    private static final String ALARM_24H = "Alarm 24h";
    private static final String ALARM_WET_MODE = "Alarm Wet Mode";
    private static final String ALARM_DEFAULT_RINGTONE = "Alarm Default Ringtone";

    /**
     * normal configurations
     */
    private static final String SHARED_REFERENCE_NAME = "Alarm Timer Config";

    private SharedPreferences mPref;
    private Application mApp;

    @Inject
    Preference(Application application) {
        this.mApp = application;
        mPref = application.getSharedPreferences(SHARED_REFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isAlarm24h() {
        return mPref.getBoolean(ALARM_24H, false);
    }

    @Override
    public void setAlarm24h(boolean is24h) {
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(ALARM_24H, is24h);
        editor.apply();
    }

    @Override
    public boolean isWetMode() {
        return mPref.getBoolean(ALARM_WET_MODE, false);
    }

    @Override
    public void setWetMode(boolean isWetMode) {
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(ALARM_WET_MODE, isWetMode);
        editor.apply();
    }

    @Override
    public RingToneData getDefaultRingtone() {
        String ringtoneString = mPref.getString(ALARM_DEFAULT_RINGTONE, EMPTY);
        if (!TextUtils.isDigitsOnly(ringtoneString)) {
            return new Gson().fromJson(ringtoneString, RingToneData.class);
        }
        return new RingToneData(mApp.getString(R.string.title_default_list_item), "R.raw.bell");
    }

    @Override
    public void setDefaultRingTone(RingToneData defaultRingTone) {
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putString(ALARM_DEFAULT_RINGTONE, new Gson().toJson(defaultRingTone));
        editor.apply();
    }
}
