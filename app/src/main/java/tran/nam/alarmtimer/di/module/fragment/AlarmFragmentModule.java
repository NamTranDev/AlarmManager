package tran.nam.alarmtimer.di.module.fragment;


import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import tran.nam.alarmtimer.application.view.main.AlarmFragment;
import tran.nam.core.di.inject.PerFragment;

/**
 * Provides alarm fragment dependencies.
 */
@Module
public abstract class AlarmFragmentModule {

    @Binds
    @PerFragment
    abstract Fragment fragment(AlarmFragment fragment);
}
