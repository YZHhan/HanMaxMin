package com.han.hanmaxmin.mvp.fragment;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanIFragment;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/22  21:20
 * @Description  List和Fragment结合的顶层接口，
 */

public interface HanIListFragment<D> extends HanIFragment {
    int getHeaderLayout();

    int getFooterLayout();

    int getTopLayout();

    int getBottomLayout();

    ListView getListView();

    int getViewTypeCount();

    int getItemViewType(int position);

    HanListAdapterItem<D> getListAdapterItem(int type);

    void setData(List<D> list);

    void setData(List<D> list,boolean showEmptyView);

    void addData(List<D> list);

    void addData(D d);

    void delete(int position);

    void delete(D d);

    void deleteAll();

    List<D> getData();

    D getData(int position);

    void updateAdapter(boolean showEmptyView);

    BaseAdapter onCreateAdapter();

    BaseAdapter getAdapter();

}
