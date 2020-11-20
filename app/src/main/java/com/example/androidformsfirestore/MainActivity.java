package com.example.androidformsfirestore;

/**-- This Application contains all the operations required
 *   to perform DML operations in Database <MySQL>
 *   @Server: WAMP Server
 *   @Database: Reference Languages: pHp and MySQL
 **/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    public static final String GENDER = "Gender";
    public static final String EMAIL_ID = "Email Id";
    public static final String USERNAME = "Username";
    public static final String FULL_NAME = "Full name";
    public static final String PASSWORD = "Password";
    EditText fullname, username, email, password, confirmPassword, gender;
    String getSelectedUser, getfullname, getusername, getemail, getpassword, getconfirmPassword, getgender;
    FirebaseFirestore database;
    Spinner userType;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();


    }

    private void extractInputs() {
        getfullname = fullname.getText().toString();
        getusername = username.getText().toString();
        getemail = email.getText().toString();
        getpassword = password.getText().toString();
        getconfirmPassword = confirmPassword.getText().toString();
        getgender = gender.getText().toString();

        getSelectedUser = userType.getSelectedItem().toString();

    }

    private void initializeViews() {
        database =FirebaseFirestore.getInstance();
        fullname = findViewById(R.id.full_name);
        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass_word);
        confirmPassword = findViewById(R.id.confirm_password);
        gender = findViewById(R.id.gender_input);
        userType = findViewById(R.id.input_types);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Information");
        alertDialog.setCancelable(true);
    }

    public void loginOperation(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void registerOperation(View view) {
     // REGISTER USER
        extractInputs();

        Map<String,Object> map=new HashMap<>();
        map.put(FULL_NAME, getfullname);
        map.put(USERNAME, getusername);
        map.put(EMAIL_ID, getemail);

        if(!getpassword.equals(getconfirmPassword))
        { Toast.makeText(this, "Password not matched", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            map.put(PASSWORD, getpassword);
        }
        map.put(GENDER, getgender);
        performValidation(map);




    }

    private void performValidation(Map<String, Object> map) {
        database.collection("Information").document(String.valueOf(getSelectedUser)).collection("Data").add(map)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()) {
                            alertDialog.setMessage("Registration done");
                        }
                        else {
                            alertDialog.setMessage("Error: "+task.getException().getMessage());
                        }
                        alertDialog.show();
                    }
                });
    }

}