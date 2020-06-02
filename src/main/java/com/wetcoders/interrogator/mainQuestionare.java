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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class mainQuestionare extends AppCompatActivity {
    EditText questBox;
    Button op1,op2,op3,op4;
    boolean lastQuest = false;
    public String user="";
    private double mul=0,confi=0;
    private int ind=1;
    private String hashesMain[]={"01812cdf95cda6e7feb5c34170dc0bd153918bb9714232ada94c865aeae2b046","18596c4062307fc059188b03a609d8b09ba3765700ee35239796091de930db72"
            ,"29b7dba136e44018ef4f6b084a7f02a6a46dffb3688e2e109905ae3d023718d7","2dac4dc0658db9630017536808b8e09048f9c0e62b4034a754c6c9a43b78bd86",
            "3698a57b198d90fef92c3b73184c1d9e0b1ca4867815583f4b8e92c4367798c6","36e5c0260d7015fc239328fc20f65e1e28b3ae697352f904943f7c20c682193a",
            "3d6f0e1ac9b9e84873908632b3ed4b970f1ddf9713b97366c0c321b837b750ee","551d34003da2639c8da738be06325cc8034e96242353c1bcb31f06add30021d5",
            "87e795208c0deead59fc8191012bda679a7b18565c04e7bf828a8a136b4d13bc","8b6d805a2687fefe7540338891b07bc17991732f5c8a232c9985d7762e9c34a6",
            "8d4db8eb0d93dc60d22dcb3899b96700918b469a4fb0b4d5246d63d34ea22900","9cdec93749109e7f0e522907df069c6cd706d2444e1e7043018a9fbc637c94d6",
            "a5ffb66468dd1e332dd9bda32ad1c7d724b42da69f3d8b0ef1dcedcb9502fc46","a7a086e4fe189e1ec256a1d787f187f078353ede2cf392865b897daefaa4b7ab",
            "b2f2e9ae087bf8185cfe1833e2ef4cc5de76faf314c8e88bf45082fa76634e3e","be0b33e3e636f205f86578222eb7fc7afad60fd24495dcc153ad211bac886363",
            "be1ce411384dba19f8315ab1ba83446ba8e6f0f47953ca52a1e7f9aeff5a6133","c8cdac50848c1e1e881fc251afa09fde3b4c6b41fe2c0e57bab340239b9b6a5a",
            "d33963e97fc71c2a84403e9c8cdf22ab3f4c4e69f235c39de77bfa9c2c504284","d57edfe4a3b20a1c4e4d7052190a5d457722795a15aaaed050fcea4ac2105fa0",
            "d631b461cdc825c2f419174a7f76d16a3c555e8142e2269a7b3d01576ca0ad9d","eb69aafc5ae0d2b45354915cb62c58c52cf5cec71806649f40f1f9a99554759a",
            "f3c68113f544575f3b923179d7c0382d75b03ccd85199a1530821708da9ce8e0","f4f25414691fb304a5204c2f230f60db928e50c5df6cdc98ef40bcdbc3645877"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_questionare);
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
        String tmp = getQuestions();
        ind = 1;
        lastQuest = false;
    }
    public void alertBuilder(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.create();
        builder.show();
    }
    public void onHelloClickOp1(View view){
        validator(1);
    }
    public void onHelloClickOp2(View view){
        validator(2);
    }
    public void onHelloClickOp3(View view){
        validator(3);
    }
    public void onHelloClickOp4(View view){
        validator(4);
    }
    private void storeUser(String s){
        user = s;
    }
    private void storeMultiplier(double n){
        mul = n;
    }
    private void storeCon(double n){
        confi = n;
    }
    String prevHash = "01812cdf95cda6e7feb5c34170dc0bd153918bb9714232ada94c865aeae2b046";
    private void validator(int op){
        if(lastQuest)
            alertBuilder("The Interrogator","Main Questionnaire successfully completed");
        //confi = 0;
        Log.w("HASH","VALUE"+prevHash);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("mainQuestions");
        DatabaseReference u = database.getReference("current");
        u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.child("user").getValue(String.class);
                storeUser(s);
                Log.w("User",""+s);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                MainActivity ob = new MainActivity();
                ob.alertBuilder("Error","Failed to connect to database");
            }
        });
        u = database.getReference("users");
        u.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w("USER IS ",user);
                double a;
                if(dataSnapshot.child(user).child("multiplier").getValue(Double.class)!=null){
                    a = dataSnapshot.child(user).child("multiplier").getValue(Double.class);
                    Log.w("MULTIPLIER  IS ",""+a);
                    storeMultiplier(a);
                }

                //String b = dataSnapshot.child(user).child("Name").getValue(String.class);

                //
                //Log.w("MULTIPLIER",""+a);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                MainActivity ob = new MainActivity();
                ob.alertBuilder("Error","Failed to connect to database");
            }
        });
        //double confidence = 0;
        String s1 = "";

        switch(op){
            case 1:
                //hash = getQuestions();
                ref = ref.child(prevHash).child("ConOp1");
                s1="ConOp1";
                break;
            case 2:
                //hash = getQuestions();
                ref = ref.child(prevHash).child("ConOp2");
                s1="ConOp2";
                break;
            case 3:
                //hash = getQuestions();
                ref = ref.child(prevHash).child("ConOp3");
                s1="ConOp3";
                break;
            case 4:
                //hash = getQuestions();
                ref = ref.child(prevHash).child("ConOp4");
                s1="ConOp4";
                break;
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double con = dataSnapshot.getValue(Double.class);
                storeCon(con);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference ref1= database.getReference("mainQuestions");
        ref1 = ref1.child(prevHash).child(s1);
        Log.w("Config = ",""+confi);
        ref1.setValue(confi+mul);
        confi = 0;
        //prevHash = hash;
        prevHash=getQuestions();
    }

    private String getQuestions(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref;
        //correctAns="";
        //TextView t1 = findViewById(R.id.passView1);
        //String user = t1.getText().toString();
        if(ind<hashesMain.length) {
            ref = database.getReference("mainQuestions");
            ref = ref.child(hashesMain[ind]);
            Log.w("Hello","Hash is "+hashesMain[ind]);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    questBox = findViewById(R.id.questionBox3);
                    op1 = findViewById(R.id.op1Button);
                    op2 = findViewById(R.id.op2Button);
                    op3 = findViewById(R.id.op3Button);
                    op4 = findViewById(R.id.op4Button);
                    questBox.setText(dataSnapshot.child("statement").getValue(String.class));//.toString();
                    op1.setText(dataSnapshot.child("Option1").getValue(String.class));
                    op2.setText(dataSnapshot.child("Option2").getValue(String.class));
                    op3.setText(dataSnapshot.child("Option3").getValue(String.class));
                    op4.setText(dataSnapshot.child("Option4").getValue(String.class));
                    //correctAns = dataSnapshot.child("CorrectOption").getValue().toString();
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
        if(ind == hashesMain.length){
            lastQuest = true;
        }
        ind++;
        return hashesMain[ind-1];

    }
    }


