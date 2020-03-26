package com.xz.myapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xz.base.BaseActivity;
import com.xz.myapp.R;
import com.xz.ui.button.XButton;
import com.xz.ui.widget.BazierDemoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BezierActivity extends BaseActivity {


    @BindView(R.id.bezier_view)
    BazierDemoView bezierView;
    @BindView(R.id.et_2)
    EditText et2;
    @BindView(R.id.et_3)
    EditText et3;
    @BindView(R.id.refresh_btn)
    XButton refreshBtn;
    @BindView(R.id.start_anim)
    XButton startAnim;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_bezier;
    }

    @Override
    public void initData() {

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezierView.refresh(
                        Integer.valueOf(et2.getText().toString().trim()),
                        Integer.valueOf(et3.getText().toString().trim()));
            }
        });
        startAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezierView.stopAnim();
                bezierView.startAnim();
            }
        });

        et2.setText(String.valueOf(bezierView.getCvY()));
        et3.setText(String.valueOf(bezierView.getWaveLen()));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
