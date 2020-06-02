package com.wetcoders.interrogator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {
    EditText quest;
    TextView registerInput;
    Button op1,op2,op3,op4;
    boolean lastQuest = false;
    private int ind = 0;
    private String hashes[]={"51ee67e1a319017b7af4f972e4410e458ddb8a83696f76da558220972e985ee4",
            "54c7c1561fad0d1b7d616157c7c28e7f3b90174a5524ad77685aea58a2f725f0",
            "5bab2436e02b986e3e169218291839583ed871a11e4f63f0dc493c904e001503","709e0a0efc83f1ef423eb0b884b478162327a75e57d62a42817fdf9cd24374b0"
            ,"ce31401a53bbfd010036870cf65f7b96d66db1f8c18b2bad8fcf68844347771d","baeade46693e154bde50d2169c4b5724fab5941e98a92c13abb936e043e28514"
            ,"e5ab3faf84fc6d87ef041b801014fa3bde2aa39706f1ea523d1369face0dbdef","ed492025ab46815245a60de67215c761c0b0744a43164ffb1faccce5557cf2dc"};

    String correctAns;
    String user;
    String pass;
    int corr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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
        quest = findViewById(R.id.questionBox);
        op1 = findViewById(R.id.optionButton1);
        op2 = findViewById(R.id.optionButton2);
        op3 = findViewById(R.id.optionButton3);
        op4 = findViewById(R.id.optionButton4);
        lastQuest = false;
        registerInput = findViewById(R.id.textView5);
        MainActivity ob = new MainActivity();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref2 = database.getReference("current");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView t1 = findViewById(R.id.passView1);
                t1.setText(dataSnapshot.child("user").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                alertBuilder("Error","Invalid id");
            }
        });

        pass = "";
        corr=0;
        fetchQuestion();
    }
    public void onClickOp1(View view){
        validator(1);
    }
    public void onClickOp2(View view){
        validator(2);
    }
    public void onClickOp3(View view){
        validator(3);
    }
    public void onClickOp4(View view){
        validator(4);
    }
    private void validator(int id){
        String exp = "";
        if(lastQuest)
            alertBuilder("The Interrogator","Screening Test successfully completed");
        registerInput.setVisibility(View.VISIBLE);
        switch(id){
            case 1:
                exp = op1.getText().toString();
                break;
            case 2:
                exp = op2.getText().toString();
                break;
            case 3:
                exp = op3.getText().toString();
                break;
            case 4:
                exp = op4.getText().toString();
                break;
                default:
                    MainActivity ob = new MainActivity();
                    ob.alertBuilder("Error","Exception invalid option");
                break;
        }
        if(exp.equals(correctAns)){
            //alertBuilder("Congratulations","Correct Answer");
            corr++;
        }
        fetchQuestion();
    }
    public void alertBuilder(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.create();
        builder.show();
    }
    public void fetchQuestion(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref;
        registerInput.setVisibility(View.INVISIBLE);
        correctAns="";
        TextView t1 = findViewById(R.id.passView1);
        user = t1.getText().toString();

        if(ind<hashes.length) {
            ref = database.getReference("questions");
            ref = ref.child(hashes[ind]);
            ind++;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    quest.setText(dataSnapshot.child("statement").getValue(String.class));//.toString();
                    op1.setText(dataSnapshot.child("Option1").getValue(String.class));
                    op2.setText(dataSnapshot.child("Option2").getValue(String.class));
                    op3.setText(dataSnapshot.child("Option3").getValue(String.class));
                    op4.setText(dataSnapshot.child("Option4").getValue(String.class));
                    correctAns = dataSnapshot.child("CorrectOption").getValue().toString();
                    //Log.w("error", "Val: " + value);
                    //EditText password1 = findViewById(R.id.usernameEditText);
                    //password1.setText(value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    MainActivity ob = new MainActivity();
                    ob.alertBuilder("Error","Failed to connect to database");
                }
            });
        }

        if(ind == hashes.length){
            DatabaseReference ref1 = database.getReference("users");
            ref1 = ref1.child(user);
            lastQuest = true;
            //alertBuilder("hello",user);
            ref1.child("correctAnswers").setValue(corr);
            final DatabaseReference ref2 = database.getReference("ScreeningTest");
                ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(corr>dataSnapshot.child("max").getValue(Integer.class)){
                        ref2.child("max").setValue(corr);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    alertBuilder("Error","Invalid id");
                }
            });
        }
    }

}
