package com.pcy.memo.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pcy.memo.Model.Memo_data;
import com.pcy.memo.R;

import java.util.ArrayList;

public class Memo_edit extends AppCompatActivity {

    private EditText et_memo_write;
    private String memo;
    private Button btn_write_write;
    private ImageButton btn_write_back;
    private ImageButton btn_blue, btn_green, btn_red, btn_yellow;
    private ImageView iv_check_blue, iv_check_green, iv_check_red, iv_check_yellow;
    private ArrayList<Memo_data> listMemoData = new ArrayList<>();
    private int colorNum = 0, MEMO_EDIT = 2000;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_write);
        overridePendingTransition(0, 0);

        et_memo_write = (EditText)findViewById(R.id.et_memo_write);
        btn_write_write = (Button) findViewById(R.id.btn_write_write);
        btn_write_back = (ImageButton) findViewById(R.id.btn_write_back);
        btn_blue = (ImageButton) findViewById(R.id.btn_blue);
        btn_green = (ImageButton) findViewById(R.id.btn_green);
        btn_red = (ImageButton) findViewById(R.id.btn_red);
        btn_yellow = (ImageButton) findViewById(R.id.btn_yellow);
        btn_yellow = (ImageButton) findViewById(R.id.btn_yellow);
        iv_check_blue = (ImageView) findViewById(R.id.iv_check_blue);
        iv_check_green = (ImageView) findViewById(R.id.iv_check_green);
        iv_check_red = (ImageView) findViewById(R.id.iv_check_red);
        iv_check_yellow = (ImageView) findViewById(R.id.iv_check_yellow);

        btn_write_write.setText("수정하기");

        Intent intent = getIntent();

        /*
        listMemoData = (ArrayList<Memo_data>) intent.getSerializableExtra("listMemoData");
        position = intent.getIntExtra("position", 0);
         */

        memo = intent.getStringExtra("memo");
        colorNum = intent.getIntExtra("colorNum",0);
        CheckImageManager(colorNum);

        et_memo_write.setText(memo);


        /*
        Memo_data memoData = new Memo_data();
        memoData = listMemoData.get(position);

        colorNum = memoData.getColor();
        et_memo_write.setText(memoData.getMemo());

         */

        btn_write_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_memo_write.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    /*
                    Memo_data memoData = new Memo_data();
                    memoData.setColor(colorNum);
                    memoData.setMemo(et_memo_write.getText().toString());

                    listMemoData.remove(position);
                    listMemoData.add(position, memoData);

                    Intent intent = new Intent();
                    intent.putExtra("editText", et_memo_write.getText().toString());
                    intent.putExtra("listMemoData", listMemoData);
                     */
                    Intent intent = new Intent();
                    intent.putExtra("memo", et_memo_write.getText().toString());
                    intent.putExtra("colorNum", colorNum);
                    setResult(MEMO_EDIT, intent); // 결과값 설정
                    finish(); // 현재 액티비티 종료
                    overridePendingTransition(0, 0);
                }
            }
        });

        btn_write_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorNum = 0;
                CheckImageManager(colorNum);
            }
        });

        btn_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorNum = 1;
                CheckImageManager(colorNum);
            }
        });

        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorNum = 2;
                CheckImageManager(colorNum);
            }
        });

        btn_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorNum = 3;
                CheckImageManager(colorNum);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void CheckImageManager(int colorNum) {
        switch (colorNum) {
            case 0:
                iv_check_blue.setVisibility(View.VISIBLE);
                iv_check_green.setVisibility(View.INVISIBLE);
                iv_check_red.setVisibility(View.INVISIBLE);
                iv_check_yellow.setVisibility(View.INVISIBLE);
                break;
            case 1:
                iv_check_blue.setVisibility(View.INVISIBLE);
                iv_check_green.setVisibility(View.VISIBLE);
                iv_check_red.setVisibility(View.INVISIBLE);
                iv_check_yellow.setVisibility(View.INVISIBLE);
                break;
            case 2:
                iv_check_blue.setVisibility(View.INVISIBLE);
                iv_check_green.setVisibility(View.INVISIBLE);
                iv_check_red.setVisibility(View.VISIBLE);
                iv_check_yellow.setVisibility(View.INVISIBLE);
                break;
            case 3:
                iv_check_blue.setVisibility(View.INVISIBLE);
                iv_check_green.setVisibility(View.INVISIBLE);
                iv_check_red.setVisibility(View.INVISIBLE);
                iv_check_yellow.setVisibility(View.VISIBLE);
                break;
        }
    }
}
