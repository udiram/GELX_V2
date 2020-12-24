package com.gelx.gelx_v2.models;

import java.util.List;

public class ImageData {

    private int job_id;
    private String image;
    private List<XY> ladderPercents;
    private String email;
    private String fname;
    private String lname;
    private String user_id;


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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
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
