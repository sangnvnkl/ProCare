package com.sangnv.procare;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sangnv.procare.Model.ItemRow;
import com.sangnv.procare.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_RSS_LINK = "arg_rss_link";
    // TODO: Customize parameters
    private String bang_danh_gia = null;
    private OnListFragmentInteractionListener mListener;
    Context context;
    View mView;
    TextView textView;
    RecyclerView recyclerView;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(String bang_danh_gia) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RSS_LINK, bang_danh_gia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            bang_danh_gia = getArguments().getString(ARG_RSS_LINK);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter cho RecyclerView

        context = mView.getContext();
        recyclerView = mView.findViewById(R.id.list);
        textView = mView.findViewById(R.id.txt_dlg);

        //duong ngan cach 2 item
        DividerItemDecoration itemDecor = new DividerItemDecoration(context, VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

//        //check sate internet
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

//        if (isConnected){
//            textView.setVisibility(View.GONE);
//            new ReadXML().execute(mTypeRSS);
//        } else {
//            if (MainActivity.inDex == 4)
//            {
//                textView.setVisibility(View.GONE);
//                new ReadXML().execute(mTypeRSS);
//            }
//            else
//            {
//                textView.setVisibility(View.VISIBLE);
//                // Sang thêm dòng dưới
//                recyclerView.setVisibility(View.INVISIBLE);
//                textView.setText("Không có kết nối internet");
//            }
//        }

        String danh_sach_tieu_chi = SharedPrefs.getInstance().get(bang_danh_gia, String.class);

        List<ItemRow> itemList = new Gson().fromJson(danh_sach_tieu_chi, new TypeToken<List<ItemRow>>() {
        }.getType());

        recyclerView.setAdapter(new RecyclerViewAdapter(itemList, mListener));

        mSwipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //lien ket sang main activity
                mListener.onRefreshSwipe();
            }
        });

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ItemRow item);

        //sang them ham xu ly refresh
        void onRefreshSwipe();

        //sang them ham xu ly click button xoa item
        void onItemChange();
    }
}
