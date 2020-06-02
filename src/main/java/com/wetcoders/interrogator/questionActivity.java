package com.wetcoders.interrogator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class questionActivity extends AppCompatActivity {
    EditText question,op1,op2,op3,op4,co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
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
    }
    public void onClickSave(View view){
        question = findViewById(R.id.editText2);
        op1 = findViewById(R.id.editText3);
        op2 = findViewById(R.id.editText7);
        op3 = findViewById(R.id.editText8);
        op4 = findViewById(R.id.editText9);
        co = findViewById(R.id.editText10);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mainQuestions");
        String idHash="";
        try {
            idHash = SHA256(question.getText().toString());
        }
        catch(NoSuchAlgorithmException ok){

        }
        DatabaseReference myRef1 = myRef.child(idHash);
        myRef1.child("statement").setValue(question.getText().toString());
        myRef1.child("Option1").setValue(op1.getText().toString());
        myRef1.child("Option2").setValue(op2.getText().toString());
        myRef1.child("Option3").setValue(op3.getText().toString());
        myRef1.child("Option4").setValue(op4.getText().toString());
        myRef1.child("ConOp1").setValue(0);
        myRef1.child("ConOp2").setValue(0);
        myRef1.child("ConOp3").setValue(0);
        myRef1.child("ConOp4").setValue(0);

    }
    public static String SHA256 (String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for(byte b: digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
}
    public void onClickClear(View view){
        question = findViewById(R.id.editText2);
        op1 = findViewById(R.id.editText3);
        op2 = findViewById(R.id.editText7);
        op3 = findViewById(R.id.editText8);
        op4 = findViewById(R.id.editText9);
        co = findViewById(R.id.editText10);
        question.setText("");
        op1.setText("");
        op2.setText("");
        op3.setText("");
        op4.setText("");
        co.setText("");
    }

}
