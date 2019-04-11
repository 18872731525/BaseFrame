package com.bgn.baseframe.base;

import android.app.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bgn.baseframe.R;
import com.bgn.baseframe.interfaces.OnClickDefaultBtn;
import com.bgn.baseframe.utils.KeyboardHelper;
import com.bgn.baseframe.utils.NetworkUtil;
import com.bgn.baseframe.utils.ToastUtil;
import com.bgn.baseframe.view.defaultview.DefaultView;
import com.bgn.baseframe.view.loading.LoadingView;
import com.bgn.baseframe.view.statebar.BaseStateBar;
import com.bgn.baseframe.view.statebar.StateBar;
import com.bgn.baseframe.view.titlebar.BaseTitleBar;
import com.bgn.baseframe.view.titlebar.TitleBar;


import es.dmoral.toasty.Toasty;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：wl on 2017/9/18 09:39
 * 邮箱：wanglun@stosz.com
 */
public class BaseActivity extends BasePermissionsAndStackActivity implements BaseView, OnClickDefaultBtn {

    public static final String JSON_STRING = "json_string";
    public static final String POSITION = "position";
    public static final String ID = "id";
    public static final String DATASTRING = "data_string";

    //根容器布局
    private RelativeLayout mContainer;
    //是否加载导航栏
    private boolean isLoadTitleBar = true;
    /*导航栏*/
    private BaseTitleBar titleBar;
    /*状态栏*/
    private BaseStateBar stateBar;
    /*缺省页*/
    private DefaultView defaultView;
    /*加载框子*/
    private LoadingView loadingView;
    /*RxJava的事件解绑管理类？*/
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    /*提供给fragment处理软键盘问题*/
    public RelativeLayout getmContainer() {
        return mContainer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(initContentView(view));
    }

    protected View initContentView(View view) {
        mContainer = new RelativeLayout(this);
        initStateBar();
        initTitleBar();
        mContainer.addView(view, getLayoutParams());
        return mContainer;
    }

    private RelativeLayout.LayoutParams getLayoutParams() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (titleBar != null) {
            lp.addRule(RelativeLayout.BELOW, titleBar.getId());
        } else if (stateBar.isEnabled()) {
            lp.addRule(RelativeLayout.BELOW, stateBar.getId());
        }
        return lp;
    }


    /*********************LoadingView相关方法***********************/

    private void initLoadingView() {
        if (loadingView == null) {
            loadingView = new LoadingView();
            mContainer.addView(loadingView.getRootView(), getLayoutParams());
        }
    }

    private void initLoadingView(int type) {
        if (loadingView == null) {
            loadingView = new LoadingView(type);
            mContainer.addView(loadingView.getRootView(), getLayoutParams());
        } else {
            loadingView.resetAnimation(type);
        }
    }

    /*********************TitleBar相关方法***********************/


    public void initTitleBar() {
        if (isLoadTitleBar) {
            titleBar = new TitleBar(this);
            titleBar.setLeftTextClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.title_bar_height));
            if (stateBar.isEnabled()) {
                lp.addRule(RelativeLayout.BELOW, stateBar.getId());
            }
            mContainer.addView(titleBar.getView(), lp);
        }
    }

    public void setIsLoadTitleBar(boolean isLoad) {
        isLoadTitleBar = isLoad;
    }

    /*********************StateBar相关方法***********************/

    protected void initStateBar() {
        if (setStateBarPattern()) {
            stateBar = new StateBar(true);
            mContainer.addView(stateBar.getView());
        } else {
            stateBar = new StateBar(false);
        }
    }

    protected boolean setStateBarPattern() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return true;
        } else {
            return false;
        }
    }

    /*********************缺省页DefaultView相关方法***********************/

    private void initDefaultView() {
        if (defaultView == null) {
            defaultView = new DefaultView();
            defaultView.setClickListener(this);
            if (loadingView != null) {
                mContainer.addView(defaultView.getRootView(), mContainer.getChildCount() - 1, getLayoutParams());
            } else {
                mContainer.addView(defaultView.getRootView(), getLayoutParams());
            }
        }
    }

    /*********************实现接口的方法**********************/

    @Override
    public void onDefaultViewClick(int tag) {

    }

    @Override
    public boolean isNetworkerConnectHint() {
        boolean networkerConnect = NetworkUtil.isNetworkerConnect();

        if (!networkerConnect) {
            showHint(getString(R.string.network_error));
        }

        return networkerConnect;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoadingView() {
        initLoadingView();
        if (loadingView.getRootView().getVisibility() != View.VISIBLE) {
            loadingView.getRootView().setBackgroundColor(Color.TRANSPARENT);
            loadingView.getRootView().setVisibility(View.VISIBLE);
            KeyboardHelper.hideKeyboard(loadingView.getRootView());
        }
    }

    @Override
    public void showLoadingView(int type) {
        initLoadingView(type);
        if (loadingView.getRootView().getVisibility() != View.VISIBLE) {
            loadingView.getRootView().setBackgroundColor(Color.TRANSPARENT);
            loadingView.getRootView().setVisibility(View.VISIBLE);
            KeyboardHelper.hideKeyboard(loadingView.getRootView());
        }
    }


    public boolean isLoadingShowing() {

        if (loadingView != null && loadingView.getRootView().getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void hideLoadingView() {
        if (loadingView != null && loadingView.getRootView().getVisibility() != View.GONE) {
            loadingView.getRootView().setVisibility(View.GONE);
        }
    }


    @Override
    public void showDefaultView() {
        initDefaultView();
        defaultView.show();
    }

    @Override
    public void showErrorDefaultView(@Nullable String error) {
        initDefaultView();
        defaultView.setErrorData(error);
    }

    @Override
    public void showNoDataDefaultView(String text) {
        initDefaultView();
        defaultView.setResultData(text);
    }

    @Override
    public void hideDefaultView() {
        if (defaultView != null) {
            defaultView.hide();
        }
    }

    @Override
    public DefaultView.DefaultViewBuild getDefaultViewBuild() {
        initDefaultView();
        return defaultView.getBuild();
    }

    @Override
    public boolean defaultViewIsShow() {
        if (defaultView == null) {
            return false;
        } else {
            return defaultView.isShow();
        }
    }

    @Override
    public BaseTitleBar getTitleBar() {
        return titleBar;
    }

    @Override
    public BaseStateBar getStateBar() {
        return stateBar;
    }


    @Override
    public void showHintAndFinish(String hint) {

    }

    @Override
    public void showHint(@Nullable String hintText) {
        if (TextUtils.isEmpty(hintText)) {
        } else {
            //Toasty.info(this, hintText).show();
            ToastUtil.showInfo(hintText);
        }
    }

    @Override
    public void showHint(String hintText, int color) {

    }

    @Override
    public void hideRefreshView() {

    }

    public View createFragmentContainnerView() {
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.id_fragment);
        return frameLayout;
    }

    public void loadFragment(Fragment fragment, int id) {
        if (fragment != null && !fragment.isAdded()) {
            if (id == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment, fragment).commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction().replace(id, fragment).commitAllowingStateLoss();
            }
        }


    }

    /*****************RxJava相关***************************************/
    @Override
    public void addDisposable(Disposable observer) {
        compositeDisposable.add(observer);
    }

    @Override
    public void removeDisposable(Disposable observer) {
        compositeDisposable.remove(observer);
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispose();
    }
}
