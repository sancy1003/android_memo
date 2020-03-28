package com.pcy.memo.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pcy.memo.Domain.Memo_data_manager;
import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.util.ArrayList;

public class Memo_detail extends AppCompatActivity {
    private String memoText;
    private int position, colorNum;
    private TextView tv_detail_memo;
    private ImageButton btn_memo_detail_edit, btn_memo_detail_delete, btn_detail_back;
    private int REQUEST_EDIT = 2001, MEMO_EDIT = 2000, MEMO_RETURN=2002;
    private ArrayList<Memo_data> listMemoData;
    private Memo_data_manager memoDataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_detail_view);

        tv_detail_memo = (TextView) findViewById(R.id.tv_detail_memo);
        btn_memo_detail_edit = (ImageButton) findViewById(R.id.btn_memo_detail_edit);
        btn_memo_detail_delete = (ImageButton) findViewById(R.id.btn_memo_detail_delete);
        btn_detail_back = (ImageButton) findViewById(R.id.btn_detail_back);
        memoDataManager = new Memo_data_manager(getApplicationContext());


        final Intent intent = getIntent();
        //overridePendingTransition(0, 0);

        listMemoData = (ArrayList<Memo_data>) intent.getSerializableExtra("listMemoData");
        position = intent.getExtras().getInt("position");

        Memo_data memoData = new Memo_data();
        memoData = listMemoData.get(position);

        memoText = memoData.getMemo();
        tv_detail_memo.setText(memoText);
        colorNum = memoData.getColor();

        btn_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("listMemoData", listMemoData);
                setResult(MEMO_RETURN, intent);
                finish();
            }
        });

        btn_memo_detail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAlert(position);
            }
        });

        btn_memo_detail_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Memo_detail.this, Memo_edit.class);
                intent.putExtra("colorNum", colorNum);
                intent.putExtra("memo", memoText);
                startActivityForResult(intent, REQUEST_EDIT);
                /*
                intent.putExtra("listMemoData", listMemoData);
                intent.putExtra("position", position);
                 */
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_EDIT && resultCode == MEMO_EDIT){
            /*
            String resultText = data.getStringExtra("editText");
            listMemoData = (ArrayList<Memo_data>) data.getSerializableExtra("listMemoData");

            tv_detail_memo.setText(resultText);

             */
            String resultText = data.getStringExtra("memo");
            colorNum = data.getIntExtra("colorNum", 0);
            tv_detail_memo.setText(resultText);

            Memo_data memoData = new Memo_data();
            memoData.setMemo(resultText);
            memoData.setColor(colorNum);

            listMemoData.remove(position);
            listMemoData.add(position, memoData);
            SaveData();
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("메모 지우기");
        builder.setMessage("정말 메모를 지우시나요?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listMemoData.remove(delPos);
                        Intent intent = new Intent();
                        intent.putExtra("listMemoData", listMemoData);
                        setResult(MEMO_RETURN, intent); // 결과값 설정
                        finish(); // 현재 액티비티 종료
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
