package com.example.androidformsfirestore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity implements AsyncResponseUI {

    String getTableName, getLoggingUsername;
    EditText uTDfullname, uTDemail, uTDpassword;
    String getUTDfullname, getUTDemail, getUTDpassword;
    private FirebaseFirestore database;
    private String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        intializeLayout();
        //extractingInputs();
    }

    private void intializeLayout() {
        uTDfullname = findViewById(R.id.updated_fullname);
        uTDemail = findViewById(R.id.updated_email);
        uTDpassword = findViewById(R.id.updated_password);
        database = FirebaseFirestore.getInstance();
    }

    private void extractingInputs() {
        getTableName = getIntent().getStringExtra("table");
        getLoggingUsername = getIntent().getStringExtra("username");
        getID =getIntent().getStringExtra("ID");

        getUTDfullname = uTDfullname.getText().toString();
        getUTDemail = uTDemail.getText().toString();
        getUTDpassword = uTDpassword.getText().toString();
    }

    private void updateInfo(String key,String value) {

        System.out.println("Table name: "+getTableName);
        System.out.println("getID: "+getID);
        database.collection("Information").document(getTableName).collection("Data").document(getID).update(key,value).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    System.out.println("QUERY EXECUTED_--------------------------------------->");
                }
            }
        });
    }

    @Override
    public void processFinish(String output) {
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        finish();
    }

    public void pingthisInfo(View view) {

        extractingInputs();

        if(!getUTDfullname.isEmpty()) {
            updateInfo("Full name",getUTDfullname);
        }
        if(!getUTDemail.isEmpty()) {
            updateInfo("Email Id",getUTDemail);
        }
        if(!getUTDpassword.isEmpty()) {
            updateInfo("Password",getUTDpassword);
        }
        Toast.makeText(UpdateActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}