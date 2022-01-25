package com.example.pierrefeuilleciseaux;

import static android.content.ContentValues.TAG;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity2 extends AppCompatActivity {
    Integer playRun;
    public int counter = 10;
    @TargetApi(Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final TextView textView = findViewById(R.id.timer);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerName = prefs.getString("playerName", null);
        String playerNumber = prefs.getString("playerNumber", null);
        String otherNumber = prefs.getString("otherNumber", null);
        String number = prefs.getString("number", null);
        if (playerName != null) {
            TextView getNameP1 = findViewById(R.id.nameP1);
            getNameP1.setText(playerName);
        }

        ImageButton pierre = findViewById(R.id.pierre);
        ImageButton feuille = findViewById(R.id.feuille);
        ImageButton ciseaux = findViewById(R.id.ciseaux);
        Button back = findViewById(R.id.back);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + playerNumber);
        DatabaseReference other = database.getReference("" + otherNumber);

        final Integer[] scoreP1 = new Integer[1];
        final Integer[] scoreP2 = new Integer[1];

        final Integer[] statutP1 = new Integer[1];
        final Integer[] statutP2 = new Integer[1];

        // CountDownTimer yourCountDownTimer = new CountDownTimer(10000,1000) {
        //    @Override
        //    public void onTick(long millisUntilFinished) {
        //        textView.setText(String.valueOf(counter));
        //        counter--;
        //    }
        //    @Override
        //    public void onFinish() {
        //        if (playerName != null) {
        //            ArrayList signList = new ArrayList();
        //            signList.add(0, "" + number);
        //            signList.add(1, "" + playerName);
        //            signList.add(2, playRun);
        //            signList.add(3, "");
        //            signList.add(4, scoreP1[0]);
        //            signList.add(5, playRun);
        //            player.setValue(signList);
        //            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        //            startActivity(intent);
        //        }
        //    }
        //}.start();

        if (playerName != null) {

            player.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String number = dataSnapshot.child("0").getValue(String.class);
                    String name = dataSnapshot.child("1").getValue(String.class);
                    statutP1[0] = dataSnapshot.child("2").getValue(Integer.class);
                    String sign = dataSnapshot.child("3").getValue(String.class);
                    scoreP1[0] = dataSnapshot.child("4").getValue(Integer.class);
                    playRun = dataSnapshot.child("5").getValue(Integer.class);
                    TextView getScoreP1 = findViewById(R.id.scoreP1);

                    if (scoreP1[0] != null) {
                        getScoreP1.setText(scoreP1[0].toString());
                    }

                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("APPX", "Failed to read value.", error.toException());
                }
            });

            other.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String name = dataSnapshot.child("1").getValue(String.class);
                    statutP2[0] = dataSnapshot.child("2").getValue(Integer.class);
                    String sign = dataSnapshot.child("3").getValue(String.class);
                    scoreP2[0] = dataSnapshot.child("4").getValue(Integer.class);
                    if (name != null) {
                        TextView getNameP2 = findViewById(R.id.nameP2);
                        getNameP2.setText(name);
                        TextView getScoreP2 = findViewById(R.id.scoreP2);
                        getScoreP2.setText(scoreP2[0].toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("APPX", "Failed to read value.", error.toException());
                }
            });

            pierre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList signList = new ArrayList();
                    signList.add(0, "" + number);
                    signList.add(1, "" + playerName);
                    signList.add(2, playRun);
                    signList.add(3, "Pierre");
                    signList.add(4, scoreP1[0]);
                    signList.add(5, playRun);
                    player.setValue(signList);
                    //yourCountDownTimer.cancel();

                    other.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Integer statut = dataSnapshot.child("2").getValue(Integer.class);
                            String sign = dataSnapshot.child("3").getValue(String.class);
                            if (statut != null) {
                                if (sign.equals("")) {
                                    Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (statut.equals(playRun)) {
                                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
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
            });

            feuille.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList signList = new ArrayList();
                    signList.add(0, "" + number);
                    signList.add(1, "" + playerName);
                    signList.add(2, playRun);
                    signList.add(3, "Feuille");
                    signList.add(4, scoreP1[0]);
                    signList.add(5, playRun);
                    player.setValue(signList);
                    //yourCountDownTimer.cancel();

                    other.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Integer statut = dataSnapshot.child("2").getValue(Integer.class);
                            String sign = dataSnapshot.child("3").getValue(String.class);
                            if (statut != null) {
                                if (sign.equals("")) {
                                    Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (statut.equals(playRun)) {
                                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
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
            });

            ciseaux.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList signList = new ArrayList();
                    signList.add(0, "" + number);
                    signList.add(1, "" + playerName);
                    signList.add(2, playRun);
                    signList.add(3, "Ciseaux");
                    signList.add(4, scoreP1[0]);
                    signList.add(5, playRun);
                    player.setValue(signList);
                    //yourCountDownTimer.cancel();

                    other.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Integer statut = dataSnapshot.child("2").getValue(Integer.class);
                            String sign = dataSnapshot.child("3").getValue(String.class);
                            if (statut != null) {
                                if (sign.equals("")) {
                                    Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (statut.equals(playRun)) {
                                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
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
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.removeValue();
                    prefs.edit().remove("playerName").commit();
                    prefs.edit().remove("playerNumber").commit();
                    prefs.edit().remove("otherNumber").commit();
                    prefs.edit().remove("number").commit();
                    //yourCountDownTimer.cancel();
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });



        }
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
                        // yourCountDownTimer.cancel();
                        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
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