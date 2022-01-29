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
        String playerNumber = prefs.getString("playerNumber", null);
        String otherNumber = prefs.getString("otherNumber", null);

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

                String signP1inDB = dataSnapshot.child("3").getValue(String.class);
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
                                textView.setText("Egalité");
                            } else if (signP2inDB.equals("Feuille")) {
                                textView.setText("Perdu");
                            } else if (signP2inDB.equals("Ciseaux")) {
                                textView.setText("Gagné");
                                scoreP1 +=1;
                            } else {
                                textView.setText("Gagné");
                                scoreP1 +=1;
                            }
                        } else if (signP1inDB.equals("Feuille")) {
                            if (signP2inDB.equals("Pierre")) {
                                textView.setText("Gagné");
                                scoreP1 +=1;
                            } else if (signP2inDB.equals("Feuille")) {
                                textView.setText("Egalité");
                            } else if (signP2inDB.equals("Ciseaux")) {
                                textView.setText("Perdu");
                            } else {
                                textView.setText("Gagné");
                                scoreP1 +=1;
                            }
                        } else if (signP1inDB.equals("Ciseaux")) {
                            if (signP2inDB.equals("Pierre")) {
                                textView.setText("Perdu");
                            } else if (signP2inDB.equals("Feuille")) {
                                textView.setText("Gagné");
                                scoreP1 +=1;
                            } else if (signP2inDB.equals("Ciseaux")) {
                                textView.setText("Egalité");
                            } else {
                                textView.setText("Gagné");
                                scoreP1 +=1;
                            }
                        } else {
                            textView.setText("Perdu");
                        }

                        player.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // This method is called once with the initial value and again
                                // whenever data at this location is updated.
                                String number = dataSnapshot.child("0").getValue(String.class);
                                String name = dataSnapshot.child("1").getValue(String.class);
                                String sign = dataSnapshot.child("3").getValue(String.class);
                                ArrayList signList = new ArrayList();
                                signList.add(0, "" + number);
                                signList.add(1, "" + name);
                                signList.add(2, "Clicked");
                                signList.add(3, "" + sign);
                                signList.add(4, scoreP1);
                                signList.add(5, 0);
                                player.setValue(signList);
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
                    player.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String number = dataSnapshot.child("0").getValue(String.class);
                            String name = dataSnapshot.child("1").getValue(String.class);
                            String sign = dataSnapshot.child("3").getValue(String.class);
                            ArrayList signList = new ArrayList();
                            signList.add(0, "" + number);
                            signList.add(1, "" + name);
                            signList.add(2, "Clicked");
                            signList.add(3, "" + sign);
                            signList.add(4, scoreP1);
                            signList.add(5, 0);
                            player.setValue(signList);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("APPX", "Failed to read value.", error.toException());
                        }
                    });
                    Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    player.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String number1 = dataSnapshot.child("0").getValue(String.class);
                            String name1 = dataSnapshot.child("1").getValue(String.class);
                            ArrayList signList = new ArrayList();
                            signList.add(0, "" + number1);
                            signList.add(1, "" + name1);
                            signList.add(2, "Not Clicked");
                            signList.add(3, "Sign");
                            signList.add(4, scoreP1);
                            signList.add(5, playRun);
                            player.setValue(signList);
                            other.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String statut2 = dataSnapshot.child("2").getValue(String.class);
                                    if (statut2.equals("Not Clicked")) {
                                        Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
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

                }

            }
        });

        other.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String sign = dataSnapshot.child("0").getValue(String.class);

                if (sign == null) {
                    other.removeEventListener(this);
                    Intent intent = new Intent(MainActivity3.this, MainActivity7.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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