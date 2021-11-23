package com.example.conceptcube;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class JoinActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbReference;
    MemberAccount account;
    EditText edt_id,edt_pw,edt_pw2, edt_email, edt_bday;
    DatePicker dp_bday;
    Button btn_id_check, btn_register, btn_back;
    String id,pw,pw2,email;
    Date bday;
    ScrollView scroll_view;
    ConstraintLayout parent_layout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        findViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp_bday.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    getAndDisplayDate();
                }
            });
        } else {
            getAndDisplayDate();
        }

        scroll_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginPage();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = edt_id.getText().toString();
                pw = edt_pw.getText().toString();
                pw2 = edt_pw2.getText().toString();
                email = edt_email.getText().toString();

                if (isValid()) {
                    try {
                        addDatatoFirebase();
                        toMainPage();
                        toast("Registered successfully");
                    }
                    catch (Exception e){
                        toast(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void addDatatoFirebase() {
        // below 2 lines of code is used to set
        // data in our object class.
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("account");
        account = new MemberAccount(id,pw,email,bday);
        dbReference.setValue(account);
        // we are use add value event listener method
        // which is called with database reference.
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                MemberAccount account1 = snapshot.getValue(MemberAccount.class);
                toast(account1.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                toast("Failed to add data\n" + error);
            }
        });

    }


    public void findViews() {
        parent_layout = findViewById(R.id.parent_layout);
        scroll_view = findViewById(R.id.scroll_view);
        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        edt_pw2 = findViewById(R.id.edt_pw2);
        edt_email = findViewById(R.id.edt_email);
        edt_bday = findViewById(R.id.edt_bday);
        dp_bday = findViewById(R.id.dp_bday);
        btn_id_check = findViewById(R.id.btn_id_check);
        btn_back = findViewById(R.id.btn_back);
        btn_register= findViewById(R.id.btn_register);
    }
    public void getAndDisplayDate() {
        bday = new Date(dp_bday.getYear()-1900,dp_bday.getMonth(),dp_bday.getDayOfMonth());
        edt_bday.setText(new SimpleDateFormat("dd/MM/yyyy").format(bday));
    }


    public boolean isValid() {
        if (pw.equals(pw2))
            return true;
        return false;
    }
    public void toLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void toMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void toast(String msg) {
        Toast.makeText(JoinActivity.this, msg , Toast.LENGTH_SHORT).show();
    }
}

