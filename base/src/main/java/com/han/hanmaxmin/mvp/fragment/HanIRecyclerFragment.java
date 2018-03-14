package com.han.hanmaxmin.mvp.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.han.hanmaxmin.common.widget.recyclerview.HeaderFooterRecyclerAdapter;
import com.han.hanmaxmin.common.widget.recyclerview.HeaderFooterRecyclerView;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanIFragment;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  10:36
 * @Description
 */

public interface HanIRecyclerFragment<D> extends HanIFragment {
    HanRecyclerAdapterItem<D> getRecyclerAdapterItem(LayoutInflater inflater, ViewGroup viewGroup, int type);

    int getHeaderLayout();

    int getFooterLayout();

    int getTopLayout();

    int getBottomLayout();

    HeaderFooterRecyclerView getRecyclerView();

    int getItemViewType(int position);

    void setData(List<D> list);

    void setData(List<D> list,boolean showEmptyView);

    void addData(List<D> list);

    void addData(D d);

    void addData(List<D> list, int position);

    List<D> getData();

    D getData(int position);

    void delete(int position);

    void delete(D d);

    void deleteAll();

    void updateAdapter(boolean showEmptyView);

    RecyclerView.Adapter onCreateAdapter();

    RecyclerView.Adapter getAdapter();

}
