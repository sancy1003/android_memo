package com.pcy.memo.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pcy.memo.Adapter.MemoGridViewAdapter;
import com.pcy.memo.Domain.Memo_data_manager;
import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Memo_frag extends Fragment {

    private View view;
    private ImageButton btn_memo_write;
    private GridView gv_memo;
    private MemoGridViewAdapter memoGridViewAdapter;
    private ArrayList<Memo_data> listMemoData;
    private Memo_data_manager memoDataManager;
    private static final int REQUEST_CODE = 5302, MEMO_EDIT = 2000, MEMO_RETURN=2002;
    private boolean delResult = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memo_frag, container, false);
        btn_memo_write = view.findViewById(R.id.btn_memo_write);
        gv_memo = (GridView) view.findViewById(R.id.gv_memo);
        memoDataManager = new Memo_data_manager(getContext());

        listMemoData = new ArrayList<Memo_data>();

        LoadData();

        CreateMemo(listMemoData);

        btn_memo_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Memo_write.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return  view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            String resultText = data.getStringExtra("memo");
            int colorNum = data.getIntExtra("color", 0);
            Memo_data memo = new Memo_data();
            memo.setMemo(resultText);
            memo.setColor(colorNum);
            listMemoData.add(memo);
            SaveData();
            CreateMemo(listMemoData);
        } else if(requestCode == REQUEST_CODE && resultCode == MEMO_RETURN){
            listMemoData = (ArrayList<Memo_data>) data.getSerializableExtra("listMemoData");
            SaveData();
            CreateMemo(listMemoData);
        }
    }

    public void CreateMemo(final ArrayList<Memo_data> listMemoData) {
        memoGridViewAdapter = new MemoGridViewAdapter(getContext(), listMemoData);
        gv_memo.setAdapter(memoGridViewAdapter);

        gv_memo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Memo_data memoData = new Memo_data();
                memoData = (Memo_data)parent.getAdapter().getItem(position);

                Intent intent = new Intent(getActivity(), Memo_detail.class);
                intent.putExtra("listMemoData", listMemoData);
                intent.putExtra("position", position);
                intent.putExtra("memo", memoData.getMemo());
                intent.putExtra("color", memoData.getColor());

                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        gv_memo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteAlert(position);
                return true;
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

    public void DeleteAlert(final int delPos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("메모 지우기");
        builder.setMessage("정말 메모를 지우시나요?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listMemoData.remove(delPos);
                        SaveData();
                        CreateMemo(listMemoData);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

}
