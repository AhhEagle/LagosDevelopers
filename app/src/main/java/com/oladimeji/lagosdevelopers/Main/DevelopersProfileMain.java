package com.oladimeji.lagosdevelopers.Main;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oladimeji.lagosdevelopers.Profile.DevelopersProfile;
import com.oladimeji.lagosdevelopers.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;


/**
 * Created by Oladimeji on 7/10/2017.
 */


public class DevelopersProfileMain extends AppCompatActivity {
    DevelopersProfile developersProfile;
    TextView developerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers_profile_main);

        developersProfile = Parcels.unwrap(getIntent().getParcelableExtra("DeveloperProfile"));

        ImageView developerImage = (ImageView) findViewById(R.id.developer_image);
        Picasso.with(this)
                .load(developersProfile.getAvatarUrl())
                .into(developerImage);



        developerName = (TextView) findViewById(R.id.developer_name);
        developerName.setText(getDevelopersFullName());

        TextView developerLocation = (TextView) findViewById(R.id.developer_location);
        developerLocation.setText(developersProfile.getLocation());


        TextView developerRepos = (TextView) findViewById(R.id.developer_repos);
        developerRepos.setText(String.valueOf(developersProfile.getPublicRepos()));


        Button developerWebLink = (Button) findViewById(R.id.developer_web_link);
        developerWebLink.setText("VIEW " + getDevelopersFullName() + " ON THE WEB");


        developerWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri developerWebPage = Uri.parse(developersProfile.getHtmlUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, developerWebPage);
                Intent intentChooser = Intent.createChooser(intent, getString(R.string.view_developer_profile));
                if (intentChooser.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentChooser);
                }
            }
        });


        FloatingActionButton developerShareLink = (FloatingActionButton) findViewById(R.id.share_button);


        developerShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_developer_profile) + developersProfile.getLogin() + "\n " + developersProfile.getHtmlUrl());
                Intent intentChooser = Intent.createChooser(intent, getString(R.string.share_intent_chooser_title));
                if (intentChooser.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentChooser);
                }
            }
        });
    }

    private String getDevelopersFullName() {
        String developersName = developersProfile.getName();
        if (developersName.length() > 40) {
            return developersName.substring(0, 40);
        }
        return developersName;
    }
}
