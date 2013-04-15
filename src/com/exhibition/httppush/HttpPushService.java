package com.exhibition.httppush;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.netty.client.MyClient;
import com.exhibition.utils.MobileConfig;
import com.exhibition.utils.NetworkHelper;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

public class HttpPushService extends Service {

    public static final String TAG = "HttpPushService ";

    public static final int HTTP_PUSH_NOTIFICATION_ID = 1001;
    /**
     * 默认推送时间间隔.
     */
    private static final long DEFAULT_PUSH_INTERVAL = 1000 * 20 * 1;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long interval;

    private NotificationManager mNotifMan;

    private static PendingIntent pi;
    private static AlarmManager alarmMgr;

    private AudioManager mAudioManager;
    private MyClient mMyClient;
    private MobileConfig mMobileConfig;

    protected static void actionStart(Context ctx) {
        Intent i = new Intent(ctx, HttpPushService.class);
        ctx.startService(i);

        int minutes = XmlDB.getInstance(ctx).getKeyIntValue(StringPools.mPushIntervalKey, -1);
        if (minutes < 0) {
            interval = DEFAULT_PUSH_INTERVAL;
        } else {
            interval = minutes * 60 * 1000;
        }
        pi = PendingIntent.getService(ctx, 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmMgr = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pi);
    }

    public static void launchService(Context context) {
        Intent intent = new Intent();
        intent.setAction(HttpPushReceiver.LAUNCH_ACTION);
        context.sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void handleCommand(Intent intent) {  
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
//      handleCommand(intent);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}