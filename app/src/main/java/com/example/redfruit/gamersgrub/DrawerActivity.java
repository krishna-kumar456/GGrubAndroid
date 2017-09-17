package com.example.redfruit.gamersgrub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.example.redfruit.gamersgrub.R.id.imageView;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userName;
    private TextView gamerID;
    private ImageView profilePic;
    private Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Login lgout = new Login();
                lgout.logOut();
                Intent backtoLogin = new Intent(DrawerActivity.this, Login.class);
                startActivity(backtoLogin);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        drawer.setOnTouchListener(new OnSwipeListener(DrawerActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(DrawerActivity.this, "top", Toast.LENGTH_SHORT).show();
                Intent openMaps = new Intent(DrawerActivity.this, Maps.class);
                startActivity(openMaps);
            }
            public void onSwipeRight() {
                Toast.makeText(DrawerActivity.this, "right", Toast.LENGTH_SHORT).show();

            }
            public void onSwipeLeft() {
                Toast.makeText(DrawerActivity.this, "left", Toast.LENGTH_SHORT).show();
                Intent openEvents = new Intent(DrawerActivity.this, Events.class);
                startActivity(openEvents);
            }
            public void onSwipeBottom() {
                Toast.makeText(DrawerActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        userName = (TextView) findViewById(R.id.username);
        profilePic = (ImageView) findViewById(R.id.profilepic);

        Login lg = new Login();
        String userN = lg.getUser();
        String userID = lg.getUserID();
        Uri fbPic = lg.getUri();


        if (userN.equals("null")) {
            userName.setText("Default User");

        }

        else {

            Log.d("ggrub", "Facebook UserID" + userID);
            userName.setText(userN);
            Picasso.with(this)
                    .load(fbPic) //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(120, 120)
                    .into(profilePic);



        }

        editProfile = (Button) findViewById(R.id.Edit);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toeditProfile = new Intent(DrawerActivity.this, EditProfile.class);
                startActivity(toeditProfile);
            }
        });






        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}
