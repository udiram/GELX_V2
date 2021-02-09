package com.gelx.gelx_v2.ui.dashboard;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gelx.gelx_v2.PermanentStorage;
import com.gelx.gelx_v2.models.LadderData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public static void saveLadderData(Context context, List<LadderData> ladderDataList){
       String ladderString = new Gson().toJson(ladderDataList);
       PermanentStorage.getInstance().storeString(context, PermanentStorage.LADDER_KEY, ladderString);

        Log.i("ladderstore", ladderString);
    }

    public static List<LadderData> retrieveLadderData(Context context){
        String ladderString = PermanentStorage.getInstance().retrieveString(context, PermanentStorage.LADDER_KEY);
        if (ladderString.isEmpty()){
            return new ArrayList<LadderData>();
        }
        List<LadderData> ladderDataList = new Gson().fromJson(ladderString, new TypeToken<List<LadderData>>(){}.getType());
        return ladderDataList;
    }


    public LiveData<String> getText() {
        return mText;
    }
}