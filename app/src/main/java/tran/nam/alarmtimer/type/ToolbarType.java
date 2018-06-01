package tran.nam.alarmtimer.type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static tran.nam.alarmtimer.type.ToolbarType.ALARM;
import static tran.nam.alarmtimer.type.ToolbarType.HOME;

@IntDef({HOME, ALARM})
@Retention(RetentionPolicy.SOURCE)
public @interface ToolbarType {
    int HOME = 1;
    int ALARM = 2;
}
