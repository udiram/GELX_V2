package com.gelx.gelx_v2.models;

import java.util.ArrayList;
import java.util.List;

public class LaneData {

    private int column;
    private List<Integer> data;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Integer> getData() {
        return data;
    }

    public List<Integer> getDataAsInts() {

        List<Integer> integers = new ArrayList<Integer>();
        for (Integer item : data) {
            if (item > 0)
                integers.add(item.intValue());
        }
        return integers;

    }

    public void setData(List<Integer> data) {
        this.data = data;
    }




}
