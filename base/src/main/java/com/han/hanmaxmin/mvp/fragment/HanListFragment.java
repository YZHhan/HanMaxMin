package com.han.hanmaxmin.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.InnerScroller;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.InnerScrollerContainer;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.OuterScroller;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  16:31
 * @Description  继承Fragment的顶级父类。HanFragment,
 */

public abstract class HanListFragment<P extends HanPresenter, D> extends HanFragment<P> implements HanIListFragment<D>, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener,InnerScrollerContainer {

    protected  final List<D> mList = new ArrayList<>();

    private ListView mListView;
    private BaseAdapter mListAdapter;
    private View headerView;
    private View footerView;


    /**
     * listView 的布局
     */
    @Override
    public int layoutId() {return R.layout.han_fragment_listview;}

    @Override
    public int getHeaderLayout() {return 0;}

    @Override public int getFooterLayout() {
        return 0;
    }

    @Override public int getTopLayout() {
        return 0;
    }

    @Override public int getBottomLayout() {
        return 0;
    }

    @Override
    public ListView getListView() {return mListView;}

    @Override
    public int getViewTypeCount() {return 1;}

    @Override
    public int getItemViewType(int position) {return 0;}

    @Override
    protected View initView(LayoutInflater inflater) {
        View rootView = super.initView(inflater);//  super 调用父类的方法
        if(getTopLayout() > 0 || getBottomLayout() >0) initTopBottomView(rootView, inflater);
        initListView(inflater, rootView);
        return rootView;
    }

    /**
     * 初始化ListView
     */
    protected  void initListView(LayoutInflater inflater, View view){
        if(view instanceof ListView){
            mListView = (ListView) view;
        } else {
            mListView = (ListView) view.findViewById(android.R.id.list);
        }
        if(mListView == null) throw new RuntimeException("ListView is not exit or its id not 'android.R.id.list' in current layout !");
        if(getHeaderLayout() != 0){
            headerView = inflater.inflate(getHeaderLayout(), null);
            mListView.addHeaderView(headerView);
        }
        if(getFooterLayout() != 0){
            footerView = inflater.inflate(getFooterLayout(), null);
            mListView.addFooterView(footerView);
        }
        mListView.setOnItemClickListener(this);
        mListView.setOnItemClickListener(this);
        BaseAdapter adapter = onCreateAdapter();
        if(adapter != null){
                mListAdapter = adapter;
        } else {
            mListAdapter = new MyAdapter();
        }
        mListView.setAdapter(mListAdapter);
        mListView.setOnScrollListener(this);
    }

    protected  void initTopBottomView(View rootView, LayoutInflater inflater){
        if(rootView instanceof LinearLayout){
            if(getTopLayout() > 0){
                ((LinearLayout) rootView).addView(inflater.inflate(getTopLayout(), null), 0);
            }
            if (getBottomLayout() > 0) {
                int childCount = ((LinearLayout) rootView).getChildCount();
                ((LinearLayout) rootView).addView(inflater.inflate(getBottomLayout(), null), childCount);
            }

        } else {
            L.e(initTag(), "layoutId() root must be LinearLayout when getTopLayout() or getBottomLayout() is overwrite !");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        L.i(initTag(), "onItemClick()... position : " + position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void setData(List<D> list) {
        setData(list, true);
    }

    @Override
    public void setData(List<D> list, boolean showEmptyView) {
        synchronized (mList){
            mList.clear();
            if(list != null && !list.isEmpty()) mList.addAll(list);
            updateAdapter(showEmptyView);
        }
    }

    @Override
    public void addData(D d) {
    if(d != null){
        synchronized (mList){
            mList.clear();
            updateAdapter(true);
            }
        }
    }

    @Override public void addData(List<D> list) {
        if (list != null && !list.isEmpty()) {
            synchronized (mList) {
                mList.addAll(list);
                updateAdapter(true);
            }
        }
    }

    @Override public void delete(int position) {
        synchronized (mList) {
            if (position >= 0 && position < mList.size()) {
                mList.remove(position);
                updateAdapter(true);
            }
        }
    }

    @Override public void delete(D d) {
        if (d == null) return;
        synchronized (mList) {
            mList.remove(d);
            updateAdapter(true);
        }
    }

    @Override public void deleteAll() {
        synchronized (mList) {
            mList.clear();
            updateAdapter(true);
        }
    }

    @Override
    public List<D> getData() {
        return mList;
    }

    @Override
    public D getData(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.get(position);
        }
        return null;
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override
    public void updateAdapter(boolean showEmptyView) {
        if(mListAdapter != null){
            mListAdapter.notifyDataSetChanged();
            if (mViewAnimator != null){//  调用父类的变量
                if(mList.isEmpty() && showEmptyView){
                    showEmptyView();
                } else {
                    showContentView();
                }
            }
        }
    }

    @Override
    public BaseAdapter onCreateAdapter() {
        return null;
    }

    @Override
    public BaseAdapter getAdapter() {
        return mListAdapter;
    }

    public View getHeaderView(){
        return headerView;
    }

    public View getFooterView(){
        return footerView;
    }


    /**
     * Adapter封装在Fragment中，
     * 如果需要重写{@link #onCreateAdapter()}
     */
    private final class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            if(position > getCount() -1)return null;
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
          return position;
        }

        /**
         * 一个神奇的操作，在适配器的方法里面，用HanListFragment  去调用{@link #getItemViewType(int)}
         * 在Fragment里面去控制adapter
         */
        @Override
        public int getItemViewType(int position) {
            return HanListFragment.this.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return HanListFragment.this.getViewTypeCount();
        }

        /**
         * 进行多布局的封装
         * 进行复用的封装
         *
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HanListAdapterItem item = null;// 声明Item的Adapter。把适配器的工作转给HanListAdapterItem。
            if(convertView == null){// 判断当前view
                int count = getViewTypeCount();// 得到当前布局的个数。
                if(count > 1){// 如果大于1，就是复杂布局。
                    int type = getItemViewType(position);//  得到当前布局的的type
                    item = getListAdapterItem(type);// 传入Fragment的抽象方法，getListAdapterItem返回的是HanListAdapterItem。type用作区分不同。
                } else {
                    item = getListAdapterItem(0);
                }
                convertView = LayoutInflater.from(getActivity()).inflate(item.getItemLayout(), null, false);// getItemLayout 可能为空
                item.init(convertView);//  进行适配器的ViewBind。
                convertView.setTag(item);
            }
            if(item == null) item = (HanListAdapterItem) convertView.getTag();
            if(item != null)item.bindData(getItem(position), position, getCount());//得到集合的一个元素，item的索引，item的count
            return convertView;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /*--------------------------------------以下是HeaderViewPager支持-----------------------------------*/


    protected OuterScroller mOuterScroller;
    protected int mIndex;

    @Override
    public void setMyOuterScroller(OuterScroller outerScroller, int myPosition) {
        mOuterScroller = outerScroller;
        mIndex = myPosition;
        if(mListView instanceof InnerScroller){
            L.i(initTag(), "注册调度控件: setMyOuterScroller()    position:" + myPosition);
            ((InnerScroller)mListView).register2Outer(mOuterScroller, mIndex);
        }
    }
}

















