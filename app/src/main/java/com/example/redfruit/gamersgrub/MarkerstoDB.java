package com.example.redfruit.gamersgrub;

public class MarkerstoDB {

    public String marker_name;
    public Double latitude;
    public Double longitude;

    public MarkerstoDB() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public MarkerstoDB(String marker_name, Double latitude, Double longitude) {
        this.marker_name = marker_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
