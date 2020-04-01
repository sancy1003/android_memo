package com.pcy.memo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcy.memo.Model.CheckList_data;
import com.pcy.memo.R;
import com.pcy.memo.View.MainActivity;

import java.util.ArrayList;

public class CheckDelListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CheckList_data> data;

    public CheckDelListViewAdapter(Context context, ArrayList<CheckList_data> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CheckList_data getItem(int i) {
        return data.get(i);
    }

    /*
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

     */

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        context = parent.getContext();

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_listview_del_checklist, parent, false);
        //}

        TextView tv_checkList = convertView.findViewById(R.id.tv_checkList);

        CheckList_data checkListData = data.get(position);

        tv_checkList.setText(checkListData.getContent());

        return convertView;
    }
}

