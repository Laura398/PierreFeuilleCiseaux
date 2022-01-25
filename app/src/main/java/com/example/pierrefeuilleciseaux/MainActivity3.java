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

public class MainActivity3 extends AppCompatActivity {

    Integer playRun = 0;
    Integer scoreP1;
    Integer scoreP2;
    String nameP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        View view = findViewById(R.id.view);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerName = prefs.getString("playerName", null);
        String playerNumber = prefs.getString("playerNumber", null);
        String otherNumber = prefs.getString("otherNumber", null);
        String number = prefs.getString("number", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + playerNumber);
        DatabaseReference other = database.getReference("" + otherNumber);

        player.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int imgRock = getResources().getIdentifier("" + R.drawable.pierre100, null, null);
                int imgLeaf = getResources().getIdentifier("" + R.drawable.feuille100, null, null);
                int imgScissors = getResources().getIdentifier("" + R.drawable.ciseaux100, null, null);
                int imgSkip = getResources().getIdentifier("" + R.drawable.skip, null, null);

                String numberP1 = dataSnapshot.child("0").getValue(String.class);
                String nameP1 = dataSnapshot.child("1").getValue(String.class);
                String statutP1 = dataSnapshot.child("2").getValue(String.class);
                String signP1inDB = dataSnapshot.child("3").getValue(String.class);
                Integer score1 = dataSnapshot.child("4").getValue(Integer.class);
                scoreP1 = dataSnapshot.child("4").getValue(Integer.class);
                playRun = dataSnapshot.child("5").getValue(Integer.class);
                ImageView signP1 = findViewById(R.id.sign1);

                if (signP1inDB.equals("Pierre")) {
                    signP1.setImageResource(imgRock);
                } else if (signP1inDB.equals("Feuille")) {
                    signP1.setImageResource(imgLeaf);
                } else if (signP1inDB.equals("Ciseaux")) {
                    signP1.setImageResource(imgScissors);
                } else {
                    signP1.setImageResource(imgSkip);
                }

                ArrayList signList = new ArrayList();
                signList.add(0, "" + number);
                signList.add(1, "" + nameP1);
                signList.add(2, "Not Clicked");
                signList.add(3, "");
                signList.add(4, score1);
                signList.add(5, playRun);
                player.setValue(signList);

                other.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        int imgRock = getResources().getIdentifier("" + R.drawable.pierre100, null, null);
                        int imgLeaf = getResources().getIdentifier("" + R.drawable.feuille100, null, null);
                        int imgScissors = getResources().getIdentifier("" + R.drawable.ciseaux100, null, null);
                        int imgSkip = getResources().getIdentifier("" + R.drawable.skip, null, null);

                        String signP2inDB = dataSnapshot.child("3").getValue(String.class);
                        scoreP2 = dataSnapshot.child("4").getValue(Integer.class);
                        nameP2 = dataSnapshot.child("1").getValue(String.class);
                        ImageView signP2 = findViewById(R.id.sign2);

                        if (signP2inDB.equals("Pierre")) {
                            signP2.setImageResource(imgRock);
                        } else if (signP2inDB.equals("Feuille")) {
                            signP2.setImageResource(imgLeaf);
                        } else if (signP2inDB.equals("Ciseaux")) {
                            signP2.setImageResource(imgScissors);
                        } else {
                            signP2.setImageResource(imgSkip);
                        }

                        playRun += 1;

                        TextView textView = findViewById(R.id.textView);
                        if (signP1inDB.equals("Pierre")) {
                            if (signP2inDB.equals("Pierre")) {
                                textView.setText("Draw");
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else if (signP2inDB.equals("Feuille")) {
                                textView.setText("Loose");
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else if (signP2inDB.equals("Ciseaux")) {
                                textView.setText("Win");
                                scoreP1 +=1;
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else {
                                textView.setText("Win");
                                scoreP1 +=1;
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            }
                        } else if (signP1inDB.equals("Feuille")) {
                            if (signP2inDB.equals("Pierre")) {
                                textView.setText("Win");
                                scoreP1 +=1;
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else if (signP2inDB.equals("Feuille")) {
                                textView.setText("Draw");
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else if (signP2inDB.equals("Ciseaux")) {
                                textView.setText("Loose");
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else {
                                textView.setText("Win");
                                scoreP1 +=1;
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            }
                        } else if (signP1inDB.equals("Ciseaux")) {
                            if (signP2inDB.equals("Pierre")) {
                                textView.setText("Loose");
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else if (signP2inDB.equals("Feuille")) {
                                textView.setText("Win");
                                scoreP1 +=1;
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else if (signP2inDB.equals("Ciseaux")) {
                                textView.setText("Draw");
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            } else {
                                textView.setText("Win");
                                scoreP1 +=1;
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + numberP1);
                                signList.add(1, "" + nameP1);
                                signList.add(2, "Not Clicked");
                                signList.add(3, "");
                                signList.add(4, scoreP1);
                                signList.add(5, playRun);
                                player.setValue(signList);
                            }
                        } else {
                            textView.setText("Loose");
                            ArrayList signList = new ArrayList();
                            signList.add(0, "" + numberP1);
                            signList.add(1, "" + nameP1);
                            signList.add(2, "Not Clicked");
                            signList.add(3, "");
                            signList.add(4, scoreP1);
                            signList.add(5, playRun);
                            player.setValue(signList);
                        }

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playRun.equals(3)) {
                    Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                    startActivity(intent);
                }

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
                        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}