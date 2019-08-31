package com.sangnv.procare.ui.setting;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sangnv.procare.R;
import com.sangnv.procare.utils.AppState;
import com.sangnv.procare.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class SettingFragment extends Fragment {
    private static final String TAG = SettingFragment.class.getName();

    private SettingViewModel mViewModel;
    private Context context;
    private View mView;
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private SettingAdapter settingAdapter;
    private OnSettingFragmentListener mListener;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.setting_fragment, container, false);
        recyclerView = mView.findViewById(R.id.list_setting);
        context = mView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        editText = mView.findViewById(R.id.editText);
        button = mView.findViewById(R.id.button_them);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bang = editText.getText().toString();
                Log.i(TAG, "onClick them: bang danh gia " + bang);
                addItem(bang);
            }
        });
        return mView;
    }

    public void addItem(String item) {
        AppState.getInstance().addBang(item);
        settingAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        AppState.getInstance().removeBang(position);
        settingAdapter.notifyDataSetChanged();
    }

    public void refreshItem() {
        settingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);

        // TODO: Use the ViewModel
        settingAdapter = new SettingAdapter(AppState.getInstance().getDanhSachBang(), mListener);
        setupRecyclerView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingFragmentListener) {
            mListener = (OnSettingFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSettingFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSettingFragmentListener {
        void onItemEdit(String item);

        //sang them ham xu ly refresh
        void onRefreshSwipe();

        void onAddItem(String item);

        void onRemoveItem(int position);
    }

    private void setupRecyclerView() {
        //duong ngan cach 2 item
        DividerItemDecoration itemDecor = new DividerItemDecoration(context, VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(settingAdapter);
    }
}
