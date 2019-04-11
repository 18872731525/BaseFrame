package com.bgn.baseframe.base;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.bgn.baseframe.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;


/**
 * Created by wanglun on 2018/3/27.
 */

public abstract class BaseListFragment<V extends BaseView, T extends BasePresenterImpl<V>, M> extends MVPBaseListFragment<V, T> {

    /*数据*/
    /*网络请求标识*/
    public static final int HTTP_INIT_LIST = 1;
    public static final int HTTP_LOAD_MORE_LIST = 2;
    public static final int HTTP_REFRESH_LIST = 3;
    /*分页*/
    public int page_index = 0;
    public int page_count = 10;

    public RecyclerView recycleView;
    public SwipeRefreshLayout swipeView;
    /*适配器*/
    public BaseQuickAdapter<M, BaseViewHolder> adapter;
    /*数据*/
    public List<M> datas = new ArrayList<>();
    /*头部view*/
    public View headView;

    @Override
    protected View initView(LayoutInflater inflater) {
        View rootView = inflater.inflate(getLaoutResouse(), null);
        recycleView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_view);
        initRecyclerView();
        initSwipeView();
        initOtherUi(rootView);
        return rootView;
    }

    protected void initOtherUi(View rootView) {

    }

    protected int getLaoutResouse() {
        return R.layout.fragment_base_list;
    }

    public void initRecyclerView() {
        adapter = setAdapter();
        // recycleView.setAdapter(adapter);
        adapter.bindToRecyclerView(recycleView);//双向绑定？更稳？
        setLayoutManage();
        setLoadMoreListener();
        setEmptyView();
        setCusTomLoadMore();
    }

    protected void setCusTomLoadMore() {

    }


    protected boolean shoudOpenLoadMore() {
        return true;
    }

    protected boolean shoudSetEmptyView() {
        return true;
    }

    protected boolean shoudOpenRefresh() {
        return true;
    }

    protected void setLoadMoreListener() {
        if (shoudOpenLoadMore()) {
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    loadMoreData();
                }
            }, recycleView);
        }
    }

    protected void setEmptyView() {
        if (shoudSetEmptyView()) {
            adapter.setEmptyView(R.layout.recycleview_no_data);
        }
    }


    @Override
    protected void initData() {
        if (isNetworkerConnectHint()) {
            initListData();
        } else {
            showDefaultView();
        }
    }

    @Override
    public void onDefaultViewClick(int tag) {
        if (isNetworkerConnectHint()) {
            hideDefaultView();
            initListData();
        }
    }


    protected void setLayoutManage() {
        final GridLayoutManager manager = new GridLayoutManager(getBaseActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount() - 1) {
                    return manager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recycleView.setLayoutManager(manager);
    }

    public void initSwipeView() {
        if (shoudOpenRefresh()) {
            swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (isNetworkerConnectHint()) {
                        onRefreshData();
                    } else {
                        swipeView.setRefreshing(false);
                    }
                }
            });
        } else {
            swipeView.setEnabled(false);
        }

    }

    protected boolean shouldRemoveNoMoreView() {
        return true;
    }

    protected void handleData(List<M> comingDatas, int type) {
        switch (type) {
            case HTTP_INIT_LIST:
                if (comingDatas == null || comingDatas.size() == 0) {
                    datas.clear();
                    adapter.notifyDataSetChanged();
                    if (!shoudSetEmptyView()) {
                        showNoDataDefaultView("No Data");
                    }
                } else if (comingDatas.size() < page_count) {
                    datas.addAll(comingDatas);
                    if (shoudOpenLoadMore()) {
                        adapter.loadMoreEnd(shouldRemoveNoMoreView());
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    page_index++;
                } else {
                    datas.addAll(comingDatas);
                    adapter.notifyDataSetChanged();
                    page_index++;
                }
                break;
            case HTTP_LOAD_MORE_LIST:
                if (comingDatas == null || comingDatas.size() == 0) {
                    adapter.loadMoreEnd(shouldRemoveNoMoreView());
                } else if (comingDatas.size() < page_count) {
                    datas.addAll(comingDatas);
                    adapter.loadMoreEnd(shouldRemoveNoMoreView());
                    page_index++;
                } else {
                    datas.addAll(comingDatas);
                    adapter.loadMoreComplete();
                    page_index++;
                }
                break;
            case HTTP_REFRESH_LIST:
                if (comingDatas == null || comingDatas.size() == 0) {
                    datas.clear();
                    adapter.notifyDataSetChanged();
                    if (!shoudSetEmptyView()) {
                        showNoDataDefaultView("No Data");
                    }
                } else if (comingDatas.size() < page_count) {
                    page_index = 1;
                    datas.clear();
                    datas.addAll(comingDatas);
                    adapter.setNewData(datas);
                    if (shoudOpenLoadMore()) {
                        adapter.loadMoreEnd(shouldRemoveNoMoreView());
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    page_index = 1;
                    datas.clear();
                    datas.addAll(comingDatas);
                    adapter.setNewData(datas);
                }
                swipeView.setRefreshing(false);
                break;
        }

    }

    @Override
    public void hideRefreshView() {
        if (swipeView != null && swipeView.isRefreshing()) {
            swipeView.setRefreshing(false);
        }
    }

    /**
     * @param enabled 是否可以下拉刷新，默认true
     */
    protected void setRefreshEnabled(boolean enabled) {
        swipeView.setEnabled(enabled);
    }

    /*强制重写的方法*/

    protected abstract BaseQuickAdapter setAdapter();

    protected abstract void onRefreshData();

    protected abstract void initListData();

    protected abstract void loadMoreData();

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getIntentId(Intent intent) {
        if (intent != null && !TextUtils.isEmpty(intent.getStringExtra(ID))) {
            return intent.getStringExtra(ID);
        } else {
            return "";
        }
    }

    public String getIntentJsonString(Intent intent) {
        if (intent != null && !TextUtils.isEmpty(intent.getStringExtra(BaseActivity.JSON_STRING))) {
            return intent.getStringExtra(BaseActivity.JSON_STRING);
        } else {
            return "";
        }
    }
}
