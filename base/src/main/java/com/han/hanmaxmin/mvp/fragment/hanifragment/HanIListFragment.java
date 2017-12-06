package com.han.hanmaxmin.mvp.fragment.hanifragment;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.han.hanmaxmin.mvp.fragment.HanIFragment;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/22  21:20
 * @Description
 */

public interface HanIListFragment<D> extends HanIFragment {
    int getHeaderLayout();

    int getFootLayout();

    int getTopLayout();

    int getBottomLayout();

    ListView getListView();

    int getViewTypeCount();

    int getItemViewType(int position);

    int getListAdapterItem(int type);//待完善>>>

    void setData(List<D> list);

    void setData(List<D> list,boolean showEmptyView);

    void addData(List<D> list);

    void delete(int position);

    void deleteAll();

    List<D> getData();

    void updateAdapter(boolean showEmptyView);

    BaseAdapter onCreateAdapter();

    BaseAdapter getAdapter();






}
