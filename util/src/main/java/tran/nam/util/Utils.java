package tran.nam.util;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {

    public static float scalePixel(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / 320;
    }

    public static float scaleDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density / 320;
    }

    public static int dip2px(Context context, float dpVale) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
