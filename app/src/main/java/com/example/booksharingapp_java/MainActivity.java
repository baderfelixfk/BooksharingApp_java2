package com.example.booksharingapp_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = (TextView) findViewById(R.id.txt_registerMain);
        register.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.txt_registerMain:
                startActivity(new Intent(this,RegisterUserScreen.class));
                break;

        }

    }
}