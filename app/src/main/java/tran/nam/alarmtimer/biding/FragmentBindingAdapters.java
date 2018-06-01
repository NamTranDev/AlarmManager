/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tran.nam.alarmtimer.biding;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import tran.nam.util.Logger;

import static tran.nam.alarmtimer.type.DateType.FRIDAY;
import static tran.nam.alarmtimer.type.DateType.MONDAY;
import static tran.nam.alarmtimer.type.DateType.SATURDAY;
import static tran.nam.alarmtimer.type.DateType.SUNDAY;
import static tran.nam.alarmtimer.type.DateType.THURSDAY;
import static tran.nam.alarmtimer.type.DateType.TUESDAY;
import static tran.nam.alarmtimer.type.DateType.WEDNESDAY;

/**
 * Binding adapters that work with a fragment instance.
 */
public class FragmentBindingAdapters {
    final Fragment fragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }

    @BindingAdapter("textTime")
    public static void bindTextTime(TextView textView, long date){
        String time = "";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
            time = sdf.format(new Date(date));
        }catch (Exception e){
            Logger.debug(e);
        }
        textView.setText(time);
    }
}
