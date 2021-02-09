package com.gelx.gelx_v2.models;

import androidx.annotation.VisibleForTesting;

import java.util.List;

public class ImageData {

    private int job_id;
    private String image;
    private List<XY> ladderPercents;
    private String email;
    private String username;
    private String lname;
    private String fname;
    private String user_id;
    private String password;
    private List<LadderData> ladderData;


    public List<LadderData> getLadderData() {
        return ladderData;
    }

    public void setLadderData(List<LadderData> ladderData) {
        this.ladderData = ladderData;
    }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }



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
