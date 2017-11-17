package com.example.administrator.keepservicealive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.example.administrator.keepservicealive.R;
import com.example.administrator.keepservicealive.service.FirstWay;
import com.example.administrator.keepservicealive.utils.MyExcutorManager;

/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/10/31.
 */

public class CancelNoticeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(FirstWay.NOTICE_ID, builder.build());
            MyExcutorManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    // 延迟1s
                    SystemClock.sleep(1000);
                    // 取消CancelNoticeService的前台
                    stopForeground(true);
                    // 移除DaemonService弹出的通知
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel(FirstWay.NOTICE_ID);
                    // 任务完成，终止自己
                    stopSelf();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
