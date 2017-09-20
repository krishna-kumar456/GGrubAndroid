package com.example.redfruit.gamersgrub;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Photo implements Serializable {

//    private String mDate;
//    private String mHumanDate;
//    private String mExplanation;
//    private String mUrl;

    private String mTitle;
    private String mDesc;
    private String mImage;

    public Photo(JSONObject photoJSON) {
        try {
//            mDate = photoJSON.getString("date");
//            mHumanDate = convertDateToHumanDate();
//            mExplanation = photoJSON.getString("explanation");
//            mUrl = photoJSON.getString("url");
            mTitle = photoJSON.getString("title");
            mDesc = photoJSON.getString("desc");
            mImage = photoJSON.getString("image");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getImage() {
        return mImage;
    }


}