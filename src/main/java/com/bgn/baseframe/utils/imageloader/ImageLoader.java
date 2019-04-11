package com.bgn.baseframe.utils.imageloader;

import android.net.Uri;
import android.widget.ImageView;

import com.bgn.baseframe.R;
import com.bgn.baseframe.utils.UiUtil;
import com.bumptech.glide.Glide;


import java.io.File;

/**
 * 作者：wl on 2017/9/14 11:31
 * 邮箱：wanglun@stosz.com
 */
public class ImageLoader {

    public static GlideConfing load(String path) {
        return new GlideConfing(Glide.with(UiUtil.getContext()).load(path)).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder);
    }

    public static GlideConfing load(File file) {
        return new GlideConfing(Glide.with(UiUtil.getContext()).load(file)).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder);
    }

    public static GlideConfing load(Uri uri) {
        return new GlideConfing(Glide.with(UiUtil.getContext()).load(uri)).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder);
    }

    public static GlideConfing load(int rid) {
        return new GlideConfing(Glide.with(UiUtil.getContext()).load(rid)).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder);
    }

    public static GlideConfing loadCircle(String url) {
        return new GlideConfing(Glide.with(UiUtil.getContext()).load(url)).setCircleTransform().placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder);
    }

}
