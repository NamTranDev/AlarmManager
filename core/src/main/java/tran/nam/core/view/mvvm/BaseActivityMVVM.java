/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package tran.nam.core.view.mvvm;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import javax.inject.Inject;

import tran.nam.common.AutoClearedValue;
import tran.nam.core.view.BaseActivity;
import tran.nam.core.model.BaseViewModel;

public abstract class BaseActivityMVVM<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity {

    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;

    protected VM mViewModel;

    protected V mViewDataBinding;

    @Override
    protected void initLayout() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    @Override
    protected void onDestroy() {
        this.mViewDataBinding = null;
        super.onDestroy();
        mViewModel.detach();
        this.mViewModel = null;
    }
}

