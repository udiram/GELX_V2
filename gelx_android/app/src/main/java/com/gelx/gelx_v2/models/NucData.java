package com.gelx.gelx_v2.models;

import java.util.ArrayList;
import java.util.List;

public class NucData {

    private int column;
    private List<Integer> peak_values;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Integer> getData() {
        return peak_values;
    }


    public void setData(List<Integer> data) {
        this.peak_values = data;
    }




}
