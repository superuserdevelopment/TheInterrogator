package com.wetcoders.interrogator;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Button b = findViewById(R.id.button4);
        b.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    String usernameFinal="";
    String passwordFinal="";
    //EditText username = findViewById(R.id.usernameEditText);
    //EditText password = findViewById(R.id.passwordEditText);
    public void onClickMain(View view){
        Intent intent = new Intent(this,mainQuestionare.class);
        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.editText4);
        if(validator(username.getText().toString(),password.getText().toString())) {
            DatabaseReference df1 = database.getReference("current");
            df1.child("user").setValue(username.getText().toString());
            df1.child("pass").setValue(password.getText().toString());
            startActivity(intent);
        }

    }
    public void resetConfidence(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("mainQuestions");
        for(int i = 0;i<hashesMain.length;i++){
            ref.child(hashesMain[i]).child("ConOp1").setValue(0);
            ref.child(hashesMain[i]).child("ConOp2").setValue(0);
            ref.child(hashesMain[i]).child("ConOp3").setValue(0);
            ref.child(hashesMain[i]).child("ConOp4").setValue(0);
        }
    }
    public void onClickLogin(View view){
        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.editText4);
        Intent intent = new Intent(this, TestActivity.class);
        if(username.getText().toString().equals("") || password.getText().toString().equals("")) {

            alertBuilder("Error","Username/Password is missing");
        }
        if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
            Intent intent1 = new Intent(this, resultsActivity.class);
            Button b = findViewById(R.id.button4);
            b.setEnabled(true);
            startActivity(intent1);
        }
        else {
            if(validator(username.getText().toString(),password.getText().toString())) {
                DatabaseReference df1 = database.getReference("current");
                df1.child("user").setValue(username.getText().toString());
                df1.child("pass").setValue(password.getText().toString());
                startActivity(intent);
            }
        }
    }
    String actPass="";
    private Boolean validator(String user, String pass){
        DatabaseReference myRef2 = database.getReference("users");
        //DatabaseReference myRef1 = myRef2.child("questions");
        TextView userVi = findViewById(R.id.userView);
        userVi.setText(user);

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //String actuser = dataSnapshot.child(user).child("Name").getValue(String.class);//.toString();
                TextView userVi = findViewById(R.id.userView);
                TextView passVi = findViewById(R.id.passView1);
                passVi.setText(dataSnapshot.child(userVi.getText().toString()).child("Password").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                alertBuilder("Error","Invalid id");
            }
        });
        TextView pi = findViewById(R.id.passView1);
        if(pass.equals(pi.getText().toString())) {

            alertBuilder("Interrogator", "Login Successful");
            return true;
        }
        return false;
    }
    public void alertBuilder(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.create();
        builder.show();
    }
    public void onClickEdit(View view){
        Intent intent = new Intent(this, questionActivity.class);
        startActivity(intent);
    }
    public void onClickNew(View view){
        DatabaseReference myRef2 = database.getReference("questions");
        //DatabaseReference myRef1 = myRef2.child("questions");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.child("id").getValue(String.class);//.toString();
                Log.w("error", "Val: " + value);
                EditText password1 = findViewById(R.id.usernameEditText);
                password1.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                EditText password1 = findViewById(R.id.usernameEditText);
                password1.setText("Error");
                            }
        });


    }


}
