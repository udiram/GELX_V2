package com.gelx.gelx_v2.models;

import java.util.ArrayList;
import java.util.List;

public class NucData {

    private int column;
    private List<Float> peak_values;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Float> getData() {
        return peak_values;
    }

    public List<Integer> getDataAsInts() {

        List<Integer> integers = new ArrayList<Integer>();
        for (Float item : peak_values) {
            if (item > 0)
                integers.add(item.intValue());
        }
        return integers;

    }


    public void setData(List<Float> data) {
        this.peak_values = data;
    }




}
