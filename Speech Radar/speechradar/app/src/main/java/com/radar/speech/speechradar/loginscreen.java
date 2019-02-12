package com.radar.speech.speechradar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class loginscreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button loginbutton;
    EditText userEmail;
    EditText userPass;
    TextView createAccountLink;
    TextView forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        loginbutton = (Button)findViewById(R.id.loginbtn);
        userEmail = (EditText) findViewById(R.id.emaillogin) ;
        userPass = (EditText) findViewById(R.id.passwordlogin);
        createAccountLink = (TextView) findViewById(R.id.createaccountlink);
        forgotpassword = (TextView) findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginscreen.this, createaccount.class));
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginscreen.this, forgotPassword.class));
            }
        });



        adddata();

    }

    public void adddata() {
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword(userEmail .getText().toString().trim(),
                        userPass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                        String email = userEmail.getText().toString();
                                        email = email.replace(".", "");
                                        email = email.replace(" ", "");
                                        Intent i = new Intent(loginscreen.this, speechrecognition.class);
                                        i.putExtra("email_var",email);
                                        startActivity(i);

                                    }else{
                                        Toast.makeText(loginscreen.this, "Please verify your email address"
                                                , Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(loginscreen.this, task.getException().getMessage()
                                            , Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }


}

