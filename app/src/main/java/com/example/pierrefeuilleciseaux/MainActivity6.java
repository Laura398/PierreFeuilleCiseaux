package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String otherNumber = prefs.getString("otherNumber", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference other = database.getReference("" + otherNumber);

        other.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String statut = dataSnapshot.child("2").getValue(String.class);
                if (statut != null) {
                    if (statut.equals("Not Clicked")) {
                            Intent intent = new Intent(MainActivity6.this, MainActivity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });
    }
}