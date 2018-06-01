package tran.nam.core.view;

import android.support.v4.app.FragmentManager;

public interface IFragmentProvider<T extends BaseFragment> {

    T[] getFragments();

    int getContentLayoutId();

    FragmentManager fragmentManager();

}
