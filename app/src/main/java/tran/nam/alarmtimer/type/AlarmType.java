package tran.nam.alarmtimer.type;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static tran.nam.alarmtimer.type.AlarmType.ALERT;
import static tran.nam.alarmtimer.type.AlarmType.BELL;
import static tran.nam.alarmtimer.type.AlarmType.EMPTY;
import static tran.nam.alarmtimer.type.AlarmType.EVAC;
import static tran.nam.alarmtimer.type.ToolbarType.ALARM;

@StringDef({ALERT, EVAC, BELL,EMPTY})
@Retention(RetentionPolicy.SOURCE)
public @interface AlarmType {
    String ALERT = "ALERT";
    String EVAC = "EVAC";
    String BELL = "BELL";
    String EMPTY = "";
}
