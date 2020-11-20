package com.example.androidformsfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity implements AsyncResponseUI, AsyncToastUI {

    public static final String ID = "ID";
    public static final String FULL_NAME = "Full name";
    public static final String USERNAME = "Username";
    public static final String EMAIL_ID = "Email Id";
    public static final String GENDER = "Gender";
    public TextView title, infoDetails;
    Button updateDetails;
    Bundle bidi;
    String getloginUsername, getloginTypeUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bidi = getIntent().getBundleExtra("UserCredentials");
        initializeLayout();
        executeDetails();

    }

    private void executeDetails() {

        String formattedText = "";
        formattedText += ID + ": " + bidi.getString(ID) + "\n";
        formattedText += FULL_NAME + ": " + bidi.getString(FULL_NAME) + "\n";
        formattedText += USERNAME + ": " + bidi.getString(USERNAME) + "\n";
        formattedText += EMAIL_ID + ": " + bidi.getString(EMAIL_ID) + "\n";
        formattedText += GENDER + ": " + bidi.getString(GENDER) + "\n";

        processFinish(formattedText);

    }

    private void initializeLayout() {
        updateDetails = findViewById(R.id.update_info_btn);
        infoDetails = findViewById(R.id.details_user);
        title = findViewById(R.id.text_title_updated);
    }

    public void setInfoDetails(String g) {
        infoDetails.setText(g);
    }

    public void updateMyInfo(View view) {
        getloginTypeUser = getIntent().getStringExtra("table");
        getloginUsername = getIntent().getStringExtra("Identity");

        // Goto to updation operation in database
        Intent i = new Intent(this, UpdateActivity.class);
        i.putExtra(ID, bidi.getString(ID));
        i.putExtra("table", getloginTypeUser);
        i.putExtra("username", getloginUsername);
        startActivity(i);
    }

    public void deleteMyInfo(View view) {
        getloginTypeUser = getIntent().getStringExtra("table");
        getloginUsername = getIntent().getStringExtra("Identity");

        //DROP COLLECTION HERE
       FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection("Information")
                .document(getloginTypeUser)
                .collection("Data")
                .document(bidi.getString(ID))
                .delete();
        showToastMessage("Profile Deleted");
    }

    @Override
    public void processFinish(String output) {
        setInfoDetails(output);
    }

    @Override
    public void showToastMessage(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }
}