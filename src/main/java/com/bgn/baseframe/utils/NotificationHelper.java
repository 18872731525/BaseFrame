package com.bgn.baseframe.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bgn.baseframe.LibLoader;
import com.bgn.baseframe.R;

/**
 * Created by wanglun on 2018/3/8.
 */

public class NotificationHelper {
    public final static int FOREGROUND_NOTIFICATION_ID = 100;
    public final static String ACTION_NOTIFICATION = "com.wdw.wendao.app.notification.action";

    public static Notification getNotification() {
        int NF_ID = 1;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(LibLoader.getApplication());
        Intent intent = new Intent(ACTION_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(LibLoader.getApplication(), NF_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentTitle("风跑");// 设置通知栏标题
        mBuilder.setContentText("风跑服务开启中"); // 设置通知栏显示内容
        mBuilder.setTicker("风跑运行中");// 通知首次出现在通知栏，带上升动画效果的
        RemoteViews rvMain = new RemoteViews(BasePackageUtil.getPackageName(), R.layout.notification_layout);
        rvMain.setTextViewText(R.id.tv_km, "风跑服务运行中");
        mBuilder.setContent(rvMain);

        //mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentIntent(pendingIntent);// 设置通知栏点击意图
        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        mBuilder.setPriority(Notification.PRIORITY_MAX); // 设置该通知优先级
        mBuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消

        mBuilder.setOngoing(true);// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;

        return notification;
    }
}
