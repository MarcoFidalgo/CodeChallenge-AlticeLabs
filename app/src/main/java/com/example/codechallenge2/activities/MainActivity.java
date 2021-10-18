package com.example.codechallenge2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.codechallenge2.R;
import com.example.codechallenge2.adapters.DiffUtils.ChannelsDiffCallback;
import com.example.codechallenge2.adapters.RecyclerAdapter;
import com.example.codechallenge2.interfaces.MeoJsonApi;
import com.example.codechallenge2.objects.Channel;
import com.example.codechallenge2.objects.ChannelList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //Constants
    private static final String BASE_URL_CHANNEL_LIST = "https://ott.online.meo.pt/catalog/v9/Channels?UserAgent=AND&$filter=substringof(%27MEO_Mobile%27,AvailableOnChannels)%20and%20IsAdult%20eq%20false&$orderby=ChannelPosition%20asc&$inlinecount=allpages";

    private String nextLink = "";
    private ChannelList listOfAllChannels;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfAllChannels = new ChannelList();
        //Loop: Iterate through all "nextLink" channel pages
        getChannelList(BASE_URL_CHANNEL_LIST);
    }

    private void setAdapter() {

        //creates the adapter for the RecyclerView
        //adapter = new RecyclerAdapter(new ArrayList<Channel>(listOfAllChannels.getChannels()));
        adapter = new RecyclerAdapter(new ArrayList<Channel>());

        //creates the layout manager(measures and position item views within the RecyclerView)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /** Gets the entire list of all available channels using a starting filter (Recursive) **/
    private void getChannelList(String url) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://ott.online.meo.pt/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MeoJsonApi meoJsonApi = retrofit.create(MeoJsonApi.class);
            Call<ChannelList> call = meoJsonApi.getChannels(url, new Channel("", ""), new String());
            call.enqueue(new Callback<ChannelList>() {
                @Override
                public void onResponse(Call<ChannelList> call, Response<ChannelList> response) {

                    if (!response.isSuccessful()) {
                        Log.i("actDebug", "Code: " + response.code());
                        return;
                    }

                    //Updates the list of channels, adding more each iteration(page)
                    listOfAllChannels.addChannels(response.body().getChannels());

                    //Exit condition
                    if(response.body().getNextLink() == null){
                        Log.i("actDebug", "Size of channel list: "+listOfAllChannels.getChannels().size());

                        return;
                    }

                    //Fills the recycler view with the first page of Channels (Sets the adapter)
                    if(url.equals(BASE_URL_CHANNEL_LIST)){
                        recyclerView = findViewById(R.id.recyclerView);
                        setAdapter();
                    }

                    nextLink = (response.body().getNextLink() ).replace("http","https");
                    Log.i("actDebug", "Response: " + response);

                    adapter.updateChannelListItems(listOfAllChannels.getChannels());
                    getChannelList(nextLink);
                }

                @Override
                public void onFailure(Call<ChannelList> call, Throwable t) {
                    Log.i("actDebug", t.getMessage());
                }
            });

    }
}