package com.example.codechallenge2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechallenge2.objects.Channel;
import com.example.codechallenge2.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<Channel> channelList;

    public RecyclerAdapter(ArrayList<Channel> channelList) {
        this.channelList = channelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvCurrentShow;
        private TextView tvNextShow;

        public MyViewHolder(final View view){
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCurrentShow = view.findViewById(R.id.tvCurrentShow);
            tvNextShow = view.findViewById(R.id.tvNextShow);
        }
    }


    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates the View
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = channelList.get(position).getName(),
                currentShow = channelList.get(position).getCurrentShow(),
                nextShow = channelList.get(position).getNextShow();
        holder.tvName.setText(name);
        holder.tvCurrentShow.setText(currentShow);
        holder.tvNextShow.setText(nextShow);

    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }
}
