package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity7 extends AppCompatActivity {
    public int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        final TextView textView = findViewById(R.id.textView2);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerNumber = prefs.getString("playerNumber", null);
        String otherNumber = prefs.getString("otherNumber", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + playerNumber);
        DatabaseReference other = database.getReference("" + otherNumber);

        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                player.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String name = dataSnapshot.child("1").getValue(String.class);

                        if (name != null) {
                            textView.setText(String.valueOf("Votre adversaire a quitté la partie, vous allez être redirigé vers la page d'accueil dans " + counter + " secondes."));
                            counter--;
                        } else {
                            Intent intent = new Intent(MainActivity7.this, MainActivity.class);
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
            public void onFinish() {

                player.removeValue();
                prefs.edit().remove("playerName").commit();
                prefs.edit().remove("playerNumber").commit();
                prefs.edit().remove("otherNumber").commit();
                prefs.edit().remove("number").commit();
                Intent intent = new Intent(MainActivity7.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }.start();
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
                        Intent intent = new Intent(MainActivity7.this, MainActivity.class);
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