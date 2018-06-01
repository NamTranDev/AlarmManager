package tran.nam.core.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Binds;
import dagger.Module;
import tran.nam.core.di.inject.PerActivity;

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of {@link AppCompatActivity}.
 */
@Module
public abstract class BaseActivityModule {

    static final String ACTIVITY_FRAGMENT_MANAGER = "BaseActivityModule.activityFragmentManager";

    @Binds
    @PerActivity
    /*
     * PerActivity annotation isn't necessary since Activity instance is unique but is here for
     * convention. In general, providing Application, Activity, Fragment, BroadcastReceiver,
     * etc does not require scoped annotations since they are the components being injected and
     * their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    abstract Context activityContext(AppCompatActivity activity);
}