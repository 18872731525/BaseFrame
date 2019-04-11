package com.bgn.baseframe.utils;

import android.content.res.AssetManager;
import android.view.View;

import com.bgn.baseframe.LibLoader;
import com.bgn.baseframe.utils.jsontool.GsonUtil;
import com.orhanobut.logger.Logger;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssetUtil {
    public static String getJsonFromAsset(String fileNmae) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = LibLoader.getApplication().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileNmae)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
