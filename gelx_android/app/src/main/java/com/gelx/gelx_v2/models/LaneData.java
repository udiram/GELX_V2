package com.gelx.gelx_v2.models;

import java.util.ArrayList;
import java.util.List;

public class LaneData {

    private int column;
    private List<Float> data;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Float> getData() {
        return data;
    }

    public List<Integer> getDataAsInts() {

        List<Integer> integers = new ArrayList<Integer>();
        for (Float item : data) {
            integers.add(item.intValue());
        }
        return integers;

    }

    public void setData(List<Float> data) {
        this.data = data;
    }




}
