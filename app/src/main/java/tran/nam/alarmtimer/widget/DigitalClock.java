package tran.nam.alarmtimer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class DigitalClock extends View {

    private Typeface custom_font;
    private TextPaint textPaintDate = new TextPaint();
    private TextPaint textPaintTime = new TextPaint();
    private TextPaint textPaintInfo = new TextPaint();
    private Paint paint = new Paint();

    private int[] colors = {Color.YELLOW, Color.argb(255, 255, 165, 0), Color.RED, Color.MAGENTA, Color.CYAN, Color.BLUE, Color.GREEN, Color.WHITE, Color.DKGRAY};
    private int selected_color = 5; // start with blue

    //values
    private String year = "YEAR";
    private String month = "MONTH";
    private String day = "DAY";
    private String hour = "HOUR";
    private String minute = "MINUTE";
    private String second = "SECOND";

    private int centerY,centerX;
    private int[] date_time;
    private String day_of_week;


    public DigitalClock(Context context) {
        super(context);
        init(context);
    }

    public DigitalClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //load the digital font
        custom_font = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/digital.ttf");

        //anti aliasing
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        //draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        //get date & time
        date_time = getDateTime();
        day_of_week = getDayOfWeek(date_time[3]);

        //timer
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate(); //invalidate the graphics every 1 second
                //-------------------
                h.postDelayed(this, 1000);
            }
        }, 1000); // 1 second delay (takes millis)
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerY = getHeight() / 2;
        centerX = getWidth() / 2;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        canvas.drawPaint(paint);

        //show date
        textPaintDate.setTextSize(getWidth() / 10);
        textPaintDate.setTypeface(custom_font);
        textPaintDate.setColor(colors[selected_color]);

        int y = getTop();

        Rect bounds = new Rect();
        String upper = String.valueOf(date_time[0]) + "   " + String.valueOf(date_time[1] + 1) + "  " + String.valueOf(date_time[2]) + " " + day_of_week;
        textPaintDate.getTextBounds(upper, 0, upper.length(), bounds);
        int x_pos = (getWidth() - bounds.width()) / 2;
        canvas.drawText(upper, x_pos, getHeight()/4 + y, textPaintDate);

        //describe date
        textPaintInfo.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        textPaintInfo.setTextSize(getWidth() / 20);
        textPaintInfo.setColor(Color.GRAY);

        int yDescribeDate = y + bounds.height() + 10;

        canvas.drawText(year, x_pos, yDescribeDate, textPaintInfo);
        canvas.drawText(month, x_pos + measureString((String.valueOf(date_time[0]) + "o"), textPaintDate).width(), yDescribeDate, textPaintInfo);
        canvas.drawText(day, x_pos + measureString(String.valueOf(date_time[0]) + "   o" + String.valueOf(date_time[1] + 1)
                + String.valueOf(date_time[2]), textPaintDate).width(), yDescribeDate, textPaintInfo);

        //show time
//        String[] time_str = addLeadingZeros(date_time[4], date_time[5], date_time[6]);
//
//        textPaintTime.setTextSize(getWidth() / 6);
//        textPaintTime.setTypeface(custom_font);
//        textPaintTime.setColor(colors[selected_color]);
//
//        String middle = String.valueOf(time_str[0] + ":" + time_str[1] + ":" + time_str[2]);
//        bounds = new Rect();
//        textPaintTime.getTextBounds("00:00:00", 0, "00:00:00".length(), bounds);
//        x_pos = (getWidth() - bounds.width()) / 2;
//        canvas.drawText(middle, x_pos, center_y + (int) (bounds.height() / 1.2), textPaintTime);
//
//        //describe time
//        canvas.drawText(hour, x_pos, getHeight()/2 + getHeight()/4, textPaintInfo);
//        canvas.drawText(minute, x_pos + measureString(String.valueOf(time_str[0]) + "::", textPaintTime).width(), getHeight()/2 + getHeight()/4, textPaintInfo);
//        canvas.drawText(second, x_pos + measureString(String.valueOf(time_str[0] + ":" + time_str[1] + ":::"), textPaintTime).width(), getHeight()/2 + getHeight()/4, textPaintInfo);
    }


    private String[] addLeadingZeros(int hours, int minutes, int seconds) {
        String hours_str, minutes_str, seconds_str;

        if (hours <= 9)
            hours_str = "0" + hours;
        else
            hours_str = String.valueOf(hours);

        if (minutes <= 9)
            minutes_str = "0" + minutes;
        else
            minutes_str = String.valueOf(minutes);

        if (seconds <= 9)
            seconds_str = "0" + seconds;
        else
            seconds_str = String.valueOf(seconds);

        return new String[]{hours_str, minutes_str, seconds_str};
    }


    private Rect measureString(String text, TextPaint tp) {

        Rect bounds = new Rect();
        tp.getTextBounds(text, 0, text.length(), bounds);

        return bounds; // return width & height of string
    }

    private int[] getDateTime() {

        //get date & time
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        int ss = calendar.get(Calendar.SECOND);
        int mm = calendar.get(Calendar.MINUTE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);

        return new int[]{year, month, day, day_of_week, hh, mm, ss};

    }

    private String getDayOfWeek(int day_of_week) {
        switch (day_of_week) {
            case Calendar.MONDAY:
                return "MON";
            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THU";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
            case Calendar.SUNDAY:
                return "SUN";
        }

        return null;

    }

}