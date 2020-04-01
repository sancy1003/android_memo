package com.pcy.memo.Domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcy.memo.Model.CheckList_data;
import com.pcy.memo.Model.Memo_data;

import java.util.ArrayList;

public class CheckList_data_manager {

    private Context context;
    private CheckList_data checkListData;
    private ArrayList<CheckList_data> listCheckData = new ArrayList<>();

    public CheckList_data_manager(Context context) {
        this.context = context;
    }

    public void SaveCheckList(ArrayList<CheckList_data> listCheckData) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        Gson gson = new Gson();
        String saveCheck = gson.toJson(listCheckData);
        prefsEditor.putString("checkList", saveCheck);
        prefsEditor.commit();
    }

    public ArrayList<CheckList_data> LoadCheckList(){
        ArrayList<CheckList_data> listCheckData = new ArrayList<CheckList_data>();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String loadCheck = sharedPrefs.getString("checkList" , "");
        listCheckData = gson.fromJson(loadCheck, new TypeToken<ArrayList<CheckList_data>>(){}.getType());
        return listCheckData;
    }
}
