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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserScreen extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView btn_registeruser, banner;
    private EditText et_Name, et_Age, et_Email, et_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_screen);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



        mAuth = FirebaseAuth.getInstance();




        banner = (TextView) findViewById(R.id.txt_MainAppName2);
        banner.setOnClickListener(this);
        btn_registeruser = (Button) findViewById(R.id.btn_register);
        btn_registeruser.setOnClickListener(this);
        et_Name = (EditText)  findViewById(R.id.etxt_FullNameRegister);
        et_Age = (EditText)  findViewById(R.id.etxt_AgeRegister);
        et_Email = (EditText)  findViewById(R.id.etxt_emailAddressRegister);
        et_password = (EditText)  findViewById(R.id.etxt_passwordRegister);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_MainAppName2:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }



    public void registerUser()
    {
        String email = et_Email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String age = et_Age.getText().toString().trim();
        String name = et_Name.getText().toString().trim();

        if(name.isEmpty())
        {
            et_Name.setError("Name required");
            et_Name.requestFocus();
            return;
        }
        if(age.isEmpty() )
        {
            et_Age.setError("Age required");
            et_Age.requestFocus();
            return;
        }
        if(!isNumeric(age))
        {
            et_Age.setError("Please enter a number");
            et_Age.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            et_password.setError("Password required");
            et_password.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            et_Email.setError("Email required");
            et_Email.requestFocus();
            return;
        }
        if(!isValidEmailAddress(email))
        {
            et_Email.setError("Enter valid email");
            et_Email.requestFocus();
            return;
        }
        // Check for valid email
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        if(!email.matches(emailPattern))
//        {
//            et_Email.setError("Please enter valid email");
//            et_Email.requestFocus();
//            return;
//
//        }
        // Check for password a bit
        if(password.length() < 6)
        {
            et_password.setError("Password must be at least 6 chars");
            et_password.requestFocus();
            return;
        }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                User user = new User(name, age, email);

                                FirebaseDatabase.getInstance().getReference("users")
                                        .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterUserScreen.this, "User han been registrated", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegisterUserScreen.this, "User registration failed", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });

                            }
                        }
                    });


    }

}