package com.example.conceptcube;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edt_id, edt_pw;
    AppCompatButton btn_login, btn_sign_up;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    MemberAccount account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("MemberAccount");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toMainPage();
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSignUpPage();
            }
        });

    }
    public void findViews() {
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        btn_login = findViewById(R.id.btn_login);
        btn_sign_up= findViewById(R.id.btn_sign_up);
    }


    public void toSignUpPage() {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    public void toMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}