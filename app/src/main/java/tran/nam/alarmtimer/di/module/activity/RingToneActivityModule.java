package tran.nam.alarmtimer.di.module.activity;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import tran.nam.alarmtimer.application.view.home.RingToneActivity;
import tran.nam.core.di.inject.PerActivity;
import tran.nam.core.view.BaseActivity;
import tran.nam.core.view.BaseActivityModule;

/**
 * Provides main activity dependencies.
 */
@Module(includes = {
        BaseActivityModule.class
})
public abstract class RingToneActivityModule {

    /**
     * As per the contract specified in {@link BaseActivityModule}; "This must be included in all
     * activity modules, which must provide a concrete implementation of {@link AppCompatActivity}."
     * <p>
     * This provides the activity required to inject the
     * {@link BaseActivityModule#ACTIVITY_FRAGMENT_MANAGER} into the
     * {@link BaseActivity}.
     *
     * @param activity the example 1 activity
     * @return the activity
     */
    @Binds
    @PerActivity
    abstract AppCompatActivity activity(RingToneActivity activity);
}
