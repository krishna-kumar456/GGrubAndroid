package com.example.redfruit.gamersgrub;


public class Profile {


    public String gamerName;
    public String userName;
    public String userId;


    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Profile(String gamerName, String userName, String userId) {

        this.gamerName = gamerName;
        this.userName = userName;
        this.userId = userId;

    }
}