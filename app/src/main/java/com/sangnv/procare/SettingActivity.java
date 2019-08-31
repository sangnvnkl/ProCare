package com.sangnv.procare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.sangnv.procare.Model.ItemRow;
import com.sangnv.procare.ui.setting.SettingAdapter;
import com.sangnv.procare.ui.setting.SettingFragment;

public class SettingActivity extends AppCompatActivity implements SettingFragment.OnSettingFragmentListener {
    private static final String TAG = SettingActivity.class.getName();
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        settingFragment = SettingFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, settingFragment)
                    .commitNow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public void onItemEdit(String item) {
        Log.i(TAG, "onItemEdit: " + item);
    }

    @Override
    public void onRefreshSwipe() {
        settingFragment.refreshItem();
    }

    @Override
    public void onAddItem(String item) {
        settingFragment.addItem(item);
    }

    @Override
    public void onRemoveItem(int position) {
        settingFragment.removeItem(position);
    }
}
