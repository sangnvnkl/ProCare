package com.sangnv.procare.Model;

import com.google.gson.annotations.SerializedName;

public class ItemRow {
    @SerializedName("title")
    private String title;
    @SerializedName("seek_min")
    private int seek_min;
    @SerializedName("seek_max")
    private int seek_max;
    @SerializedName("value")
    private int value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeek_min() {
        return seek_min;
    }

    public void setSeek_min(int seek_min) {
        this.seek_min = seek_min;
    }

    public int getSeek_max() {
        return seek_max;
    }

    public void setSeek_max(int seek_max) {
        this.seek_max = seek_max;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
