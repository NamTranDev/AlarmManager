package tran.nam.alarmtimer.di.module.activity;

import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tran.nam.alarmtimer.application.view.main.AlarmFragment;
import tran.nam.alarmtimer.application.view.main.HomeFragment;
import tran.nam.alarmtimer.application.view.main.MainActivity;
import tran.nam.alarmtimer.di.module.fragment.AlarmFragmentModule;
import tran.nam.alarmtimer.di.module.fragment.HomeFragmentModule;
import tran.nam.core.di.inject.PerActivity;
import tran.nam.core.di.inject.PerFragment;
import tran.nam.core.view.BaseActivity;
import tran.nam.core.view.BaseActivityModule;
import tran.nam.core.view.BaseFragment;
import tran.nam.core.view.IFragmentProvider;

/**
 * Provides main activity dependencies.
 */
@Module(includes = {
        BaseActivityModule.class
})
public abstract class MainActivityModule {

    /**
     * Provides the injector for the {@link HomeFragmentModule}, which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment homeFragmentInjector();

    /**
     * Provides the injector for the {@link AlarmFragmentModule}, which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = AlarmFragmentModule.class)
    abstract AlarmFragment alarmFragmentInjector();

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
    abstract AppCompatActivity activity(MainActivity activity);

    @Binds
    @PerActivity
    abstract IFragmentProvider<BaseFragment> providerFragmentHelper(MainActivity activity);
}
