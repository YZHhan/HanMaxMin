package com.han.hanmaxmin.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.han.hanmaxmin.R;

import java.util.HashMap;
import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/20  14:09
 * @Description
 */

public class ListViewAdapter extends BaseAdapter {
    private List<String>   list;
    private LayoutInflater inflater;
        public static HashMap<Integer,Boolean> isCheck;

    public ListViewAdapter(List<String> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        isCheck = new HashMap<Integer,Boolean>();
        for(int i = 0;i< list.size();i++){
            isCheck.put(i,false);
            setIsCheck(isCheck);
        }
    }



    @Override public int getCount() {
        return list.size();
    }

    @Override public Object getItem(int position) {
        return list.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_adapter, null);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.adapter_checkbox);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.adapter_textView);
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setTag(position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.checkBox.setTag(position);
        }
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if(getIsCheck().get(position)){
                    isCheck.put(position, false);
                    setIsCheck(isCheck);
                }else{
                    isCheck.put(position, true);
                    setIsCheck(isCheck);
                }
            }
        });
        if(getIsCheck().get(position)){
            viewHolder.checkBox.setChecked(true);
        }else{
            viewHolder.checkBox.setChecked(false);
        }

        return convertView;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView textView;
    }

    public static HashMap<Integer,Boolean> getIsCheck(){
        return ListViewAdapter.isCheck;
    }
    public static void setIsCheck(HashMap<Integer,Boolean> isCheck){
        ListViewAdapter.isCheck = isCheck;
    }
}
