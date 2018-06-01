package tran.nam.core.view;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import tran.nam.core.di.inject.PerActivity;

@PerActivity
public class FragmentHelper<T extends BaseFragment> {

    private List<Stack<T>> mPageList;
    private int mPageIndex;
    private int mLayoutId;
    private FragmentManager mFragmentManager;
    private IFragmentProvider<T> fragmentProvider;

    private T[] mBuildFragments;

    private OnChangedFragmentListener mOnChangedFragmentListener;

    @Inject
    FragmentHelper(IFragmentProvider<T> fragmentProvider) {
        this.fragmentProvider = fragmentProvider;
    }

    public void setupFragment() {
        this.mLayoutId = fragmentProvider.getContentLayoutId();
        this.mBuildFragments = fragmentProvider.getFragments();
        this.mFragmentManager = fragmentProvider.fragmentManager();

        initFragments(mBuildFragments);
    }

    public void setOnChangedFragmentListener(OnChangedFragmentListener mOnChangedFragmentListener) {
        this.mOnChangedFragmentListener = mOnChangedFragmentListener;
    }

    private void initFragments(T[] fragments) {
        this.mPageList = new ArrayList<>(fragments.length);

        for (T fragment : fragments) {
            Stack<T> stack = new Stack<>();
            stack.push(fragment);
            this.mPageList.add(stack);
        }

        T fragment = mPageList.get(mPageIndex).peek();
        if (fragment.isAdded() || fragment.isHidden() || fragment.isDetached()) {
            this.showFragment(mPageIndex);
        } else {
            if (mOnChangedFragmentListener != null)
                mOnChangedFragmentListener.onChangedFragment(fragment);
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.add(mLayoutId, fragment);
            transaction.commitAllowingStateLoss();
        }
        this.showFragment(0);
    }

    void pushFragment(T fragment) {
        Stack<T> currentStack = mPageList.get(mPageIndex);
        if (currentStack.peek().getTagName().equals(fragment.getTagName()))
            return;

        T hideFragment = currentStack.peek();
        currentStack.push(fragment);
        if (mOnChangedFragmentListener != null)
            mOnChangedFragmentListener.onChangedFragment(fragment);

        fragment.setCurrentScreen(true);
        fragment.setPush(true);
        hideFragment.setCurrentScreen(true);
        hideFragment.setPush(true);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (hideFragment.isShouldSave())
            transaction.hide(hideFragment);
        else
            transaction.detach(hideFragment);

        transaction.add(mLayoutId, fragment);
        transaction.commitAllowingStateLoss();

    }

    public boolean popFragment() {
        return popFragment(1);
    }

    void popFragmentToRoot() {
        int level = mPageList.get(mPageIndex).size() - 1;
        popFragment(level);
    }

    private boolean popFragment(int level) {
        if (level <= 0) return false;
        if (mPageList.get(mPageIndex).size() <= level) return false;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        while (level >= 1) {
            T fragment = mPageList.get(mPageIndex).pop();
            fragment.setCurrentScreen(true);
            fragment.setPush(false);
            transaction.remove(fragment);
            level--;
        }
        T showFragment = mPageList.get(mPageIndex).peek();

        if (mOnChangedFragmentListener != null)
            mOnChangedFragmentListener.onChangedFragment(showFragment);

        showFragment.setCurrentScreen(true);
        showFragment.setPush(false);

        if (showFragment.isHidden())
            transaction.show(showFragment);
        else if (showFragment.isDetached())
            transaction.attach(showFragment);

        transaction.commitAllowingStateLoss();
        return true;
    }


    public void showFragment(int index) {
        if (index == mPageIndex) return;
        T showFragment = mPageList.get(index).peek();
        T hideFragment = mPageList.get(mPageIndex).peek();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mPageIndex > index) {
            showFragment.setInLeft(true);
            hideFragment.setOutLeft(false);
        } else {
            showFragment.setInLeft(false);
            hideFragment.setOutLeft(true);
        }
        showFragment.setCurrentScreen(false);
        hideFragment.setCurrentScreen(false);
        mPageIndex = index;

        if (showFragment.isAdded()) {
            if (showFragment.isDetached())
                transaction.attach(showFragment);
            else if (showFragment.isHidden())
                transaction.show(showFragment);
        } else {
            transaction.add(mLayoutId, showFragment);
        }
        if (hideFragment.isShouldSave())
            transaction.hide(hideFragment);
        else
            transaction.detach(hideFragment);
        transaction.commitAllowingStateLoss();
        if (mOnChangedFragmentListener != null)
            mOnChangedFragmentListener.onChangedFragment(showFragment);
    }

    void replaceFragment(T fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(mLayoutId, fragment);
        mPageList.get(mPageIndex).pop();
        mPageList.get(mPageIndex).push(fragment);
        fragment.setCurrentScreen(true);
        fragment.setPush(true);
        transaction.commitAllowingStateLoss();
    }

    public BaseFragment getCurrentFragment() {
        return mPageList.get(mPageIndex).peek();
    }

    public void release() {
        if (mBuildFragments != null && mBuildFragments.length > 0) {
            for (T fragment : mBuildFragments) {
                fragment = null;
            }
        }
        mBuildFragments = null;
        mOnChangedFragmentListener = null;
        mFragmentManager = null;
    }

    public interface OnChangedFragmentListener {
        void onChangedFragment(BaseFragment fragment);
    }
}
