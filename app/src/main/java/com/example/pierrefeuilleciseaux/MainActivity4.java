package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    Integer scoreP1;
    Integer scoreP2;
    String nameP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Button back = findViewById(R.id.back);
        Button again = findViewById(R.id.again);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerName = prefs.getString("playerName", null);
        String playerNumber = prefs.getString("playerNumber", null);
        String otherNumber = prefs.getString("otherNumber", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + playerNumber);
        DatabaseReference other = database.getReference("" + otherNumber);
        DatabaseReference score = database.getReference("Score");

        player.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scoreP1 = dataSnapshot.child("4").getValue(Integer.class);

                TextView textNameP1 = findViewById(R.id.nameP1);
                TextView textScoreP1 = findViewById(R.id.scoreP1);

                textNameP1.setText(playerName);
                textScoreP1.setText(scoreP1.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        other.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameP2 = dataSnapshot.child("1").getValue(String.class);
                scoreP2 = dataSnapshot.child("4").getValue(Integer.class);

                TextView textNameP2 = findViewById(R.id.nameP2);
                TextView textScoreP2 = findViewById(R.id.scoreP2);

                textNameP2.setText(nameP2);
                textScoreP2.setText(scoreP2.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        score.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Integer myScore = dataSnapshot.child("" + playerName).child("" + nameP2).child("" + playerName).getValue(Integer.class);

                if (scoreP1 > scoreP2) {
                    if (myScore != null) {
                        myScore += 1;
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("" + playerName, myScore);
                        score.child("" + playerName).child("" + nameP2).updateChildren(hashMap);
                    } else {
                        score.child("" + playerName).child("" + nameP2).child("" + playerName).setValue(scoreP1);
                    }
                } else if (scoreP2 > scoreP1) {
                    if (myScore == null) {
                        score.child("" + playerName).child("" + nameP2).child("" + playerName).setValue(0);
                    }
                } else {
                    if (myScore != null) {
                        myScore += 1;
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("" + playerName, myScore);
                        score.child("" + playerName).child("" + nameP2).updateChildren(hashMap);
                    } else {
                        score.child("" + playerName).child("" + nameP2).child("" + playerName).setValue(scoreP1);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.removeValue();
                prefs.edit().remove("playerName").commit();
                prefs.edit().remove("playerNumber").commit();
                prefs.edit().remove("otherNumber").commit();
                prefs.edit().remove("number").commit();
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerNumber = prefs.getString("playerNumber", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + playerNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment quitter ? Vos informations vont être perdues.")
                .setTitle("Attention !")
                .setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        player.removeValue();
                        prefs.edit().remove("playerName").commit();
                        prefs.edit().remove("playerNumber").commit();
                        prefs.edit().remove("otherNumber").commit();
                        prefs.edit().remove("number").commit();
                        Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}