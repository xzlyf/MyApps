package com.xz.myapp;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.orhanobut.logger.Logger;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xz.base.BaseActivity;
import com.xz.myapp.activity.fragment.ContentFragment;
import com.xz.myapp.activity.fragment.HomeFragment;
import com.xz.myapp.activity.fragment.SettingFragment;
import com.xz.utils.fileUtils.StorageUtil;
import com.xz.utils.netUtils.DownloadV2Util;

import java.io.IOException;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private HomeFragment homeFragment;
    private ContentFragment contentFragment;
    private SettingFragment settingFragment;
    private Fragment currentFragment = new Fragment();

    @Override
    public boolean homeAsUpEnabled() {
        return false;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        initFragment();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        switchFragment(homeFragment).commit();
                        break;
                    case R.id.tab_content:
                        switchFragment(contentFragment).commit();
                        break;
                    case R.id.tab_setting:
                        switchFragment(settingFragment).commit();

                        break;
                }

            }
        });


    }


    private void initFragment() {
        homeFragment = new HomeFragment();
        contentFragment = new ContentFragment();
        settingFragment = new SettingFragment();

    }


    /**
     * 切换fragment
     *
     * @param targetFragment
     * @return
     */
    private FragmentTransaction switchFragment(Fragment targetFragment) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }
}
