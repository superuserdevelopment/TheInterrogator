package com.wetcoders.interrogator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class resultsActivity extends AppCompatActivity {
    EditText t11,t12,t21,t22,t31,t32,t41,t42,t51,t52,t61,t62,t71,t72,t81,t82,t91,t92,t101,t102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        t11 = findViewById(R.id.text1x1);
        t21 = findViewById(R.id.text2x1);
        t31 = findViewById(R.id.text3x1);
        t41 = findViewById(R.id.text4x1);
        t51 = findViewById(R.id.text5x1);
        t61 = findViewById(R.id.text6x1);
        t71 = findViewById(R.id.text7x1);
        t81 = findViewById(R.id.text8x1);
        t91 = findViewById(R.id.text9x1);
        t101 = findViewById(R.id.text10x1);
        t12 = findViewById(R.id.text1x2);
        t22 = findViewById(R.id.text2x1);
        t32 = findViewById(R.id.text3x2);
        t42 = findViewById(R.id.text4x2);
        t52 = findViewById(R.id.text5x2);
        t62 = findViewById(R.id.text6x2);
        t72 = findViewById(R.id.text7x2);
        t82 = findViewById(R.id.text8x2);
        t92 = findViewById(R.id.text9x2);
        t102 = findViewById(R.id.text10x2);
        answers();

    }
    //private final String suspects1[]={"audrey","cavendish","cleaner","cook","juan","juana","nick","sushi","suzi","vikram"};
    private String suspects[]={"audrey","cavendish","cleaner","cook","juan","juana","nick","sushi","suzi","vikram"};
    public double confidence[]={0,0,0,0,0,0,0,0,0,0};
    public String highConfidence[] = new String[3];
    public String medConfidence[] = new String[3];
    public String lowConfidence[] = new String[4];

    public void setMultiplier(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        int i;
        for(i=0;i<3;i++){
            ref.child(highConfidence[i]).child("multiplier").setValue(1);
        }
        for(i=0;i<3;i++){
            ref.child(medConfidence[i]).child("multiplier").setValue(0.5);
        }
        for(i=0;i<4;i++){
            ref.child(lowConfidence[i]).child("multiplier").setValue(0.25);
        }
    }
    public void onClickTEST(View view){
        Log.w("TEST","PRINTING VAL");
        for(int i=0;i<10;i++){
            Log.w("Arr",""+vals[i]);
        }
        //sortSuspect();
        sortSuspect();

        assignConfidence();
        display();
        assignConfidence();
        setMultiplier();
    }
    private void assignConfidence(){
        for(int i=0;i<3;i++){
            highConfidence[i]=suspects[i];
            Log.w("High Confidence",""+highConfidence[i]);
            confidence[i] = 1;
        }
        for(int i=0;i<3;i++){
            medConfidence[i]=suspects[i+3];
            Log.w("Medium Confidence",""+medConfidence[i]);
            confidence[i+3] = 0.5;
        }
        for(int i=0;i<4;i++){
            lowConfidence[i]=suspects[i+6];
            Log.w("low Confidence",""+lowConfidence[i]);
            confidence[i+6] = 0.25;
        }
    }
    private void disp(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("users");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //DatabaseReference myRef1 = myRef2.child("questions");
        //StringBuilder sb = new StringBuilder()
    }
    private void display(){
        t11 = findViewById(R.id.text1x1);
        t21 = findViewById(R.id.text2x1);
        t31 = findViewById(R.id.text3x1);
        t41 = findViewById(R.id.text4x1);
        t51 = findViewById(R.id.text5x1);
        t61 = findViewById(R.id.text6x1);
        t71 = findViewById(R.id.text7x1);
        t81 = findViewById(R.id.text8x1);
        t91 = findViewById(R.id.text9x1);
        t101 = findViewById(R.id.text10x1);
        t12 = findViewById(R.id.text1x2);
        t22 = findViewById(R.id.text2x2);
        t32 = findViewById(R.id.text3x2);
        t42 = findViewById(R.id.text4x2);
        t52 = findViewById(R.id.text5x2);
        t62 = findViewById(R.id.text6x2);
        t72 = findViewById(R.id.text7x2);
        t82 = findViewById(R.id.text8x2);
        t92 = findViewById(R.id.text9x2);
        t102 = findViewById(R.id.text10x2);
        t11.setText(suspects[0]);
        t21.setText(suspects[1]);
        t31.setText(suspects[2]);
        t41.setText(suspects[3]);
        t51.setText(suspects[4]);
        t61.setText(suspects[5]);
        t71.setText(suspects[6]);
        t81.setText(suspects[7]);
        t91.setText(suspects[8]);
        t101.setText(suspects[9]);
        t12.setText(vals[0]+" ("+confidence[0]+")");
        t22.setText(vals[1]+" ("+confidence[1]+")");
        t32.setText(vals[2]+" ("+confidence[2]+")");
        t42.setText(vals[3]+" ("+confidence[3]+")");
        t52.setText(vals[4]+" ("+confidence[4]+")");
        t62.setText(vals[5]+" ("+confidence[5]+")");
        t72.setText(vals[6]+" ("+confidence[6]+")");
        t82.setText(vals[7]+" ("+confidence[7]+")");
        t92.setText(vals[8]+" ("+confidence[8]+")");
        t102.setText(vals[9]+" ("+confidence[9]+")");

    }
    private void sortSuspect(){
        int i,j;
        String tmp;
        for(i=0; i < suspects.length; i++){
            for(j=1; j < (suspects.length-i); j++){
                if(vals[j-1] < vals[j]){
                    //swap elements
                    int t = vals[j-1];
                    vals[j-1] = vals[j];
                    vals[j] = t;
                    tmp = suspects[j-1];
                    suspects[j-1] = suspects[j];
                    suspects[j] = tmp;
                }

            }
        }
        Log.w("TEST","PRINTING VAL");
        for(i=0;i<10;i++){
            Log.w("Arr",""+vals[i]+"\t"+suspects[i]);
        }
    }


    private void answers(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("users");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                add(dataSnapshot.child(suspects[0]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[1]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[2]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[3]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[4]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[5]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[6]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[7]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[8]).child("correctAnswers").getValue(Integer.class));
                add(dataSnapshot.child(suspects[9]).child("correctAnswers").getValue(Integer.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }
    int vals[]=new int[10];
    int i=0;
    void add(int a){
        vals[i] = a;
        Log.w("VALS",""+vals[i]);
        i++;
    }
}

