package com.example.administrator.keepservicealive.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.keepservicealive.activity.SingleActivity;

import java.lang.ref.WeakReference;

/**
 * @author libohan
 *         邮箱:76681287@qq.com
 *         create on 2017/11/2.
 */

public class ScreenManager {
    private final String TAG="ScreenManager";
    private Context context;
    private static  ScreenManager instance;
    private WeakReference<Activity> mactivityRef;
    private ScreenManager(Context context) {
        this.context = context;
    }
    public static ScreenManager getInstance(Context context)
    {
        if (instance==null)
        {
            return new ScreenManager(context);
        }
        return instance;
    }

    public void setMactivity(Activity mactivity) {
        this.mactivityRef = new WeakReference<Activity>(mactivity);
    }
    public void startActivity()
    {
        Intent intent=new Intent(context, SingleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void onDestoryActivity()
    {
        if (mactivityRef!=null)
        {
            Activity mactivity=mactivityRef.get();
            if (mactivity!=null)
            {
                mactivity.finish();
            }
        }
    }

}
