package com.bgn.baseframe.view.webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bgn.baseframe.R;
import com.bgn.baseframe.base.BaseActivity;
import com.orhanobut.logger.Logger;

/**
 * 类描述：
 * 创建人：wl
 * 创建时间：2018/4/11 10:08
 */
public class BaseWebViewActivity extends BaseActivity {
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String FROM_ACTIVITY = "from";
    public WebView myWebview;

    public String title;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarState();
        setContentView(R.layout.activity_base_webview);
        initUI();
        initData();
    }

    public void setTitleBarState() {

    }

    public void setTitleAsWeb(String text) {

    }

    public String deCodeUrl(String url) {
        return url;
    }

    public void addSettingToWebView() {

    }


    protected void initUI() {

        myWebview = (WebView) findViewById(R.id.web_view);
        myWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        myWebview.getSettings().setJavaScriptEnabled(true);
        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        myWebview.getSettings().setDomStorageEnabled(true);

        addSettingToWebView();
        myWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (isLoadNext(url)) {
                    view.loadUrl(deCodeUrl(url));
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showDefaultView();
                hideLoadingView();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoadingView();
                hideDefaultView();
                if (view != null) {
                    setTitleAsWeb(view.getTitle());
                }
                //getTitleBar().setTitle(view.getTitle());

            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (isNetworkerConnectHint()) {
                    super.onPageStarted(view, url, favicon);
                } else {
                    showDefaultView();
                }

            }

//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                if (error.getCertificate() != null) {
//                    handler.proceed(); // 接受证书
//                } else {
//                    handler.cancel(); // 接受证书
//                }
//            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //Logger.d("错误：" + error.getDescription().toString() + "地址：" + view.getUrl());
                super.onReceivedError(view, request, error);
                hideLoadingView();
            }
        });
    }

    protected void initData() {
        title = getIntent().getStringExtra(TITLE);
        url = getIntent().getStringExtra(URL);

        Logger.d("H5地址：" + url);
        if (getTitleBar() != null && !TextUtils.isEmpty(title)) {
            getTitleBar().setTitle(title);
        }


        if (TextUtils.isEmpty(url) || !isNetworkerConnectHint()) {
            showDefaultView();
        } else {
            showLoadingView();
            myWebview.loadUrl(url);
        }
    }

    @Override
    public void onDefaultViewClick(int tag) {
        super.onDefaultViewClick(tag);
        if (isNetworkerConnectHint()) {
            myWebview.loadUrl(url);
            showLoadingView();
        }
    }

    public boolean isLoadNext(String url) {
        return true;
    }

    public static void start(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, BaseWebViewActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (myWebview != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = myWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(myWebview);
            }
            myWebview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            myWebview.getSettings().setJavaScriptEnabled(false);
            myWebview.clearHistory();
            myWebview.clearView();
            myWebview.removeAllViews();
            myWebview.destroy();
        }

        super.onDestroy();
    }
}
