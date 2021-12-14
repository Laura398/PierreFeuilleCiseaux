package com.example.pierrefeuilleciseaux;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = prefs.getString("playerName", null);
        if (name != null) {
            TextView getName = findViewById(R.id.textView2);
            getName.setText(name);
        } else {
            TextView getName = findViewById(R.id.textView2);
            getName.setText("Joueur : " + name);
        }

        Button pierre = findViewById(R.id.pierre);
        Button feuille = findViewById(R.id.feuille);
        Button ciseaux = findViewById(R.id.ciseaux);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + name);

        if (name != null) {

            player.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String key = dataSnapshot.getKey();
                    String value = dataSnapshot.getValue(String.class);
                    TextView getSign = findViewById(R.id.textView);
                    getSign.setText(key + " : " + value);
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
                    ArrayList<String> pierreList = new ArrayList<String>();
                    pierreList.add("Pierre");
                    pierreList.add("Clicked");
                    player.setValue(pierreList);

                }
            });

            feuille.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.setValue("Feuille");

                }
            });

            ciseaux.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.setValue("Ciseaux");

                }
            });
        } else {
            TextView getName = findViewById(R.id.textView2);
            getName.setText("Joueur : " + name);
        }
    }

    @Override
    public void onBackPressed() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = prefs.getString("playerName", null);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference player = database.getReference("" + name);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment quitter ? Vos informations vont être perdues.")
                .setTitle("Attention !")
                .setPositiveButton("Quitter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        player.removeValue();
                        System.exit(0);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}