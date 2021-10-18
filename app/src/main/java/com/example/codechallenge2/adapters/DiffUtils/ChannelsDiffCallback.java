package com.example.codechallenge2.adapters.DiffUtils;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.codechallenge2.objects.Channel;

import java.util.List;

public class ChannelsDiffCallback extends DiffUtil.Callback{
    //https://medium.com/android-news/smart-way-to-update-recyclerview-using-diffutil-345941a160e0
    private final List<Channel> mOldChannelList;
    private final List<Channel> mNewChannelList;

    public ChannelsDiffCallback(List<Channel> mOldChannelList, List<Channel> mNewChannelList) {
        this.mOldChannelList = mOldChannelList;
        this.mNewChannelList = mNewChannelList;
    }

    @Override
    public int getOldListSize() {
        return mOldChannelList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewChannelList.size();
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldChannelList.get(oldItemPosition).getCallLetter() == mNewChannelList.get(newItemPosition).getCallLetter();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Channel oldChannel = mOldChannelList.get(oldItemPosition);
        final Channel newChannel = mNewChannelList.get(newItemPosition);



        if(TextUtils.equals(oldChannel.getCallLetter(),newChannel.getCallLetter())
            && TextUtils.equals(oldChannel.getName(),newChannel.getName())
            && TextUtils.equals(oldChannel.getCurrentShow(),newChannel.getCurrentShow())
            && TextUtils.equals(oldChannel.getNextShow(),newChannel.getNextShow()) )
            return true;
        else
            return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
