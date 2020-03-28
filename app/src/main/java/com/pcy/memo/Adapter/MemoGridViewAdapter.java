package com.pcy.memo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MemoGridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Memo_data> data;

    public MemoGridViewAdapter(Context context, ArrayList<Memo_data> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Memo_data getItem(int i) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        context = parent.getContext();

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_gridview_memo, parent, false);
        //}

        LinearLayout gvItem_memo = (LinearLayout) convertView.findViewById(R.id.gvItem_memo) ;
        TextView tv_memo = (TextView) convertView.findViewById(R.id.tv_memo);

        Memo_data memo_data = data.get(position);

        switch (memo_data.getColor()) {
            case 0 :
                gvItem_memo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.post_blue));
                break;
            case 1 :
                gvItem_memo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.post_green));
                break;
            case 2 :
                gvItem_memo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.post_purple));
                break;
            case 3 :
                gvItem_memo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.post_red));
                break;
            case 4 :
                gvItem_memo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.post_yellow));
                break;
        }

        tv_memo.setText(memo_data.getMemo());

        return convertView;
    }
}
