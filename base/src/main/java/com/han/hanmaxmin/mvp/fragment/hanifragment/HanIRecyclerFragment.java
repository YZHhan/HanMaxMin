package com.han.hanmaxmin.mvp.fragment.hanifragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.han.hanmaxmin.mvp.fragment.HanIFragment;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  10:36
 * @Description
 */

public interface HanIRecyclerFragment<D> extends HanIFragment {
    int getRecyclerAdapterItem(LayoutInflater inflater,ViewGroup viewGroup,int type);//待完善

    int getHeaderLayout();

    int getFooterLayout();

    int getTopLayout();

    int getBottomLayout();

    int getRecyclerView();//待完善

    void setData(List<D> list);

    void setData(List<D> list,boolean showEmptyView);

    List<D> getData();

    void addData(List<D> list);

    void addData(List<D> list, int position);

    void delete(int position);

    void deleteAll();

    void updateAdapter(boolean showEmptyView);

    RecyclerView.Adapter onCreateAdapter();

    RecyclerView.Adapter getAdapter();

}
