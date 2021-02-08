package bupaljr.com.fikzar.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.SplashScreen;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        // Go to the profile ->
        Object clickBack = findViewById(R.id.view_back_arrow);
        ((View) clickBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this, AccountActivity.class));
            }
        });

        // Logout the account ->
        Object clickLogout = findViewById(R.id.click_logout);
        ((View) clickLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(UserProfileActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserProfileActivity.this, SplashScreen.class));
            }
        });
    }
}