/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.gelx.gelx_v2.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gelx.gelx_v2.BuildConfig;
import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.models.LadderData;

import java.util.ArrayList;
import java.util.List;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    protected static List<LadderData> mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final EditText ladderpointsET;
        private final Button saveLPBtn;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");


                }
            });
            textView = (TextView) v.findViewById(R.id.textView);

            ladderpointsET = (EditText) v.findViewById(R.id.ladderpointsET);

            saveLPBtn = (Button) v.findViewById(R.id.saveLPBtn);
                saveLPBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ladderPoints = ladderpointsET.getText().toString();
                        ladderPoints = ladderPoints.replaceAll(" ","");
                        String[] ladderPointsList = ladderPoints.split(",");
                        List<Integer> points = new ArrayList<>();
                        for (int i = 0; i < ladderPointsList.length; i++) {
                            points.add(Integer.valueOf(ladderPointsList[i]));
                        }
                        mDataSet.get(getAdapterPosition()).setLadderPoints(points);
                        mDataSet.get(getAdapterPosition()).setLadderPointsAsString(ladderPoints);
                    }
                });
        }

        public TextView getTextView() {
            return textView;
        }

        public EditText getLadderpointsET() {
            return ladderpointsET;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<LadderData> dataSet) {
        mDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ladder_item, viewGroup, false);

        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(String.valueOf(mDataSet.get(position).getLadderNumberID()));
        viewHolder.getLadderpointsET().setText(mDataSet.get(position).getLadderPointsAsString());
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
