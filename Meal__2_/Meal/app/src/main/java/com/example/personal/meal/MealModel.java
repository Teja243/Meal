package com.example.personal.meal;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "MealDetails")
class MealModel {
    @PrimaryKey @NonNull
    public String sid;
    public String sname;
    public String simage;
    public String sdesc;


    public MealModel(@NonNull String sid, String sname, String simage, String sdesc) {
        this.sid = sid;
        this.sname = sname;
        this.simage = simage;
        this.sdesc = sdesc;
    }
    @Ignore
    public MealModel() {

    }

    @NonNull
    public String getSid() {
        return sid;
    }

    public void setSid(@NonNull String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public String getSdesc() {
        return sdesc;
    }

    public void setSdesc(String sdesc) {
        this.sdesc = sdesc;
    }
}
