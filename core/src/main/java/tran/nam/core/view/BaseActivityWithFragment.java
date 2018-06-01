package tran.nam.core.view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivityWithFragment extends BaseActivity implements HasSupportFragmentInjector, IFragmentProvider {

    @Inject
    public FragmentHelper<BaseFragment> mFragmentHelper;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    protected void addFragment(BaseFragment fragment) {
        mFragmentHelper.pushFragment(fragment);
    }

    protected void showFragment(int position) {
        mFragmentHelper.showFragment(position);
    }

    protected void replaceFragment(BaseFragment fragment) {
        mFragmentHelper.replaceFragment(fragment);
    }

    @Override
    protected void initFragment() {
        mFragmentHelper.setupFragment();
    }

    @Override
    public FragmentManager fragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public final AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    protected void popToRoot() {
        mFragmentHelper.popFragmentToRoot();
    }
}
