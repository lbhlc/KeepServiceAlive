package com.example.administrator.keepservicealive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.keepservicealive.R;


/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/10/31.
 *         这个类主要是应用了，提高自身oom的值和把该service放置在前台来提高自己的存活的几率
 */

public class FirstWay extends Service {
    private final String TAG="LBH";
    public static final int NOTICE_ID=100;
    private final String THECLASSNAME=getClass().getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,THECLASSNAME);
        //在API18以上有一个弹出通知栏
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            Notification.Builder builder=new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("KEEP ALIVE");
            builder.setContentText("我还活着！！！");
            startForeground(NOTICE_ID,builder.build());
            Intent intent=new Intent(this,CancelNoticeService.class);
            startService(intent);

        }
        else
        {
            startForeground(NOTICE_ID,new Notification());
        }

    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        //当系统资源充足的情况下重新启动自己
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager mManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(NOTICE_ID);
        }
        Intent intent=new Intent(getApplicationContext(),FirstWay.class);
        startActivity(intent);
    }
}
