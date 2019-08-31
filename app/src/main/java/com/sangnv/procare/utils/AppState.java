package com.sangnv.procare.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sangnv.procare.App;
import com.sangnv.procare.R;

import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static final String TAG = AppState.class.getName();
    private static final AppState ourInstance = new AppState();
    private String mType;
    private List<String> strings;

    public static AppState getInstance() {
        return ourInstance;
    }

    private AppState() {
        this.mType = App.self().getResources().getString(R.string.key_danh_gia);
        this.strings = getList(this.mType);
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
        this.strings = getList(this.mType);
    }

    public List<String> getDanhSachBang() {
        return strings;
    }

    public List<String> getList(String key) {
        String string = SharedPrefs.getInstance().get(key, String.class);
        Log.i(TAG, String.format("get bảng %s: %s", key, string));
        List<String> stringList = new Gson().fromJson(string, new TypeToken<List<String>>() {
        }.getType());

        if (stringList == null) {
            stringList = new ArrayList<>();
        }
        return stringList;
    }

    public void addBang(String bang) {
        strings.add(bang);
        updateShare();
    }

    public void removeBang(int position) {
        strings.remove(position);
        updateShare();
    }

    public void removeAll(List<String> list) {
        strings.removeAll(list);
        updateShare();
    }

    public void updateShare(){
        SharedPrefs.getInstance().put(mType, new Gson().toJson(strings));
    }
}
