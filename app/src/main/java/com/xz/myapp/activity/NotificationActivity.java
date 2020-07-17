package com.xz.myapp.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.xz.base.BaseActivity;
import com.xz.myapp.R;

import butterknife.BindView;

public class NotificationActivity extends BaseActivity {


    @BindView(R.id.btn_1)
    Button btn1;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_notification;
    }

    @Override
    public void initData() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotifiction(NotificationActivity.this);

            }
        });
    }

    private void showNotifiction(@NonNull Context context) {
        int NOTIFICATION_ID = 5270;

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        assert manager != null;
        Notification notification = null;

        Intent intent = new Intent();
        intent.setAction(context.getPackageName());

        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification();
            notification.icon = android.R.drawable.stat_sys_download_done;
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            //notification.setLatestEventInfo(mContext, aInfo.mFilename, contentText, pi);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new Notification.Builder(context)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .build();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new NotificationCompat.Builder(context)
                    .setContentTitle("标题")
                    .setContentText("这是内容")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pi)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            manager.createNotificationChannel(mChannel);

            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.stat_sys_download_done)
                    .setContentText("测试测试")
                    .setContentTitle("Title").build();
        }
        manager.notify(NOTIFICATION_ID, notification);

    }

}
