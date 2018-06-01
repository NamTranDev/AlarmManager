package tran.nam.alarmtimer.di.module.fragment;


import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import tran.nam.alarmtimer.application.view.main.HomeFragment;
import tran.nam.core.di.inject.PerFragment;

/**
 * Provides home fragment dependencies.
 */
@Module
public abstract class HomeFragmentModule {

    @Binds
    @PerFragment
    abstract Fragment fragment(HomeFragment fragment);
}
