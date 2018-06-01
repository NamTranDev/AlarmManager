package tran.nam.alarmtimer.application.model;

import android.content.Context;
import android.databinding.BaseObservable;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.type.ToolbarType;

import static tran.nam.alarmtimer.type.ToolbarType.ALARM;
import static tran.nam.alarmtimer.type.ToolbarType.HOME;

public class ToolbarModel extends BaseObservable {

    public boolean isIvOptionalStart;
    public boolean isIvOptionalEnd;
    public boolean isTextOptionStart;
    public boolean isTextOptionEnd;
    public boolean isTitle = true;
    public boolean isLine = true;
    public String textTitle;
    public String textOptionStart;
    public String textOptionEnd;
    public int srcOptionalStart;
    public int srcOptionalEnd;
    public @ToolbarType
    int type = HOME;

    public ToolbarModel(boolean isStart, String textTitle, int src) {
        this.isIvOptionalEnd = !isStart;
        this.isIvOptionalStart = isStart;
        this.textTitle = textTitle;
        this.srcOptionalStart = src;
        this.srcOptionalEnd = src;
    }

    public ToolbarModel(boolean isTextOptionStart, boolean isTextOptionEnd, boolean isTitle, boolean isLine, String textOptionStart, String textOptionEnd) {
        this.isTextOptionStart = isTextOptionStart;
        this.isTextOptionEnd = isTextOptionEnd;
        this.isTitle = isTitle;
        this.isLine = isLine;
        this.textOptionStart = textOptionStart;
        this.textOptionEnd = textOptionEnd;
    }

    public void updateWhenTabChangeMain(Context context,int position) {
        srcOptionalEnd = position == 0 ? R.drawable.ic_more : R.drawable.ic_add;
        type = (position == 0) ? HOME : ALARM;
        textTitle = context.getString (position == 0 ? R.string.title_main : R.string.title_alarm);
        notifyChange();
    }

    public void hideIvEnd() {
        isIvOptionalEnd = false;
        notifyChange();
    }
}
