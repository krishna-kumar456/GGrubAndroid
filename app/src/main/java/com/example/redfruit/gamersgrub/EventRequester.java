package com.example.redfruit.gamersgrub;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EventRequester {

    public interface EventRequesterResponse {
        void receivedNewPhoto(Photo newPhoto);
    }

    private EventRequesterResponse mResponseListener;
    private Context mContext;
    private boolean mLoadingData;

    public boolean isLoadingData() {
        return mLoadingData;
    }


    public EventRequester(Activity listeningActivity) {


        mResponseListener = (EventRequesterResponse) listeningActivity;
        mContext = listeningActivity.getApplicationContext();
        mLoadingData = false;
    }

    public void getPhoto() throws IOException {

        try {

            mLoadingData = true;

            eventsAPI(new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                    Log.d("ggrub", "events" + result);


                    try {
                        JSONObject data = result.getJSONObject("data");
                        JSONArray allEvents = data.getJSONArray("allEvents");
                        Log.d("ggrub", "post allEvents" + allEvents);
                        for (int i = allEvents.length(); i >= 0; i--) {
                            try {
                                JSONObject oneObject = allEvents.getJSONObject(i);
                                // Pulling items from the array
                                Log.d("ggrub", "oneObject" + oneObject);
                                String title = oneObject.getString("title");
                                String desc = oneObject.getString("desc");
                                String image = oneObject.getString("image");

                                Log.d("ggrub", "Title" + title);
                                Log.d("ggrub", "Description" + image);
                                Log.d("ggrub", "Image" + desc);

                                Photo receivedPhoto = new Photo(oneObject);
                                mResponseListener.receivedNewPhoto(receivedPhoto);
                                mLoadingData = false;



                            } catch (JSONException e) {


                            }
                        }
                    }

                    catch(Exception e) {

                        Log.d("ggrub", "Something went wrong!");
                        mLoadingData = false;
                    }



                }


            });
        }

        catch (Exception e) {

        }




    }

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }

    public void eventsAPI(final VolleyCallback callback) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        JSONObject jsonParams = new JSONObject();


        try {

            params.put("query", "{ allEvents { id title desc image } }");
            jsonParams.put("query", "{ allEvents { id title desc image } }");

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

            Log.d("ggrub", "Something went wrong with event pull " + e);

        }
    }
}
