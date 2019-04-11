package com.bgn.baseframe.utils;

import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bgn.baseframe.R;
import com.orhanobut.logger.Logger;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import es.dmoral.toasty.Toasty;


/**
 * Created by wanglun on 2018/3/21.
 */

public class ToastUtil {
    public static void showInfo(String message) {
        if (TextUtils.isEmpty(message)) {
            Logger.d("要输出的message为空");
        } else {
            //  Toasty.info(UiUtil.getContext(), message).show();
            Toasty.custom(UiUtil.getContext(), message, null, UiUtil.getColor(R.color.gainsboro), Toast.LENGTH_SHORT, false, true).show();
        }
    }

    public static void showInfo(int StringId) {
        String message = UiUtil.getString(StringId);
        if (TextUtils.isEmpty(message)) {
            Logger.d("要输出的message为空");
        } else {
            // Toasty.info(UiUtil.getContext(), message).show();
            Toasty.custom(UiUtil.getContext(), message, null, UiUtil.getColor(R.color.gainsboro), Toast.LENGTH_SHORT, false, true).show();
        }
    }

}
