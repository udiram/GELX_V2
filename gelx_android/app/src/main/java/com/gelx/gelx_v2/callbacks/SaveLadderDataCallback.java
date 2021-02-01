package com.gelx.gelx_v2.callbacks;

import com.gelx.gelx_v2.models.LadderData;

import java.util.List;

public interface SaveLadderDataCallback {
    void onSavePressed(List<LadderData> ladderDataList);
}
