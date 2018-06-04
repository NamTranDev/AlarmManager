package tran.nam.alarmtimer.biding;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import tran.nam.alarmtimer.application.model.ListRingToneModel;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.alarmtimer.widget.TimePicker;
import tran.nam.core.view.BaseActivityWithFragment;

public class BidingData {
    @BindingAdapter("visible")
    public static void visible(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("srcVector")
    public static void image(AppCompatImageView view, @DrawableRes int src) {
        view.setImageResource(src);
    }

    @BindingAdapter("onTabChangeListener")
    public static void onTabChangeListener(TabLayout tabLayout, ToolbarModel toolbarModel) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbarModel.updateWhenTabChangeMain(tabLayout.getContext(), tab.getPosition());
                if (tabLayout.getContext() instanceof BaseActivityWithFragment) {
                    ((BaseActivityWithFragment) tabLayout.getContext()).mFragmentHelper.showFragment(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @BindingAdapter("setTextUnderLine")
    public static void onSetTextUnderline(TextView textView, String text) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView.setText(text);
    }

    @BindingAdapter("nameRingtone")
    public static void setTextRingtone(TextView view, ListRingToneModel defaultRingtone) {
        if (defaultRingtone == null || defaultRingtone.ringToneModels == null)
            return;
        StringBuilder ringtone = new StringBuilder();
        for (RingToneModel ringToneModel : defaultRingtone.ringToneModels){
            ringtone.append(ringToneModel.name).append(", ");
        }
        view.setText(ringtone.toString().substring(0, ringtone.toString().length() - 2));
    }

    @BindingAdapter("select24h")
    public static void select24h(TimePicker view, boolean is24h) {
        view.setIs24HourView(is24h);
    }
}
