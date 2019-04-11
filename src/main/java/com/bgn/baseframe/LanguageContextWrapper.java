package com.bgn.baseframe;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.bgn.baseframe.utils.LanguageUtil;

import java.util.Locale;

/**
 * Created by wanglun on 2018/3/5.
 */

public class LanguageContextWrapper extends ContextWrapper {

    public LanguageContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, Locale newLocale) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);
        } else {
            if (configuration.locale != newLocale) {
                LanguageUtil.setAppLanguage(newLocale);
            }
        }
        return new ContextWrapper(context);
    }
}
