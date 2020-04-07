package com.xz.myapp.activity.fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;

import com.xz.base.BaseFragment;
import com.xz.base.utils.PreferencesUtilV2;
import com.xz.myapp.R;
import com.xz.myapp.constant.Local;

/**
 * @author czr
 * @date 2020/4/3
 */
public class SettingFragment extends BaseFragment {
    private Button appMode;
    private boolean isNight;//是否夜间模式

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

        isNight = PreferencesUtilV2.getBoolean(Local.SWITCH_MODE_KEY, false);
        switchMode(isNight);

        appMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNight = !isNight;
                switchMode(isNight);
            }
        });
    }


    /**
     * 切换夜间日间模式
     *
     * @param isNight
     */
    private void switchMode(boolean isNight) {
        if (isNight) {
            //当前暗色模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            appMode.setText("亮色模式");
        } else {
            //当前亮色模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            appMode.setText("夜间模式");
        }
        PreferencesUtilV2.putBoolean(Local.SWITCH_MODE_KEY, isNight);
    }
}
