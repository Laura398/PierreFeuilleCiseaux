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

        Button back = findViewById(R.id.back);
        Button again = findViewById(R.id.again);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerName = prefs.getString("playerName", null);
        String otherNumber = prefs.getString("otherNumber", null);
        String playerNumber = prefs.getString("playerNumber", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference score = database.getReference("Score");
        DatabaseReference player = database.getReference("" + playerNumber);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.removeValue();
                prefs.edit().remove("playerName").commit();
                prefs.edit().remove("playerNumber").commit();
                prefs.edit().remove("otherNumber").commit();
                prefs.edit().remove("number").commit();
                Intent intent = new Intent(MainActivity8.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String number = dataSnapshot.child("0").getValue(String.class);
                        String name = dataSnapshot.child("1").getValue(String.class);
                        ArrayList signList = new ArrayList();
                        signList.add(0, "" + number);
                        signList.add(1, "" + name);
                        signList.add(2, "Not Clicked");
                        signList.add(3, "Sign");
                        signList.add(4, 0);
                        signList.add(5, 0);
                        player.setValue(signList);
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
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String statut = dataSnapshot.child("2").getValue(String.class);

                        if (statut.equals("Not Clicked")) {
                            Intent intent = new Intent(MainActivity8.this, MainActivity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity8.this, MainActivity6.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("APPX", "Failed to read value.", error.toException());
                    }
                });
                Intent intent = new Intent(MainActivity8.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                        Intent intent = new Intent(MainActivity8.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                        System.exit(0);
                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}