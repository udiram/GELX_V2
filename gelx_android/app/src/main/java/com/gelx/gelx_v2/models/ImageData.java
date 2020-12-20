package com.gelx.gelx_v2.models;

import java.util.List;

public class ImageData {

    private int job_id;
    private String image;
    private List<XY> ladderPercents;

    public List<XY> getLadderPercents() {
        return ladderPercents;
    }

    public void setLadderPercents(List<XY> ladderPercents) {
        this.ladderPercents = ladderPercents;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
