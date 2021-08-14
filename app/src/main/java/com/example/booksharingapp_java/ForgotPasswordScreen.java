package com.example.booksharingapp_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

//TODO: Make Layout relative, edit reset password xml

public class ForgotPasswordScreen extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextEmailPasswordReset;
    private Button buttonResetPassword;

    private FirebaseAuth mAuth;

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword_screen);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //THis is a little workaround to re initialize the keyboard for the correct background
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmailPasswordReset = (EditText) findViewById(R.id.etxt_emailAddress);

        buttonResetPassword = (Button) findViewById(R.id.btn_sendverificiation);
        buttonResetPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword()
    {
        String email = editTextEmailPasswordReset.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmailPasswordReset.setError("Please enter email");
            editTextEmailPasswordReset.requestFocus();
            return;
        }
        if(!isValidEmailAddress(email))
        {
            editTextEmailPasswordReset.setError("Please enter valid email");
            editTextEmailPasswordReset.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPasswordScreen.this,"Check your email to reset your password.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ForgotPasswordScreen.this,"Something went wrong. Try again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_forgotpasswordText:
                startActivity(new Intent(ForgotPasswordScreen.this, LoginScreen.class));
        }

    }
}