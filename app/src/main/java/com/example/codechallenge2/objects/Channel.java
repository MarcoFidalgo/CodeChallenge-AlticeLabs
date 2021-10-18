package com.example.codechallenge2.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Channel {

    @SerializedName("data") private List<Channel> channels;

    @SerializedName("Title")
    private String name;

    @SerializedName("CallLetter")
    private String callLetter;

    private String currentShow;
    private String nextShow;

    /** Used when loading list of channels */
    public Channel(String name, String callLetter) {
        this.name = name;
        this.callLetter = callLetter;
    }


    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentShow() {
        return currentShow;
    }

    public void setCurrentShow(String currentShow) {
        this.currentShow = currentShow;
    }

    public String getNextShow() {
        return nextShow;
    }

    public void setNextShow(String nextShow) {
        this.nextShow = nextShow;
    }

    public String getCallLetter() {
        return callLetter;
    }

    public void setCallLetter(String callLetter) {
        this.callLetter = callLetter;
    }
}
