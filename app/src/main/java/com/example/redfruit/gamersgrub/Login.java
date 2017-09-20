package com.example.redfruit.gamersgrub;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mAuth = FirebaseAuth.getInstance();


        AccessToken token = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();

        if(token != null || profile != null) {

            goHome();
        }

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facebookLogin();

            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }


    private void facebookLogin() {



        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("ggrub", "Facebook onSuccess" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        goHome();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("ggrub", "Facebook onSuccess Cancelled!");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("ggrub", "Facebook onError" + exception);
                    }
                });



    }

    public void goHome() {
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }

    public void logOut() {

        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
    }

    public String getUser() {
        return mAuth.getInstance().getCurrentUser().getDisplayName();
    }

    public String getUserID() {
        return mAuth.getInstance().getCurrentUser().getUid();
    }

    public Uri getUri() { return mAuth.getInstance().getCurrentUser().getPhotoUrl(); }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("ggrub", "handleFacebookAccessToken:" + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ggrub", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("ggrub", "User: " + user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ggrub", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }

}
