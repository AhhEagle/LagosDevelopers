package com.oladimeji.lagosdevelopers.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.oladimeji.lagosdevelopers.R;
import com.oladimeji.lagosdevelopers.Adapter.DeveloperAdapter;
import com.oladimeji.lagosdevelopers.Profile.Developers;
import com.oladimeji.lagosdevelopers.Profile.DevelopersProfile;
import com.oladimeji.lagosdevelopers.Profile.JSON;
import com.oladimeji.lagosdevelopers.Rest.QueryClient;
import com.oladimeji.lagosdevelopers.Rest.Github;
import com.oladimeji.lagosdevelopers.Rest.ClickLibrary;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by Oladimeji on 7/10/2017.
 */



public class DevelopersListMain extends AppCompatActivity {

    List<Developers> developers;
    RecyclerView developerRecyclerView;
    String developersUsername = "";
    static Github githubService;
    CardView listProgressBar;
    CardView profileProgressBar;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;
    boolean isConnectedOrConnecting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers_list_main);


        developerRecyclerView = (RecyclerView) findViewById(R.id.developers_list);


        listProgressBar = (CardView) findViewById(R.id.list_progress);


        profileProgressBar = (CardView) findViewById(R.id.profile_progress);


        final LinearLayout emptyStateView = (LinearLayout) findViewById(R.id.empty_state_view);


        githubService = QueryClient.getClient().create(Github.class);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                isConnectedOrConnecting = networkInfo != null && networkInfo.isConnectedOrConnecting();
                if (isConnectedOrConnecting) {
                    emptyStateView.setVisibility(View.GONE);
                    listProgressBar.setVisibility(View.VISIBLE);
                    getListOfDevelopers();
                } else {
                    if(developers != null) {
                        emptyStateView.setVisibility(View. GONE);
                    } else {
                        emptyStateView.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(broadcastReceiver, intentFilter);

        ClickLibrary.addTo(developerRecyclerView).setOnItemClickListener(new ClickLibrary.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (isConnectedOrConnecting) {
                    profileProgressBar.setVisibility(View.VISIBLE);
                    Developers developer = developers.get(position);
                    developersUsername = developer.getLogin();
                    getDeveloperProfile(developersUsername);
                }
            }
        });

    }


    private void getListOfDevelopers() {
        Call<JSON> callDevelopersList = githubService.getJSONResponse();
        callDevelopersList.enqueue(new Callback<JSON>() {
            @Override
            public void onResponse(Call<JSON> call, Response<JSON> response) {
                listProgressBar.setVisibility(View.GONE);
                developers = response.body().getItems();
                developerRecyclerView.setAdapter(new DeveloperAdapter(developers, getApplicationContext()));
                developerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
            @Override
            public void onFailure(Call<JSON> call, Throwable t) {

            }
        });
    }


    private void getDeveloperProfile(String developerUsername) {
        Call<DevelopersProfile> callDeveloperProfile = githubService.getDeveloperProfile(developerUsername);
        callDeveloperProfile.enqueue(new Callback<DevelopersProfile>() {
            @Override
            public void onResponse(Call<DevelopersProfile> call, Response<DevelopersProfile> response) {
                profileProgressBar.setVisibility(View.GONE);
                DevelopersProfile developerProfile = response.body();
                Intent intent = new Intent(DevelopersListMain.this, DevelopersProfileMain.class);
                intent.putExtra("DeveloperProfile", Parcels.wrap(developerProfile));
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DevelopersProfile> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null) {
            this.unregisterReceiver(broadcastReceiver);
        }
    }
}
