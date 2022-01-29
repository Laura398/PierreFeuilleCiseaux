package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        final com.example.listview.CustomAdapter[] adapter = new com.example.listview.CustomAdapter[1];
        ArrayList<TextSwitch> tsList = new ArrayList();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerName = prefs.getString("playerName", null);
        String otherNumber = prefs.getString("otherNumber", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference score = database.getReference("Score");
        DatabaseReference other = database.getReference("" + otherNumber);

        other.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nameP2 = dataSnapshot.child("1").getValue(String.class);

                score.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange (DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String scoreP1 = dataSnapshot.child("" + playerName).child("" + nameP2).child("" + playerName).getValue().toString();
                        String scoreP2 = dataSnapshot.child("" + nameP2).child("" + playerName).child("" + nameP2).getValue().toString();

                        TextView nameP1View = findViewById(R.id.nameP1);
                        nameP1View.setText(playerName);
                        TextView nameP2View = findViewById(R.id.nameP2);
                        nameP2View.setText(nameP2);
                        TextView scoreP1View = findViewById(R.id.scoreP1);
                        scoreP1View.setText(scoreP1);
                        TextView scoreP2View = findViewById(R.id.scoreP2);
                        scoreP2View.setText(scoreP2);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("APPX", "Failed to read value.", error.toException());
                    }
                });










            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });






    }
}