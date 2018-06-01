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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import dagger.android.AndroidInjection;

/**
 * Abstract Activity for all Activities to extend.
 * <p>
 * <b>DEPENDENCY INJECTION</b>
 * We could extend {@link dagger.android.DaggerActivity} so we can get the boilerplate
 * dagger code for free. However, we want to avoid inheritance (if possible and it is in this case)
 * so that we have to option to inherit from something else later on if needed.
 */
public abstract class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private LoadingDialog mLoadingDialog;

    /**
     * @return layout resource id
     */
    public abstract @LayoutRes
    int getLayoutId();

    protected void initLayout(){
        setContentView(getLayoutId());
    };

    protected abstract void setStatusBar();

    /*
     * Init Fragment Helper
     **/
    protected void initFragment() {}

    public abstract void initView(Bundle savedInstanceState);

    /**
     * Data initialization
     *
     * @param savedInstanceState Bundle
     */
    public abstract void initData(Bundle savedInstanceState);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBar();
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        initLayout();
        initFragment();
        mLoadingDialog = new LoadingDialog(this);
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.showDialog();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.hideDialog();
        }
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}