package com.sangnv.procare.ui.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.sangnv.procare.App;
import com.sangnv.procare.R;
import com.sangnv.procare.utils.AppState;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {
    private static final String TAG = SettingAdapter.class.getName();
    private List<String> mValues;
    private final SettingFragment.OnSettingFragmentListener mListener;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public SettingAdapter(List<String> mValues, SettingFragment.OnSettingFragmentListener listener) {
        this.mValues = mValues;
        this.mListener = listener;
    }


    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setting_row, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        holder.mTitle.setText(mValues.get(position));

        if (mValues != null && 0 <= position && position < mValues.size()) {
            final String data = mValues.get(position);
            binderHelper.bind(holder.swipeLayout, data);
            // Bind your data here
            holder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    public void setmValues(List<String> mValues) {
        this.mValues = mValues;
        notifyDataSetChanged();
    }

    public List<String> getAllItem() {
        return mValues;
    }

    public void removeItem(int position) {
        AppState.getInstance().removeBang(position);
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        return mValues.get(position);
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        private SwipeRevealLayout swipeLayout;
        private ImageButton button_delete;
        private ImageButton button_edit;

        public SettingViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = view.findViewById(R.id.setting_text);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            button_delete = itemView.findViewById(R.id.btn_delete_item);
            button_edit = itemView.findViewById(R.id.btn_edit_item);
        }

        public void bind(final String data) {
            mTitle.setText(data);

            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(getAdapterPosition());
                    mListener.onRefreshSwipe();
//                    mListener.onRemoveItem(getAdapterPosition());
                }
            });

            button_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String displayText = "Edit item: " + data;
                    Toast.makeText(App.self().getApplicationContext(), displayText, Toast.LENGTH_SHORT).show();
                    mListener.onItemEdit(data);
                }
            });

        }
    }
}
