package tran.nam.alarmtimer.widget;

import android.view.View;

import java.util.List;

public interface OnWeekRecurrenceChangeListener {
    /**
     * @param view         View of Weekdayspicker
     * @param selectedDays Integer-list of days from {@link java.util.Calendar}
     * @param even_week    0 = every week; 1 = odd; 2 = even
     */
    void onWeekChange(View view, List<Integer> selectedDays, int even_week);
}
