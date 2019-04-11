package com.bgn.baseframe.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bgn.baseframe.LibLoader;
import com.bgn.baseframe.R;


import java.util.Locale;

/**
 * 作者：wl on 2017/9/14 17:08
 * 邮箱：wanglun@stosz.com
 */
public class LanguageUtil {

    /*定义要做适配的语言*/

    /**
     * 简体中文
     */
    public static final String CHINESE_SIMPLIFIED = "zh_CN";
    /**
     * 繁体台湾.
     */
    public static final String CHINESE_HK = "zh_TW";

    /**
     * 繁体港澳.
     */
    public static final String CHINESE_TW = "zh_HK";

    /**
     * English.
     */
    public static final String ENGLISH = "en";

//    public static void setAppLanguage(String language2country) {
//        Resources resources = LibLoader.getApplication().getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        Configuration config = resources.getConfiguration();
//        // 应用用户选择语言
//        config.locale = getLocaleByString(language2country);
//        resources.updateConfiguration(config, dm);
//    }

    public static void setAppLanguage(Locale language2country) {
        Resources resources = LibLoader.getApplication().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        config.locale = language2country;
        resources.updateConfiguration(config, dm);
    }


    public static int getCountryImageByString(String language2country) {
        if (!TextUtils.isEmpty(language2country) && language2country.length() >= 2) {
            Locale locale;
            String languageString = language2country;
            if (language2country.contains("_")) {
                int lenthOfLanguageString = language2country.indexOf("_");
                languageString = language2country.substring(0, lenthOfLanguageString);
            }
            switch (languageString) {
                case "zh":
                    return R.mipmap.cn;
                case "en":
                    return R.mipmap.us;
                default:
                    return R.mipmap.us;
            }

        } else {
            return R.mipmap.us;
        }
    }


    public static Locale getLocaleByString(String language2country) {
        if (!TextUtils.isEmpty(language2country) && language2country.length() >= 2) {
            Locale locale;
            String languageString = language2country;
            if (language2country.contains("_")) {
                int lenthOfLanguageString = language2country.indexOf("_");
                languageString = language2country.substring(0, lenthOfLanguageString);
            }
            switch (languageString) {
                case "zh":
                    if (language2country.startsWith(CHINESE_SIMPLIFIED)) {
                        locale = new Locale("zh", "CN");
                    } else if (language2country.startsWith(CHINESE_HK)) {
                        locale = new Locale("zh", "HK");
                    } else if (language2country.startsWith(CHINESE_TW)) {
                        locale = new Locale("zh", "TW");
                    } else {
                        locale = new Locale("zh", "CN");
                    }
                    break;
                case "en":
                    // locale = Locale.ENGLISH;
                    locale = new Locale("en", "US");
                    break;
                default:
                    locale = Locale.ENGLISH;
                    break;
            }
            return locale;
        } else {
            return Locale.ENGLISH;
        }

    }

    public static String getSavedLanguage2Country() {
        String language2country = PreferUtil.getString(PreferUtil.KEY_LANGUAGE2COUNTRY, "");
        if (TextUtils.isEmpty(language2country)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                language2country = Resources.getSystem().getConfiguration().getLocales().get(0).toString();
            } else {
                language2country = Locale.getDefault().toString();
            }

        }
        return language2country;
    }

    public static void savedLanguage2Country(String code) {
        PreferUtil.putString(PreferUtil.KEY_LANGUAGE2COUNTRY, code);
    }


}
