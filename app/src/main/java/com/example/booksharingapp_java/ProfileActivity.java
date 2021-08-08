package com.example.booksharingapp_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonLogout, buttonBooks;


    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonBooks = (Button) findViewById(R.id.btn_gotobokks);
        buttonBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,BookListActivity.class));
            }
        });

        buttonLogout = (Button) findViewById(R.id.btn_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

        // SHOW the USER ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        final TextView textViewWelcomeText = (TextView) findViewById(R.id.tv_welcometext);
        final TextView textViewname = (TextView) findViewById(R.id.tv_name);
        final TextView textViewEmail = (TextView) findViewById(R.id.tv_emailAddress);
        final TextView textViewAge = (TextView) findViewById(R.id.tv_age);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null)
                {
                    String name = userProfile.name;
                    String age = userProfile.age;
                    String email = userProfile.email;

                    textViewWelcomeText.setText("Welcome "+ name +"!");
                    textViewWelcomeText.setTextSize(1,25);
                    textViewAge.setText(age);
                    textViewEmail.setText(email);
                    textViewname.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something went wrong", Toast.LENGTH_LONG).show();
            }
        });


    }



}