package com.example.codechallenge2.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechallenge2.adapters.DiffUtils.ChannelsDiffCallback;
import com.example.codechallenge2.interfaces.MeoJsonApi;
import com.example.codechallenge2.objects.Channel;
import com.example.codechallenge2.R;
import com.example.codechallenge2.objects.ChannelList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private static final String BASE_URL = "https://ott.online.meo.pt/Program/v9/Programs/NowAndNextLiveChannelPrograms?" +
            "UserAgent=AND&$filter=CallLetter%20eq%20%27CHANNEL_NAME%27&$orderby=StartDate%20asc";
    private static final String BASE_URL_THUMBNAIL = "http://cdn-images.online.meo.pt/eemstb/ImageHandler.ashx?evTitle=CHANNEL_TITLE&chCallLetter=CALL_LETTER&profile=16_9&width=320";

    private ArrayList<Channel> channelList;

    public RecyclerAdapter(ArrayList<Channel> channelList) {
        this.channelList = channelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvCurrentShow;
        private TextView tvNextShow;
        private ImageView ivThumbnail;

        public MyViewHolder(final View view){
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCurrentShow = view.findViewById(R.id.tvCurrentShow);
            tvNextShow = view.findViewById(R.id.tvNextShow);
            ivThumbnail = view.findViewById(R.id.ivThumbnail);
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
    public int getItemCount() {
        return channelList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = channelList.get(position).getName(),
                currentShow = channelList.get(position).getCurrentShow(),
                nextShow = channelList.get(position).getNextShow();
        holder.tvName.setText(name);
        holder.tvCurrentShow.setText(currentShow);
        holder.tvNextShow.setText(nextShow);

        //Fills visible channels Now&Next info
        getChannelInfo(channelList.get(position).getCallLetter(), position, holder);


    }



    public void updateChannelListItems(List<Channel> channels) {
        final ChannelsDiffCallback diffCallback = new ChannelsDiffCallback(this.channelList, channels);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.channelList.clear();
        this.channelList.addAll(channels);
        diffResult.dispatchUpdatesTo(this);
    }

    /** */
    private void getChannelInfo(String callLetter, int position, @NonNull RecyclerAdapter.MyViewHolder holder) {
        String baseUrl = BASE_URL;


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ott.online.meo.pt/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MeoJsonApi meoJsonApi = retrofit.create(MeoJsonApi.class);
        Call<ChannelList> call = meoJsonApi.getChannelInfo(baseUrl.replace("CHANNEL_NAME",callLetter), new Channel("", ""));
        call.enqueue(new Callback<ChannelList>() {
            @Override
            public void onResponse(Call<ChannelList> call, Response<ChannelList> response) {

                if (!response.isSuccessful()) {
                    Log.i("actDebug", "Code: " + response.code());
                    return;
                }

                //Updates the Now&Then
                channelList.get(position).setCurrentShow(response.body().getChannels().get(0).getName());
                channelList.get(position).setNextShow(response.body().getChannels().get(1).getName());

                holder.tvCurrentShow.setText(channelList.get(position).getCurrentShow());
                holder.tvNextShow.setText(channelList.get(position).getNextShow());

                //Draws show's thumbnails
                loadThumbnail(holder, position);
            }

            @Override
            public void onFailure(Call<ChannelList> call, Throwable t) {
                Log.i("actDebug", t.getMessage());
            }
        });

    }

    /** Downloads the thumbnail of current shows using Picasso library*/
    private void loadThumbnail(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String imageUrl = BASE_URL_THUMBNAIL;
        //if(channelList.get(position).getCurrentShow() != null) {
        imageUrl = imageUrl.replace("CHANNEL_TITLE", channelList.get(position).getCurrentShow());
        imageUrl = imageUrl.replace("CALL_LETTER", channelList.get(position).getCallLetter());
        //}
        Picasso.get().load(imageUrl)
                .error(R.drawable.loading_error)
                .placeholder(R.drawable.loading)
                .into(holder.ivThumbnail, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {}
                });
    }
}
