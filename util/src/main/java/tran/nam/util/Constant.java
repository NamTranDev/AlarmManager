package tran.nam.util;

import android.util.SparseArray;

public interface Constant {

    String API_URL = "";
    String EMPTY = "";

    int HOURS_IN_HALF_DAY = 12;

    /*Roboto FONT*/
    int FONT_STYLE_REGULAR = 0;
    int FONT_STYLE_MEDIUM = 1;
    int FONT_STYLE_LIGHT = 2;
    int FONT_STYLE_THIN = 3;
    int FONT_STYLE_DIGITAL = 4;

    SparseArray<String> FONT_STYLES = new SparseArray<String>() {
        {
            append(FONT_STYLE_REGULAR, "Roboto-Regular.ttf");
            append(FONT_STYLE_MEDIUM, "Roboto-Medium.ttf");
            append(FONT_STYLE_LIGHT, "Roboto-Light.ttf");
            append(FONT_STYLE_THIN, "Roboto-Thin.ttf");
            append(FONT_STYLE_DIGITAL, "digital.ttf");
        }
    };
    // No breaking space letter
    String NON_BREAKING_SPACE = "\u00A0";

    interface REQUEST_CODE{
        int PICK_RING_TONE = 1;
        int SETTING_HOME = 2;
    }

    interface KEY_INTENT{
        String FROM_SETTING = "From Setting";
        String RING_TONE = "Ring tone";
        String ALARM_DATA = "Alarm Data";
        String IS_PREVIEW_ALARM = "Is Preview Alarm";
    }

    interface KEY_INTENT_RESULT{
        String RING_TONE = "Ring tone Result";
    }

    interface KEY_DIALOG{
        String DIALOG_NAME = "Dialog Name";
        String DIALOG_ALARM_DATA = "Dialog Alarm Data";
        String DIALOG_MINUTE = "Dialog Minute";
        String DIALOG_SECOND = "Dialog Second";
        String ALARM = "Alarm";
    }
}
