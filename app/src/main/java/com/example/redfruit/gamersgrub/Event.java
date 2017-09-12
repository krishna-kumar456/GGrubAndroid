package com.example.redfruit.gamersgrub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mPhotoImageView = (ImageView) findViewById(R.id.photoImageView);
        mSelectedPhoto = (Photo) getIntent().getSerializableExtra(PHOTO_KEY);
        Picasso.with(this).load(mSelectedPhoto.getUrl()).into(mPhotoImageView);

        mDescriptionTextView = (TextView) findViewById(R.id.photoDescription);

        if (mDescriptionTextView != null) {
            mDescriptionTextView.setText(mSelectedPhoto.getExplanation());
        }


    }

}
