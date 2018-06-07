package tran.nam.alarmtimer.application.view.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.viewmodel.MainViewModel;
import tran.nam.alarmtimer.databinding.ActivityMainBinding;
import tran.nam.alarmtimer.widget.NTTextView;
import tran.nam.core.view.BaseFragment;
import tran.nam.core.view.mvvm.BaseActivityWithFragmentMVVM;
import tran.nam.util.Constant;
import tran.nam.util.StatusBarUtil;

import static tran.nam.alarmtimer.type.ToolbarType.ALARM;
import static tran.nam.alarmtimer.type.ToolbarType.HOME;

public class MainActivity extends BaseActivityWithFragmentMVVM<ActivityMainBinding, MainViewModel> {

    @Inject
    NavigatorApp mNavigatorApp;

    private AlarmFragment alarmFragment;

    @Override
    public int getLayoutId() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.setToolbarModel(mViewModel.toolbarModel);
        initTabItem(mViewDataBinding.tabLayout);
        mViewDataBinding.setItemToolbarClick(type -> {
            switch (type) {
                case ALARM:
                    mNavigatorApp.goToAddAlarm(this, null);
                    break;
                case HOME:
                    PopupMenu menu = new PopupMenu(this, mViewDataBinding.toolbar.ivOptionalEnd);

                    MenuInflater inflater = menu.getMenuInflater();
                    inflater.inflate(R.menu.home_menu, menu.getMenu());

                    MenuItem itemSetting = menu.getMenu().findItem(R.id.popup_home_setting);
                    SpannableString setting = new SpannableString("Settings");
                    setting.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, setting.length(), 0);
                    itemSetting.setTitle(setting);

                    MenuItem itemSupport = menu.getMenu().findItem(R.id.popup_home_support);
                    SpannableString support = new SpannableString("Supports");
                    support.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, support.length(), 0);
                    itemSupport.setTitle(support);

                    menu.setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()) {
                            case R.id.popup_home_setting:
                                mNavigatorApp.goToSettingHome(this);
                                return true;
                            case R.id.popup_home_support:
                                mNavigatorApp.goToSupportHome(this);
                                return true;
                        }
                        return false;
                    });
                    menu.show();

                    break;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.toolbar_color));
    }

    public void initTabItem(TabLayout tabLayout) {
        Context context = tabLayout.getContext();
        TabLayout.Tab home = tabLayout.newTab();
        initTab(context, home, "Home");

        TabLayout.Tab alarm = tabLayout.newTab();
        initTab(context, alarm, "Program");

        tabLayout.addTab(home);
        tabLayout.addTab(alarm);
        home.select();
    }

    private void initTab(Context context, TabLayout.Tab project, String tabString) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.layout_tab_item, null);
        NTTextView text = view.findViewById(R.id.tv_tab);
        text.setText(tabString);
        project.setCustomView(view);
    }

    @Override
    public BaseFragment[] getFragments() {
        alarmFragment = AlarmFragment.getInstance();
        return new BaseFragment[]{HomeFragment.getInstance(), alarmFragment};
    }

    @Override
    public int getContentLayoutId() {
        return R.id.contain_main;
    }

    @Override
    public void onBackPressed() {
        if (!mFragmentHelper.popFragment()) {
            mNavigatorApp.exit(this);
        }
    }

    public void tabAlarm() {
        TabLayout.Tab tab = mViewDataBinding.tabLayout.getTabAt(1);
        if (tab != null)
            tab.select();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_CODE.SETTING_HOME) {
            if (resultCode == RESULT_OK) {
                BaseFragment fragment = mFragmentHelper.getCurrentFragment();
                if (fragment != null && fragment instanceof HomeFragment){
                    ((HomeFragment)fragment).updateWetMode();
                }
                alarmFragment.initData(null);
            }
        }
    }

    public void updateAlarm(){
        alarmFragment.initData(null);
    }
}
