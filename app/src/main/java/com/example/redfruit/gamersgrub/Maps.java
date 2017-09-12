package com.example.redfruit.gamersgrub;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import static android.R.attr.name;
import static android.R.attr.onClick;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;
import static com.example.redfruit.gamersgrub.R.id.map;

public class Maps extends FragmentActivity implements OnMapReadyCallback,
        OnMarkerClickListener {

    private GoogleMap mMap;
    private Button logout;
    private Button back;

    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private ArrayList<MarkerOptions> mMarkerArrayList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private ValueEventListener mDBListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("markers");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent backtoLogin = new Intent(Maps.this, Login.class);
                startActivity(backtoLogin);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MarkerstoDB mark = dataSnapshot.getValue(MarkerstoDB.class);

                Log.d("ggrub", "Reading data from database" + mark);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ggrub", "markersdb:onCancelled", databaseError.toException());

                Toast.makeText(Maps.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();

            }
        };
        mDatabase.addValueEventListener(postListener);

        mDBListener = postListener;



    }


    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mDBListener != null) {
            mDatabase.removeEventListener(mDBListener);
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(city));





        mMap.setOnMarkerClickListener(Maps.this);




        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {


                try {

                    markersAPI(new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {

                            Log.d("ggrub", "markers" + result);


                            try {
                                JSONObject data = result.getJSONObject("data");
                                JSONArray allMarkers = data.getJSONArray("allMarkers");
                                Log.d("ggrub", "post allAMrjers" + allMarkers);
                                for (int i = 0; i < allMarkers.length(); i++) {
                                    try {
                                        JSONObject oneObject = allMarkers.getJSONObject(i);
                                        // Pulling items from the array
                                        Log.d("ggrub", "oneObject" + oneObject);
                                        String markerName = oneObject.getString("name");
                                        Double longitude = oneObject.getDouble("longitude");
                                        Double latitude = oneObject.getDouble("latitude");

                                        Log.d("ggrub", "Name" + markerName);
                                        Log.d("ggrub", "Latitude" + latitude);
                                        Log.d("ggrub", "Longitude" + longitude);

                                        MarkerOptions store_marker= new MarkerOptions();
                                        LatLng city = new LatLng(longitude, latitude);
                                        store_marker.position(city);
                                        store_marker.title(markerName);

                                        mMap.addMarker(store_marker);
                                        mMarkerArrayList.add(store_marker);
                                        Log.d("ggrub", "Added marker to mMarkerArrayList" + mMarkerArrayList);


                                    } catch (JSONException e) {


                                    }
                                }
                            }

                            catch(Exception e) {}


                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

                            for (MarkerOptions marker : mMarkerArrayList) {
                                Log.d("ggrub", "marker" + marker.getPosition());
                                builder.include(marker.getPosition());
                            }

//                            builder.include(store_marker.getPosition());
                            LatLngBounds bounds = builder.build();

                            int width = getResources().getDisplayMetrics().widthPixels;
                            int height = getResources().getDisplayMetrics().heightPixels;
                            int padding = (int) (width * 0.10);
                            // offset from edges of the map in pixels
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                            mMap.moveCamera(cu);
                        }
                    });
                }

                catch (Exception e) {

                }


            }
        });






    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent infoIntent = new Intent(Maps.this, Infocard.class);
        startActivity(infoIntent);

        return false;
    }


    public void addMarkertoDatabase(String mId, String mName, Double lat, Double longi) {

        MarkerstoDB m = new MarkerstoDB(mName, lat, longi);

        mDatabase.child("markers").child(mId).setValue(m);


    }

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }

    public void markersAPI(final VolleyCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        JSONObject jsonParams = new JSONObject();


        try {

            params.put("query", "{ allMarkers { id name longitude latitude } }");
            jsonParams.put("query", "{ allMarkers { id name longitude latitude } }");

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getApplicationContext(), "https://ggrub-service.herokuapp.com/graphql", entity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    Log.d("ggrub", "Post Success" + response);
                    callback.onSuccess(response);

                }

            });


        }

        catch(Exception e) {

            Log.d("ggrub", "Something went wrong with marker pull " + e);

        }
    }



}


