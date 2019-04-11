package com.bgn.baseframe.utils;

import android.Manifest;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 作者：wl on 2017/9/26 15:03
 * 邮箱：wanglun@stosz.com
 */
public class DeviceUtils {
    public static String deviceId;

    public static String getDeviceId() {

        if (TextUtils.isEmpty(deviceId)) {
            try {
                android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) UiUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);

                if (EasyPermissions.hasPermissions(UiUtil.getContext(), Manifest.permission.READ_PHONE_STATE)) {
                    deviceId = tm.getDeviceId();
                }

            } catch (Exception e) {
                Logger.e("获取设备号异常");
            }

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = android.provider.Settings.Secure.getString(UiUtil.getContext().getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
        }
        return deviceId;
    }

    /**
     * 对外获取Udid
     *
     * @return
     */
    public static String getUdid() {
        Context context = UiUtil.getContext();
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = "";
        tmSerial = "";
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
        }

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            try {
                md.update(uniqueId.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : result) {
            int i = b & 0xff;
            if (i < 0xf) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(i));
        }
        uniqueId = sb.toString().toLowerCase();

        return uniqueId;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }
}
