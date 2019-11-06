package com.xz.xzwidget.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xz.xzwidget.R;

public class IpEditView extends LinearLayout {
    private Context mContext;
    private View view;
    private EditText ip1;
    private EditText ip2;
    private EditText ip3;
    private EditText ip4;

    public IpEditView(Context context) {
        super(context);
        init(context);
    }

    public IpEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_ip_editview, this, true);
        ip1 = view.findViewById(R.id.ip_1);
        ip2 = view.findViewById(R.id.ip_2);
        ip3 = view.findViewById(R.id.ip_3);
        ip4 = view.findViewById(R.id.ip_4);
        ip1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //if (start==2){
                //    ip1.clearFocus();
                //    ip2.requestFocus();
                //}
                Log.d("xz", "beforeTextChanged: "+start+"="+after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 返回标准格式的ip地址
     *
     * @return
     */
    public String getIp() {
        return (ip1.getText().toString() + "." + ip2.getText().toString() + "." + ip3.getText().toString() + "." + ip4.getText().toString());
    }


}
