package com.example.booksharingapp_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//TODO: Make Layout relative, edit activity main

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewRegister, resetPassword;
    private Button buttonSignin;
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewRegister = (TextView) findViewById(R.id.txt_registerMain);
        textViewRegister.setOnClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //THis is a little workaround to re initialize the keyboard for the correct background
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        buttonSignin  = (Button) findViewById(R.id.btn_login);
        buttonSignin.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.etxt_EmailAddress);
        editTextPassword = (EditText)  findViewById(R.id.etxt_Password);

        resetPassword = (TextView) findViewById(R.id.txt_forgotpasswordMain);
        resetPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    // This function checks for a valid email address, true or false
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.txt_registerMain:
                startActivity(new Intent(this,BookListActivity.class));
                break;
            case R.id.btn_login:
                userLogin();
                break;
            case R.id.txt_forgotpasswordMain:
                startActivity(new Intent(this, ResetPassword.class));
                break;

        }

    }



    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("Please enter a email");
            editTextEmail.requestFocus();
            return;
        }
        if(!isValidEmailAddress(email))
        {
            editTextEmail.setError("Please enter valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPassword.setError("Password must be at least 6 chars");
            editTextPassword.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextPassword.setError("Please enter a password");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {

                    // Check if email validation ok
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                    /*if(user.isEmailVerified())
                    {
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }
                    else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verifiy your account", Toast.LENGTH_LONG).show();
                    }*/


                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}