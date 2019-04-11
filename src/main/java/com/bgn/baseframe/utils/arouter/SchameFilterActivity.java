package com.bgn.baseframe.utils.arouter;

import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bgn.baseframe.base.BaseActivity;
import com.bgn.baseframe.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：wl on 2018/4/17 10:12
 * 邮箱：wanglun@stosz.com
 */
@Route(path = "/base/schameFilterActivity", group = "base")
public class SchameFilterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  ToastUtil.showInfo("1111");
        Uri uri = getIntent().getData();
        //ARouter.getInstance().build("/mall/mainActivity").navigation();
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}
