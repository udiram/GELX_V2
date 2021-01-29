package com.gelx.gelx_v2.models;

import java.util.List;

public class LadderData {
    private int ladderNumberID;
    private List<Integer> ladderPoints;
    private String ladderPointsAsString;

    public LadderData(int ladderNumberID, List<Integer> ladderPoints) {
        this.ladderNumberID = ladderNumberID;
        this.ladderPoints = ladderPoints;
    }



    public int getLadderNumberID() {
        return ladderNumberID;
    }

    public void setLadderNumberID(int ladderNumberID) {
        this.ladderNumberID = ladderNumberID;
    }

    public List<Integer> getLadderPoints() {
        return ladderPoints;
    }

    public void setLadderPoints(List<Integer> ladderPoints) {
        this.ladderPoints = ladderPoints;
    }

    public String getLadderPointsAsString() {
        return ladderPointsAsString;
    }

    public void setLadderPointsAsString(String ladderPointsAsString) {
        this.ladderPointsAsString = ladderPointsAsString;
    }
}
