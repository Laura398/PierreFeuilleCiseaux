package com.example.pierrefeuilleciseaux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

        final com.example.listview.CustomAdapter[] adapter = new com.example.listview.CustomAdapter[1];
        ArrayList<TextSwitch> tsList = new ArrayList();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playerName = prefs.getString("playerName", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pierre-feuille-ciseaux-a00d3-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference score = database.getReference("Score");

        String objectTest = score.child("" + playerName).get().toString();
        TextView title = findViewById(R.id.title);
        title.setText(objectTest);
;
        score.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String object = dataSnapshot.child("" + playerName).getValue().toString();

                String getArrayInStringArray1 = object.split("\\{")[1];
                String getArrayInStringArray2 = getArrayInStringArray1.split("\\}")[0];

                String data[] = getArrayInStringArray2.split(", ");



                for (int i=0; i < data.length; i++)
                {
                    TextSwitch ts = new TextSwitch();
                    String[] getOneTest = data[i].split("=");
                    String object2 = dataSnapshot.child("" + getOneTest[0]).child("" + playerName).getValue().toString();
                    ts.setText(playerName + getOneTest[1] + getOneTest[0] + object2);
                    tsList.add(ts);
                    TextView title = findViewById(R.id.title);
                    title.setText(playerName + getOneTest[1] + getOneTest[0] + object2);
                }






                // Iterable otherPlayerNames =

                //for (Object otherPlayerName : object) {
                //    Integer playerScore = dataSnapshot.child("" + playerName).child("" + otherPlayerName).child("" + playerName).getValue(Integer.class);
                //    Integer otherScore = dataSnapshot.child("" + otherPlayerName).child("" + playerName).child("" + otherPlayerName).getValue(Integer.class);
                //    TextSwitch ts = new TextSwitch();
                //    ts.setText(playerName + " " + playerScore.toString() + " - " + otherScore.toString() + " " + otherPlayerName.toString());
                //    tsList[0].add(ts);
                //}



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("APPX", "Failed to read value.", error.toException());
            }
        });

        ListView list = findViewById(R.id.listview);
        list.setAdapter(adapter[0]);







    }
}