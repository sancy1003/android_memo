package com.pcy.memo.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.DialogTitle;
import androidx.fragment.app.Fragment;

import com.pcy.memo.Adapter.CheckListViewAdapter;
import com.pcy.memo.Domain.CheckList_data_manager;
import com.pcy.memo.Model.CheckList_data;
import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import org.w3c.dom.CharacterData;

import java.util.ArrayList;

public class Checklist_frag extends Fragment {

    private View view;
    private ListView lv_checkList;
    private ArrayList<CheckList_data> listCheckData;
    private CheckListViewAdapter checkListViewAdapter;
    private ImageButton btn_delete, btn_checkList_write;
    private CheckList_data_manager checkListDataManager;
    private static int REQUEST_CODE=5301, CHECKLIST_DEL = 5000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.checklist_frag, container, false);

        lv_checkList = view.findViewById(R.id.lv_checkList);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_checkList_write = view.findViewById(R.id.btn_checkList_write);
        checkListDataManager = new CheckList_data_manager(getContext());
        listCheckData = new ArrayList<>();

        LoadData();
        CreateCheckList();

        btn_checkList_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteCheckList();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckList_Delete.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == CHECKLIST_DEL) {
            LoadData();
            CreateCheckList();
        }
    }

    public void CreateCheckList() {
        checkListViewAdapter = new CheckListViewAdapter(getContext(), listCheckData);
        lv_checkList.setAdapter(checkListViewAdapter);

        lv_checkList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteAlert(position);
                return true;
            }
        });
    }

    public void WriteCheckList() {
        final EditText et = new EditText(getContext());
        et.setSingleLine();
        et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
        final FrameLayout container = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 40;
        params.leftMargin = 60;
        params.rightMargin = 60;
        et.setLayoutParams(params);
        container.addView(et);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("체크리스트 추가");
        builder.setView(container);
        builder.setPositiveButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("추가",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CheckList_data checkListData = new CheckList_data();
                        checkListData.setContent(et.getText().toString());
                        checkListData.setCheck(false);
                        listCheckData.add(checkListData);
                        SaveData();
                        CreateCheckList();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();



        Button text = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        text.setTextColor(getResources().getColor(R.color.alertText));

        Button text1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        text1.setTextColor(getResources().getColor(R.color.alertText));
    }

    public void DeleteAlert(final int delPos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("체크리스트 지우기");
        builder.setMessage("한번 지운 체크리스트는 복구할 수 없어요. \n그래도 지우시겠어요?");
        builder.setPositiveButton("아니요",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listCheckData.remove(delPos);
                        SaveData();
                        CreateCheckList();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();

        Button text = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        text.setTextColor(getResources().getColor(R.color.alertText));

        Button text1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        text1.setTextColor(getResources().getColor(R.color.alertText));
    }

    public void SaveData() {
        checkListDataManager.SaveCheckList(this.listCheckData);
    }

    public void LoadData() {
        if(checkListDataManager.LoadCheckList() != null) {
            this.listCheckData = checkListDataManager.LoadCheckList();
        }
    }

    public void clickCheckBox(int position, boolean isChecked) {
        //getContext().getSharedPreferences("checkListData", Context.MODE_PRIVATE).edit().putBoolean(Integer.toString(position), isChecked).commit();
        CheckList_data checkListData = new CheckList_data();
        checkListData = listCheckData.get(position);
        checkListData.setCheck(isChecked);

        listCheckData.remove(position);
        listCheckData.add(position, checkListData);
        SaveData();
        CreateCheckList();
    }
}
