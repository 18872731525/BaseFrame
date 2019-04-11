package com.bgn.baseframe.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.bgn.baseframe.LibLoader;
import com.bgn.baseframe.R;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 作者：wl on 2017/9/13 16:09
 * 邮箱：wanglun@stosz.com
 */

//类作用描述：1.提供常见的获取屏幕宽高、获取各种资源等方法
//            2.px  dp转换
//            3.提供延时处理的方法
public class UiUtil {

    //手机状态栏的高度
    private static int stateBarHeight;
    //屏幕的高度
    private static int screenHeight;
    //屏幕的宽度
    private static int screenWidth;
    //虚拟导航栏的高度
    private static int navigation_bar_height;

    private static void init(Context context) {

        //获取状态栏的高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            stateBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        //获取屏幕的高度和宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        //获取底部导航栏的高度
        int navigationId = 0;
        int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid > 0) {
            navigationId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            navigation_bar_height = getResources().getDimensionPixelSize(navigationId);
        }

        Logger.i("screenHeight = %d, screenWidth = %d, stateBarHeight = %d, navigation_bar_height = %d", screenHeight, screenWidth, stateBarHeight, navigation_bar_height);
    }

    static {
        init(getContext());
    }

    public static Context getContext() {
        return LibLoader.getApplication();
    }


    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @return 获取导航栏高度
     */
    public static int getNavigation_bar_height() {
        return navigation_bar_height;
    }

    /**
     * @return 状态栏高度
     */
    public static int getStateBarHeight() {
        return stateBarHeight;
    }

    /**
     * @return 获取屏幕高度
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * @return 获取屏幕宽度
     */
    public static int getScreenWidth() {
        return screenWidth;
    }


    public static long getMainThreadId() {
        return LibLoader.getMainThreadId();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return LibLoader.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /**
     * 加载布局
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }


    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }


    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * 判断触点是否落在该View上
     */
    public static boolean isTouchInView(MotionEvent ev, View v) {
        if (ev == null || v == null) {
            return false;
        }

        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
    }

    public static void setMidLineToViewText(TextView textView) {
//        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
//        textView.getPaint().setAntiAlias(true);//抗锯齿
        //  textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        //       textView.getPaint().setFlags(0);  // 取消设置的的划线

    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static void copyToBoard(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) LibLoader.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
    }

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + FOREWARD_SLASH + resourceId);
    }

    public static void handleNoToast(final Activity outSide) {
        if (!isNotificationEnabled(getContext())) {
            final AlertDialog dialog = new AlertDialog.Builder(outSide).create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            View view = View.inflate(outSide, R.layout.dialog_notification_open, null);
            dialog.setContentView(view);

            TextView titleView = (TextView) view.findViewById(R.id.dialog_title);
            titleView.setText("友情提示");

            final TextView context = (TextView) view.findViewById(R.id.tv_content);
            context.setText("检测到您没有打开通知权限,或默认屏蔽了应用通知。您将无法收到应用内提示,为正常使用,请到设置中开启");

            TextView confirm = (TextView) view.findViewById(R.id.tv_yes);
            confirm.setText("确定");
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent localIntent = new Intent();
                    //直接跳转到应用通知设置的代码：
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        localIntent.putExtra("app_package", BasePackageUtil.getPackageName());
                        localIntent.putExtra("app_uid", getContext().getApplicationInfo().uid);
                        outSide.startActivity(localIntent);
                    } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        localIntent.setData(Uri.parse("package:" + getContext().getPackageName()));
                        outSide.startActivity(localIntent);
                    }
                    dialog.dismiss();
                }
            });
        }
    }

    /**
     * 获取通知权限
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
