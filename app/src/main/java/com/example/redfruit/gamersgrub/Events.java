package com.example.redfruit.gamersgrub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class Events extends AppCompatActivity implements ImageRequester.ImageRequesterResponse{


    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;


    private ArrayList<Photo> mPhotosList;
    private ImageRequester mImageRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent backtoLogin = new Intent(Events.this, Login.class);
                startActivity(backtoLogin);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mPhotosList = new ArrayList<>();
        mImageRequester = new ImageRequester(this);

        mAdapter = new RecyclerAdapter(mPhotosList);
        mRecyclerView.setAdapter(mAdapter);




    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPhotosList.size() == 0) {
            requestPhoto();
        }

    }

    private void requestPhoto() {

        try {
            mImageRequester.getPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivedNewPhoto(final Photo newPhoto) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPhotosList.add(newPhoto);
                mAdapter.notifyItemInserted(mPhotosList.size());
            }
        });
    }
}




