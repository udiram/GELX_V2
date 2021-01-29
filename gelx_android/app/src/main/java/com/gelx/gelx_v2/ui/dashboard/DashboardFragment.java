package com.gelx.gelx_v2.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.models.LadderData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int DATASET_COUNT = 1;

    private DashboardViewModel dashboardViewModel;
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<LadderData> mDataset;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataset.add(new LadderData(mDataset.size(), null));
                mAdapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fabdelete = root.findViewById(R.id.fabdelete);
        fabdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataset.remove(mDataset.size() - 1);
                mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new CustomAdapter(mDataset);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    private void initDataset() {
        mDataset = new ArrayList<LadderData>();
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset.add(new LadderData(i, null) );
        }
    }
}