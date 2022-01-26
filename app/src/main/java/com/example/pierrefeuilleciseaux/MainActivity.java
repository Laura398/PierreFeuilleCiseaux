package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player1 = database.getReference("Player1");
        DatabaseReference player2 = database.getReference("Player2");

        final String[] player1Exists = {"no"};

        player1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sign = dataSnapshot.child("0").getValue(String.class);

                if (sign != null) {
                    player1Exists[0] = "yes";
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        Button save = findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText getName = findViewById(R.id.name);
                String name = getName.getText().toString();
                if (player1Exists[0] == "no") {
                    String playerNumber = "Player1";
                    String otherNumber = "Player2";
                    String number = "1";
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("playerName", "" + name);
                    editor.putString("playerNumber", "" + playerNumber);
                    editor.putString("otherNumber", "" + otherNumber);
                    editor.putString("number", "" + number);
                    editor.commit();
                    player1.removeValue();
                    ArrayList playerInfos = new ArrayList();
                    playerInfos.add(0, "1");
                    playerInfos.add(1, "" + name);
                    playerInfos.add(2, "Not Clicked");
                    playerInfos.add(3, "Sign");
                    playerInfos.add(4, 0);
                    playerInfos.add(5, 0);
                    player1.setValue(playerInfos);
                } else {
                    String playerNumber = "Player2";
                    String otherNumber = "Player1";
                    String number = "2";
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("playerName", "" + name);
                    editor.putString("playerNumber", "" + playerNumber);
                    editor.putString("otherNumber", "" + otherNumber);
                    editor.putString("number", "" + number);
                    editor.commit();
                    player2.removeValue();
                    ArrayList playerInfos = new ArrayList();
                    playerInfos.add(0, "2");
                    playerInfos.add(1, "" + name);
                    playerInfos.add(2, "Not Clicked");
                    playerInfos.add(3, "Sign");
                    playerInfos.add(4, 0);
                    playerInfos.add(5, 0);
                    player2.setValue(playerInfos);
                }
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}