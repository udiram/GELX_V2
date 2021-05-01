package com.gelx.gelx_v2.ui.home;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gelx.gelx_v2.R;
import com.gelx.gelx_v2.models.LadderData;
import com.gelx.gelx_v2.models.LaneData;

import java.util.ArrayList;
import java.util.List;


public class LaneDataAdapter extends RecyclerView.Adapter<LaneDataAdapter.ViewHolder> {
    private static final String TAG = "LaneDataAdapter";
    protected static List<LaneData> mDataSet;

    @NonNull
    @Override
    public LaneDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LaneDataAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

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
//                    mDataSet.get(getAdapterPosition()).setLadderPoints(points);
//                    mDataSet.get(getAdapterPosition()).setLadderPointsAsString(ladderPoints);
//
//                    saveLDCallback.onSavePressed(mDataSet);
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
}
