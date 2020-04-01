package com.pcy.memo.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pcy.memo.Adapter.CheckDelListViewAdapter;
import com.pcy.memo.Adapter.MemoDelGridViewAdapter;
import com.pcy.memo.Domain.CheckList_data_manager;
import com.pcy.memo.Domain.Memo_data_manager;
import com.pcy.memo.Model.CheckList_data;
import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.util.ArrayList;

public class CheckList_Delete extends AppCompatActivity {

    private View view;
    private TextView delOk, delCancel;
    private ListView lv_checkList;
    private CheckDelListViewAdapter checkDelListViewAdapter;
    private ArrayList<CheckList_data> checkListData;
    private CheckList_data_manager checkListDataManager;
    private ArrayList<Boolean> checkListDelCheck;
    private Context context;
    private int CHECKLIST_DEL = 5000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.checklist_del);
        context = CheckList_Delete.this;
        delOk = (TextView) findViewById(R.id.tv_del_ok) ;
        delCancel = (TextView) findViewById(R.id.tv_del_cancel);
        lv_checkList = (ListView) findViewById(R.id.lv_checkList);
        checkListDataManager = new CheckList_data_manager(context);
        checkListDelCheck = new ArrayList<>();

        checkListData = new ArrayList<CheckList_data>();

        LoadData();

        CreateCheckList();

        CheckListDeleteCheck();

        delOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                while (i < checkListData.size()){
                    if(checkListDelCheck.get(i)){
                        checkListDelCheck.remove(i);
                        checkListData.remove(i);
                    } else {
                        i ++;
                    }
                }

                SaveData();
                Intent intent = new Intent();
                setResult(CHECKLIST_DEL, intent);
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

    public void CreateCheckList() {

        checkDelListViewAdapter = new CheckDelListViewAdapter(context, checkListData);
        lv_checkList.setAdapter(checkDelListViewAdapter);

        lv_checkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv_del_check = (ImageView) view.findViewById(R.id.iv_del_check);
                if(checkListDelCheck.get(position)){
                    checkListDelCheck.set(position, false);
                    iv_del_check.setVisibility(View.INVISIBLE);
                } else {
                    checkListDelCheck.set(position, true);
                    iv_del_check.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void SaveData() {
        checkListDataManager.SaveCheckList(this.checkListData);
    }

    public void LoadData() {
        if(checkListDataManager.LoadCheckList() != null) {
            this.checkListData = checkListDataManager.LoadCheckList();
        }
    }

    public void CheckListDeleteCheck() {
        if (checkListData != null) {
            for (int i = 0; i < checkListData.size(); i++) {
                checkListDelCheck.add(false);
            }
        }
    }

}
