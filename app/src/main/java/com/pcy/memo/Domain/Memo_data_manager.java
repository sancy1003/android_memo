package com.pcy.memo.Domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcy.memo.Model.Memo_data;

import java.util.ArrayList;
import java.util.List;

public class Memo_data_manager {

    private Context context;
    private Memo_data memo_data;
    private ArrayList<Memo_data> listMemoData = new ArrayList<>();

    public Memo_data_manager(Context context) {
        this.context = context;
    }

    public void SaveMemo(ArrayList<Memo_data> listMemoData) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        Gson gson = new Gson();
        String saveMemo = gson.toJson(listMemoData);
        prefsEditor.putString("memo", saveMemo);
        prefsEditor.commit();
    }

    public ArrayList<Memo_data> LoadMemo(){
        ArrayList<Memo_data> listMemoData = new ArrayList<Memo_data>();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String loadScadule = sharedPrefs.getString("memo" , "");
        listMemoData = gson.fromJson(loadScadule, new TypeToken<ArrayList<Memo_data>>(){}.getType());
        return listMemoData;
    }
}
