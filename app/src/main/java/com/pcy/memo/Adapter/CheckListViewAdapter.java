package com.pcy.memo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.pcy.memo.Model.CheckList_data;
import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.util.ArrayList;

public class CheckListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CheckList_data> data;

    public interface ListCheckBoxClickListener {
        void onListCheckBoxClick(int position);
    }

    private ListCheckBoxClickListener listCheckBoxClickListener;

    public CheckListViewAdapter(Context context, ArrayList<CheckList_data> data, ListCheckBoxClickListener listCheckBoxClickListener) {
        this.context = context;
        this.data = data;
        this.listCheckBoxClickListener = listCheckBoxClickListener;
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
        convertView = inflater.inflate(R.layout.item_listview_checklist, parent, false);
        //}

        CheckBox cb_listView = convertView.findViewById(R.id.cb_listView);
        TextView tv_checkList = convertView.findViewById(R.id.tv_checkList);

        CheckList_data checkListData = data.get(position);

        tv_checkList.setText(checkListData.getContent());

        if(checkListData.getCheck())
        {
            cb_listView.setChecked(true);
        } else {
            cb_listView.setChecked(false);
        }

        cb_listView.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                listCheckBoxClickListener.onListCheckBoxClick(position);
            }
        });

        return convertView;
    }
}

