package com.example.redfruit.gamersgrub;


public class Profile {


    public String gamerName;
//    public String userName;
    public String userId;


    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Profile(String userId, String gamerName) {

        this.gamerName = gamerName;
//        this.userName = userName;
        this.userId = userId;

    }

    public String getGamerName() {
        return gamerName;
    }

    public String getUserId() {
        return userId;
    }


    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}