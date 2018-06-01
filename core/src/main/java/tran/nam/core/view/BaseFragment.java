/*
 * Copyright 2017 Vandolf Estrellado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tran.nam.core.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import tran.nam.core.R;

/**
 * Abstract Fragment for all Fragments and child Fragments to extend. This contains some boilerplate
 * dependency injection code and activity {@link Context}.
 * <p>
 * <b>DEPENDENCY INJECTION</b>
 * We could extend {@link dagger.android.DaggerFragment} so we can get the boilerplate
 * dagger code for free. However, we want to avoid inheritance (if possible and it is in this case)
 * so that we have to option to inherit from something else later on if needed.
 * <p>
 * <b>VIEW BINDING</b>
 * This fragment handles view bind and unbinding.
 */
public abstract class BaseFragment extends Fragment implements HasSupportFragmentInjector {

    /**
     * A reference to the activity Context is injected and used instead of the getter method. This
     * enables ease of mocking and verification in tests (in case Activity needs testing).
     * More importantly, the getter method (getContext()) is not available for API level below 23.
     * We could use getActivity() though since that is available since API 11. However, exposing the
     * Activity reference is less safe than just exposing the Context since a lot more can be done
     * with the Activity reference.
     * <p>
     * For more details, see https://github.com/vestrel00/android-dagger-butterknife-mvp/pull/52
     */
    protected boolean mIsCurrentScreen;
    private boolean mShouldSave = true;
    private boolean mIsInLeft;
    private boolean mIsOutLeft;
    private boolean mIsPush;
    private boolean mInitialized = true;
    private boolean mViewCreated = false;
    private boolean mViewDestroyed = false;
    private WaitThread mWaitThread;

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    /**
     * @return layout resource id
     */
    public abstract @LayoutRes int getLayoutId();

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Perform injection here before M, L (API 22) and below because onAttach(Context)
            // is not yet available at L.
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(Activity).
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    public View initLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    protected abstract void initView(Bundle savedInstanceState);

    /**
     * Data initialization
     *
     * @param savedInstanceState Bundle
     */
    protected abstract void initData(Bundle savedInstanceState);

    protected BaseActivityWithFragment getBaseActivity() {
        return ((BaseActivityWithFragment) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initLayout(inflater,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() != null) getView().setOnClickListener(view1 -> {/*Nothing*/});

        onVisible();
        mViewCreated = true;
        mViewDestroyed = false;
        if (mWaitThread != null)
            mWaitThread.continueProcessing();
    }

    protected void addFragment(BaseFragment fragment) {
        if (getActivity() != null && getActivity() instanceof BaseActivityWithFragment && !getActivity().isFinishing())
            ((BaseActivityWithFragment) getActivity()).addFragment(fragment);
    }

    protected void showFragment(int position) {
        if (getActivity() != null && getActivity() instanceof BaseActivityWithFragment && !getActivity().isFinishing())
            ((BaseActivityWithFragment) getActivity()).showFragment(position);
    }

    protected void replaceFragment(BaseFragment fragment) {
        if (getActivity() != null && getActivity() instanceof BaseActivityWithFragment && !getActivity().isFinishing())
            ((BaseActivityWithFragment) getActivity()).replaceFragment(fragment);
    }

    protected void showLoadingDialog(){
        if (getActivity() != null && getActivity() instanceof BaseActivityWithFragment && !getActivity().isFinishing())
            ((BaseActivity) getActivity()).showLoadingDialog();
    }

    protected void hideLoadingDialog(){
        if (getActivity() != null && getActivity() instanceof BaseActivityWithFragment && !getActivity().isFinishing())
            ((BaseActivity) getActivity()).hideLoadingDialog();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation;
        if (mIsCurrentScreen) {
            int popExit = getPopExitAnimId();
            int pushEnter = getPushEnterAnimId();
            int pushExit = getPushExitAnimId();
            int popEnter = getPopEnterAnimId();
            if (mIsPush)
                animation = AnimationUtils.loadAnimation(getBaseActivity(), enter ? pushEnter : pushExit);
            else
                animation = AnimationUtils.loadAnimation(getBaseActivity(), enter ? popEnter : popExit);
        } else {
            if (enter) {
                int left = getLeftInAnimId();
                int right = getRightInAnimId();
                if (mIsInLeft) {
                    if (left == 0) {
                        animation = new AlphaAnimation(1, 1);
                        animation.setDuration(getResources().getInteger(R.integer.animation_time_full));
                    } else {
                        animation = AnimationUtils.loadAnimation(getBaseActivity(), left);
                    }
                } else {
                    if (right == 0) {
                        animation = new AlphaAnimation(1, 1);
                        animation.setDuration(getResources().getInteger(R.integer.animation_time_full));
                    } else {
                        animation = AnimationUtils.loadAnimation(getBaseActivity(), right);
                    }
                }
            } else {
                int left = getLeftOutAnimId();
                int right = getRightOutAnimId();
                animation = AnimationUtils.loadAnimation(getBaseActivity(), mIsOutLeft ? left : right);
            }
        }

        if (enter) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mViewDestroyed)
                        return;
                    mWaitThread = new WaitThread(BaseFragment.this);
                    mWaitThread.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return animation;
    }

    protected void showKeyboard() {
        if (getBaseActivity() != null && getBaseActivity() instanceof BaseActivityWithFragment && !getBaseActivity().isFinishing()) {
            getBaseActivity().showKeyboard();
        }
    }

    protected void hideKeyboard() {
        if (getBaseActivity() != null && getBaseActivity() instanceof BaseActivity && !getBaseActivity().isFinishing())
            getBaseActivity().hideKeyboard();
    }

    public String getTagName() {
        return getClass().getSimpleName();
    }

    public boolean isInitialized() {
        return mInitialized;
    }

    public void initialized() {
        mInitialized = false;
    }

    public boolean isViewCreated() {
        return mViewCreated;
    }

    public void setInLeft(boolean inLeft) {
        mIsInLeft = inLeft;
    }

    public void setOutLeft(boolean outLeft) {
        mIsOutLeft = outLeft;
    }

    public void setCurrentScreen(boolean currentScreen) {
        mIsCurrentScreen = currentScreen;
    }

    public void setPush(boolean push) {
        mIsPush = push;
    }

    public boolean isShouldSave() {
        return true;
    }

    protected void onVisible() {}

    protected void handleAfterVisible() {}

    protected void onInVisible() {}

    protected int getPushExitAnimId() {
        return R.anim.slide_out_left;
    }

    protected int getPopEnterAnimId() {
        return R.anim.slide_in_left;
    }

    protected int getPopExitAnimId() {
        return R.anim.slide_out_right;
    }

    protected int getPushEnterAnimId() {
        return R.anim.slide_in_right;
    }

    protected int getLeftInAnimId() {
        return R.anim.slide_in_left;
    }

    protected int getRightInAnimId() {
        return R.anim.slide_in_right;
    }

    protected int getLeftOutAnimId() {
        return R.anim.slide_out_left;
    }

    protected int getRightOutAnimId() {
        return R.anim.slide_out_right;
    }

    @Override
    public void onDestroyView() {
        // This lifecycle method still gets called even if onCreateView returns a null view.
        if (mWaitThread != null)
            mWaitThread.stopProcessing();
        mViewDestroyed = true;
        mViewCreated = false;
        onInVisible();
        super.onDestroyView();
    }
}