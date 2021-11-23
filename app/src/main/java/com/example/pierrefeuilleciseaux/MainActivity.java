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

        Button save = findViewById(R.id.button);
        final String[] nameVar = new String[1];

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
                TextView getName = findViewById(R.id.getName);
                getName.setText("Joueur : " + nameValue);
                nameVar[0] = nameValue;
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
                TextView getSign = findViewById(R.id.getSign);
                getSign.setText("Signe : " + signValue);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView getName = findViewById(R.id.getName);
                EditText setName = findViewById(R.id.setName);
                EditText setSign = findViewById(R.id.setSign);
                if(nameVar[0].equals(getName)) {
                    //
                } else {
                    name.setValue(setName.getText().toString(), setSign.getText().toString());
                }
                sign.setValue(setSign.getText().toString());
            }
        });






    }
}