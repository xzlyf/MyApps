package com.xz.myapp.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;

import com.orhanobut.logger.Logger;
import com.xz.base.BaseApplication;
import com.xz.base.BaseFragment;
import com.xz.myapp.R;
import com.xz.myapp.constant.Local;
import com.xz.utils.PreferencesUtilV2;

import butterknife.BindView;

/**
 * @author czr
 * @date 2020/4/3
 */
public class SettingFragment extends BaseFragment {
    private Button appMode;

    @Override
    protected int getLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View rootView) {
        appMode = rootView.findViewById(R.id.app_mode);
    }

    @Override
    protected void initDate(Context mContext) {

        boolean isNight = PreferencesUtilV2.getBoolean(Local.SWITCH_MODE_KEY, false);
        //switchMode(isNight);
    }


    /**
     * 切换夜间日间模式
     *
     * @param isNight
     */
    private void switchMode(boolean isNight) {
        if (isNight) {
            //当前暗色模式
            PreferencesUtilV2.putBoolean(Local.SWITCH_MODE_KEY, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            appMode.setText("亮色模式");
        } else {
            //当前亮色模式
            PreferencesUtilV2.putBoolean(Local.SWITCH_MODE_KEY, true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            appMode.setText("夜间模式");

        }
    }
}
