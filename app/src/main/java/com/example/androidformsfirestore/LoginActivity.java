package com.example.androidformsfirestore;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginmeButton;
    EditText logUsername, logPassword;
    String getlogname, getlogPasscode, getloginTypeUser;
    Spinner loginTypeUser;
    AlertDialog alertDialog;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializelayout();
    }

    private void initializelayout() {
        logUsername = findViewById(R.id.login_username);
        logPassword = findViewById(R.id.login_password);
        loginmeButton = findViewById(R.id.loginbtn);
        database = FirebaseFirestore.getInstance();
        loginTypeUser = findViewById(R.id.login_types);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Status:");
        alertDialog.setCancelable(true);
    }

    private void extractInputs() {
        getlogname = logUsername.getText().toString();
        getlogPasscode = logPassword.getText().toString();
        getloginTypeUser = loginTypeUser.getSelectedItem().toString();
    }

    public void fastLogin(View view) {
        //LOGIN USER
        extractInputs();
        credentialsAuthentication();


}

    private void credentialsAuthentication() {
        database.collection("Information").document(getloginTypeUser).collection("Data").whereEqualTo("Username", getlogname).whereEqualTo("Password", getlogPasscode).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String,Object> resultData =new HashMap<>();
                    boolean status=false;
                    for (QueryDocumentSnapshot qd:task.getResult())
                    {
                        resultData=qd.getData();
                        resultData.put("ID", qd.getId());
                        status=true;
                    }
                if(status) {
                    Bundle bundle = new Bundle();
                    for (Map.Entry<String, Object> entry : resultData.entrySet()) {
                        bundle.putString(entry.getKey(), entry.getValue().toString());
                    }
                    Intent act=new Intent(LoginActivity.this, ProfileActivity.class);
                    act.putExtra("UserCredentials",bundle);
                    act.putExtra("table", getloginTypeUser);
                    act.putExtra("Identity", getlogname);
                    startActivity(act);
                    }
                else {
                    alertDialog.setMessage("Invalid Username or Password");
                    alertDialog.show();
                }
                }
            }
        });
    }
}