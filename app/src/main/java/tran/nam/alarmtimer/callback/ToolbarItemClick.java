package tran.nam.alarmtimer.callback;

import tran.nam.alarmtimer.type.ToolbarType;

public interface ToolbarItemClick {
    interface OnTvOptionalStartClick {
        void onTvOptionalStartClick(@ToolbarType int type);
    }

    interface OnTvOptionalEndClick {
        void onTvOptionalEndClick(@ToolbarType int type);
    }

    interface OnIvOptionalStartClick {
        void onIvOptionalStartClick(@ToolbarType int type);
    }

    interface OnIvOptionalEndClick {
        void onIvOptionalEndClick(@ToolbarType int type);
    }
}
