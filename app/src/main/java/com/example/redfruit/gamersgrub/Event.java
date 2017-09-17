package com.example.redfruit.gamersgrub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Event extends AppCompatActivity {

    private ImageView mPhotoImageView;
    private TextView mDescriptionTextView;
    private Photo mSelectedPhoto;
    private static final String PHOTO_KEY = "PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoEvents = new Intent(Event.this, Events.class);
                startActivity(backtoEvents);
            }
        });

        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mSelectedPhoto = (Photo) getIntent().getSerializableExtra(PHOTO_KEY);
        Log.d("ggrub", "mSelectedPhotot " + mSelectedPhoto);
        Picasso.with(this).load(mSelectedPhoto.getImage()).into(mPhotoImageView);

        mDescriptionTextView = (TextView) findViewById(R.id.photoDescription);

        if (mDescriptionTextView != null) {
            mDescriptionTextView.setText(mSelectedPhoto.getDesc());
        }

        mDescriptionTextView.setOnTouchListener(new OnSwipeListener(Event.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                Toast.makeText(Event.this, "right", Toast.LENGTH_SHORT).show();
                Intent openEvents = new Intent(Event.this, Events.class);
                startActivity(openEvents);

            }
            public void onSwipeLeft() {

            }
            public void onSwipeBottom() {

            }

        });


    }

}
