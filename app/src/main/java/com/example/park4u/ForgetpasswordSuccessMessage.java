package com.example.park4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetpasswordSuccessMessage extends AppCompatActivity {

    Button  login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword_success_message);

        login_btn = findViewById(R.id.btn_LoginBack);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetpasswordSuccessMessage.this, Login.class);
                startActivity(intent);

            }
        });
    }
}