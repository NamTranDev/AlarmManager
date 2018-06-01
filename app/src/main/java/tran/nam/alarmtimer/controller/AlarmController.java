package tran.nam.alarmtimer.controller;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Inject;

import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.view.alarm.AlarmActivity;
import tran.nam.util.Constant;
import tran.nam.util.Logger;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.app.PendingIntent.getActivity;

public class AlarmController {

    private final Context mAppContext;

    @Inject
    AlarmController(Application app) {
        this.mAppContext = app.getApplicationContext();
    }

    /*
      Schedules the alarm with the {@link AlarmManager}.
      If {@code alarm.}{@link Alarm#isEnabled() isEnabled()}
      returns false, this does nothing and returns immediately.

      If there is already an alarm for this Intent scheduled (with the equality of two
      intents being defined by filterEquals(Intent)), then it will be removed and replaced
      by this one. For most of our uses, the relevant criteria for equality will be the
      action, the data, and the class (component). Although not documented, the request code
      of a PendingIntent is also considered to determine equality of two intents.
     */
    public void scheduleAlarm(AlarmModel alarm) {
        if (alarm == null || !alarm.isEnable) {
            return;
        }

        AlarmManager am = (AlarmManager) mAppContext.getSystemService(Context.ALARM_SERVICE);
        final long ringAt = alarm.ringsAt();
        final PendingIntent alarmIntent = alarmIntent(alarm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PendingIntent showIntent = create(mAppContext, (int) alarm.id);
            AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(ringAt, showIntent);
            if (am != null)
                am.setAlarmClock(info, alarmIntent);
        } else {
            // WAKEUP alarm types wake the CPU up, but NOT the screen;
            // you would handle that yourself by using a wakelock, etc..
            if (am != null)
                am.setExact(AlarmManager.RTC_WAKEUP, ringAt, alarmIntent);
            // Show alarm in the status bar
            Intent alarmChanged = new Intent("android.intent.action.ALARM_CHANGED");
            alarmChanged.putExtra("alarmSet", true);
            mAppContext.sendBroadcast(alarmChanged);
        }
    }

    private PendingIntent alarmIntent(AlarmModel alarm) {
        Intent intent = new Intent(mAppContext, AlarmActivity.class)
                .putExtra(Constant.KEY_INTENT.IS_PREVIEW_ALARM, false).putExtra(Constant.KEY_INTENT.ALARM_DATA, new Gson().toJson(alarm));
        // Even when we try to retrieve a previous instance that actually did exist,
        // null can be returned for some reason. Thus, we don't checkNotNull().
        return getActivity(mAppContext, (int) alarm.id, intent, FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent create(@NonNull Context context, int stableId) {
        Intent intent = new Intent();
        return PendingIntent.getActivity(context, stableId, intent, FLAG_UPDATE_CURRENT);
    }

    /**
     * Cancel the alarm. This does NOT check if you previously scheduled the alarm.
     */
    public void cancelAlarm(AlarmModel alarm) {
        Logger.debug("Cancelling alarm " + alarm);
        AlarmManager am = (AlarmManager) mAppContext.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pi = alarmIntent(alarm);
        assert am != null;
        am.cancel(pi);
        pi.cancel();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Remove alarm in the status bar
            Intent alarmChanged = new Intent("android.intent.action.ALARM_CHANGED");
            alarmChanged.putExtra("alarmSet", false);
            mAppContext.sendBroadcast(alarmChanged);
        }
    }
}
