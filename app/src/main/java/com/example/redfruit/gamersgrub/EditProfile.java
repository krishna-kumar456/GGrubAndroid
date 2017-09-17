package com.example.redfruit.gamersgrub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class EditProfile extends AppCompatActivity {

    private EditText gamerID;
    private Button mSaveButton;
    private String gamerName;
    private DatabaseReference mDatabase;
    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("profile");


        gamerID = (EditText) findViewById(R.id.editText);
        mSaveButton = (Button) findViewById(R.id.saveButton);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Profile userP = dataSnapshot.getValue(Profile.class);

                Log.d("ggrub", "Reading data from database" + userP);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ggrub", "profile:onCancelled", databaseError.toException());

                Toast.makeText(EditProfile.this, "Failed to load post.",
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


}
