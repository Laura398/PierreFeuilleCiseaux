package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

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
                String sign = dataSnapshot.child("3").getValue(String.class);
                if (statut != null) {
                    if (statut.equals("Clicked")) {
                        if (sign.equals("Sign")) {

                        } else {
                            other.removeEventListener(this);
                            Intent intent = new Intent(MainActivity5.this, MainActivity3.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                    }
                }

                String number = dataSnapshot.child("0").getValue(String.class);

                if (number == null) {
                    other.removeEventListener(this);
                    Intent intent = new Intent(MainActivity5.this, MainActivity7.class);
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
                        Intent intent = new Intent(MainActivity5.this, MainActivity.class);
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