//package com.bgn.baseframe.view.picture;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.bgn.baseframe.R;
//import com.bgn.baseframe.utils.BaseFileUtil;
//import com.jph.takephoto.app.TakePhoto;
//import com.jph.takephoto.app.TakePhotoActivity;
//import com.jph.takephoto.compress.CompressConfig;
//import com.jph.takephoto.model.CropOptions;
//import com.jph.takephoto.model.LubanOptions;
//import com.jph.takephoto.model.TResult;
//import com.jph.takephoto.model.TakePhotoOptions;
//import com.orhanobut.logger.Logger;
//
//import java.io.File;
//
//
///**
// * Created by Administrator on 2018/4/8.
// */
//
//@Route(path = "/base/selectPictureActivity", group = "base")
//public class SelectPictureActivity extends TakePhotoActivity {
//
//    public static String PATH = "path";
//
//    TextView tvPhotograph;
//    TextView tvAlbum;
//    TextView tvCancel;
//    TakePhoto takePhotoInstance;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.select_picture_dialog);
//        initView();
//        initPhotoOption();
//
//    }
//
//    private void initPhotoOption() {
//        if (takePhotoInstance == null) {
//            takePhotoInstance = getTakePhoto();
//            /*压缩配置*/
//            int maxSize = 1024 * 100;
//            int width = 800;
//            int height = 800;
//            boolean showProgressBar = false;
//            boolean enableRawFile = false;
//            CompressConfig config;
//            // LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
//            config = new CompressConfig.Builder().setMaxSize(maxSize)
//                    .setMaxPixel(width >= height ? width : height)
//
//                    //.enableReserveRaw(enableRawFile)
//                    .create();
//            takePhotoInstance.onEnableCompress(config, showProgressBar);
//            /*选图设置-重相册*/
//            TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
//            builder.setWithOwnGallery(false);
//            /*纠正角度问题*/
//            builder.setCorrectImage(true);
//            takePhotoInstance.setTakePhotoOptions(builder.create());
//
//        }
//
//    }
//
//    private Uri getImageUri() {
//        /*设置头像路径*/
//        File file = BaseFileUtil.makeFileIfNotExists(BaseFileUtil.getImageDir() + "/" + BaseFileUtil.HEAD_IMAGE_FILENAME);
//        Uri imageUri = Uri.fromFile(file);
//        return imageUri;
//    }
//
//    private CropOptions getCropOption() {
//        /*裁剪配置*/
//        CropOptions.Builder cropBuilder = new CropOptions.Builder();
//        //cropBuilder.setOutputX(100).setOutputY(100);
//        cropBuilder.setAspectX(1).setAspectY(1);
//        cropBuilder.setWithOwnCrop(false);
//        return cropBuilder.create();
//    }
//
//
//    private void initView() {
//        tvPhotograph = findViewById(R.id.tv_photograph);
//        tvAlbum = findViewById(R.id.tv_album);
//        tvCancel = findViewById(R.id.tv_cancel);
//        tvPhotograph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePhotograph();
//            }
//        });
//        tvAlbum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectFromGallery();
//            }
//        });
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    public void takePhotograph() {
//        /*选择拍照类型*/
//        //takePhotoInstance.onPickMultipleWithCrop(limit, getCropOptions());
//        // takePhotoInstance.onPickMultiple(limit);
//        //takePhotoInstance.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
//        //takePhotoInstance.onPickFromDocuments();
//        //takePhotoInstance.onPickFromGalleryWithCrop(imageUri, getCropOptions());
//        // takePhotoInstance.onPickFromGallery();
//        takePhotoInstance.onPickFromCaptureWithCrop(getImageUri(), getCropOption());
//        //takePhotoInstance.onPickFromCapture(imageUri);
//    }
//
//    public void selectFromGallery() {
//        /*选择拍照类型*/
//        //takePhotoInstance.onPickMultipleWithCrop(limit, getCropOptions());
//        // takePhotoInstance.onPickMultiple(limit);
//        //takePhotoInstance.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
//        // takePhotoInstance.onPickFromDocuments();
//        takePhotoInstance.onPickFromGalleryWithCrop(getImageUri(), getCropOption());
//        //takePhotoInstance.onPickFromGallery();
//        //takePhotoInstance.onPickFromCaptureWithCrop(getImageUri(), getCropOption());
//        //takePhotoInstance.onPickFromCapture(imageUri);
//    }
//
//    @Override
//    public void takeCancel() {
//        super.takeCancel();
//    }
//
//    @Override
//    public void takeFail(TResult result, String msg) {
//        super.takeFail(result, msg);
//    }
//
//    @Override
//    public void takeSuccess(TResult result) {
//        super.takeSuccess(result);
//        //Logger.d("TEST:" + result.getImages().get(0).getPath());
//        Intent intent = new Intent();
//        intent.putExtra(PATH, result.getImage().getCompressPath());
//        Logger.d("path:" + result.getImage().getCompressPath());
//        setResult(100, intent);
//        finish();
//    }
//
//
//}
