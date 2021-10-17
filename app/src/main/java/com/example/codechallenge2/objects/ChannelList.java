package com.example.codechallenge2.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChannelList {

    @SerializedName("value")
    private List<Channel> channels = new ArrayList<>();

    @SerializedName("odata.nextLink")
    private String nextLink;


    //Add multiple channels to the list
    public void addChannels(List c) {
        channels.addAll(c);
    }


    //Getters & Setters
    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }
}
