package com.example.park4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regVehicleNo, regPassword;
    Button btn_SignUp, backTo_login_btn;
    private ProgressDialog pd;
    private FirebaseAuth Auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Auth = FirebaseAuth.getInstance();

        databaseReference =  FirebaseDatabase.getInstance().getReference();

        btn_SignUp = findViewById(R.id.signup_login);
        backTo_login_btn = findViewById(R.id.back_to_login);
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regVehicleNo = findViewById(R.id.reg_vehicleNo);
        regPassword = findViewById(R.id.reg_password);
        pd = new ProgressDialog(this);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validateVehicleNo() | !validatePassword()) {
                   Toast.makeText(SignUp.this, "Fill all fields...!",Toast.LENGTH_SHORT).show();

               } else {

                   final String name = regName.getEditText().getText().toString().trim();
                   final String username = regUsername.getEditText().getText().toString().trim();
                   final String email = regEmail.getEditText().getText().toString().trim();
                   final String phoneNo = regPhoneNo.getEditText().getText().toString().trim();
                   final String vehicleNo = regVehicleNo.getEditText().getText().toString().trim();
                   final String password = regPassword.getEditText().getText().toString().trim();

                   pd.setMessage("wait");
                   pd.show();

                   Auth.createUserWithEmailAndPassword(email, password)
                           .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   pd.dismiss();
                                   if (task.isSuccessful()) {
                                       Toast.makeText(SignUp.this,"Account creation Successful!",Toast.LENGTH_SHORT).show();

                                       // Sign in success, update UI with the signed-in user's information
                                       UserHelperClass userHelperClass= new UserHelperClass(name, username, email, phoneNo, vehicleNo, password);
                                       FirebaseUser user = Auth.getCurrentUser();
                                       databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                       databaseReference.child(user.getUid()).setValue(userHelperClass);

                                       Log.d("SignUp", "createUserWithEmail:success");

                                       startActivity(new Intent(getApplicationContext(),Login.class));

                                   } else {
                                       // If sign in fails, display a message to the user.
                                       Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                                       Toast.makeText(SignUp.this, "Authentication failed.",
                                               Toast.LENGTH_SHORT).show();

                                   }
                               }
                           });

                }
            }
        });

        backTo_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);

            }
        });
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;

        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;

        } else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            return true;
        }
    }

    private Boolean validateVehicleNo() {
        String val = regVehicleNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regVehicleNo.setError("Field cannot be empty");
            return false;
        } else {
            regVehicleNo.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;

        } else {
            regPassword.setError(null);
            return true;
        }
    }
}
