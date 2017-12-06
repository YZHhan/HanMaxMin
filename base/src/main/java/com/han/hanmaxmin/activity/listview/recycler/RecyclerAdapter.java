package com.han.hanmaxmin.activity.listview.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.hanmaxmin.R;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/22  22:09
 * @Description __________onCreateViewHolder
 * __________onBindViewHolder
 * __________getItemCount
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHodler> {
    private Context        context;
    private List<String>   list;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<String> list) {
        super();
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override public RecyclerViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler_adapter, null);
        RecyclerViewHodler viewHodler = new RecyclerViewHodler(view);
        return viewHodler;
    }

    @Override public void onBindViewHolder(RecyclerViewHodler holder, int position) {
        holder.rv_recycler_name.setText(list.get(position));
    }


    @Override public int getItemCount() {
        return list.size();
    }


    class RecyclerViewHodler extends RecyclerView.ViewHolder {
        private TextView rv_recycler_name;

        public RecyclerViewHodler(View itemView) {
            super(itemView);
            rv_recycler_name = (TextView) itemView.findViewById(R.id.rv_recycler_name);
        }
    }
}
