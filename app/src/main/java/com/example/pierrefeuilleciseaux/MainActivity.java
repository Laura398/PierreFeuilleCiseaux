package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pierre = findViewById(R.id.pierre);
        Button feuille = findViewById(R.id.feuille);
        Button ciseaux = findViewById(R.id.ciseaux);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference name = database.getReference("Name");
        DatabaseReference sign = database.getReference("Sign");

        name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String nameValue = dataSnapshot.getValue(String.class);
                Log.d("APPX", "Value is: " + nameValue);
                TextView getName = findViewById(R.id.textView);
                getName.setText("Joueur : " + nameValue);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        sign.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String signValue = dataSnapshot.getValue(String.class);
                Log.d("APPX", "Value is: " + signValue);
                TextView getSign = findViewById(R.id.textView2);
                getSign.setText("Signe : " + signValue);
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
                TextView textView = findViewById(R.id.editName);
                name.setValue(textView.getText().toString());
                sign.setValue("Pierre");

            }
        });

        feuille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.editName);
                name.setValue(textView.getText().toString());
                sign.setValue("Feuille");

            }
        });

        ciseaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.editName);
                name.setValue(textView.getText().toString());
                sign.setValue("Ciseaux");

            }
        });






    }
}