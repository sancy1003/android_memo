package com.pcy.memo.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.pcy.memo.Adapter.MemoDelGridViewAdapter;
import com.pcy.memo.Adapter.MemoGridViewAdapter;
import com.pcy.memo.Domain.Memo_data_manager;
import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.util.ArrayList;

public class Memo_Delete extends AppCompatActivity {

    private View view;
    private TextView delOk, delCancel;
    private GridView gv_memo;
    private MemoDelGridViewAdapter memoDelGridViewAdapter;
    private ArrayList<Memo_data> listMemoData;
    private Memo_data_manager memoDataManager;
    private boolean delResult = false;
    private ArrayList<Boolean> memoDelCheck;
    private Context context;
    private int MEMO_DEL = 2005;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.memo_del);
        context = Memo_Delete.this;
        delOk = (TextView) findViewById(R.id.tv_del_ok) ;
        delCancel = (TextView) findViewById(R.id.tv_del_cancel);
        gv_memo = (GridView) findViewById(R.id.gv_memo);
        memoDataManager = new Memo_data_manager(context);
        memoDelCheck = new ArrayList<>();

        listMemoData = new ArrayList<Memo_data>();

        LoadData();

        CreateMemo(listMemoData);

        MemoDeleteCheck();

        delOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                while (i < listMemoData.size()){
                    if(memoDelCheck.get(i)){
                        memoDelCheck.remove(i);
                        listMemoData.remove(i);
                    } else {
                        i ++;
                    }
                }

                SaveData();
                Intent intent = new Intent();
                setResult(MEMO_DEL, intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        delCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void CreateMemo(final ArrayList<Memo_data> listMemoData) {

        memoDelGridViewAdapter = new MemoDelGridViewAdapter(context, listMemoData);
        gv_memo.setAdapter(memoDelGridViewAdapter);

        gv_memo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv_del_check = (ImageView) view.findViewById(R.id.iv_del_check);
                if(memoDelCheck.get(position)){
                    memoDelCheck.set(position, false);
                    iv_del_check.setVisibility(View.INVISIBLE);
                } else {
                    memoDelCheck.set(position, true);
                    iv_del_check.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void SaveData() {
        memoDataManager.SaveMemo(this.listMemoData);
    }

    public void LoadData() {
        if(memoDataManager.LoadMemo() != null) {
            this.listMemoData = memoDataManager.LoadMemo();
        }
    }

    public void MemoDeleteCheck() {
        if (listMemoData != null) {
            for (int i = 0; i < listMemoData.size(); i++) {
                memoDelCheck.add(false);
            }
        }
    }

}
