package bupaljr.com.fikzar.handyman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.SplashScreen;
import bupaljr.com.fikzar.customer.AccountActivity;
import bupaljr.com.fikzar.customer.SettingsActivity;

public class HandymanAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_account);


        // Go to the Settings ->
        Object clickSettings = findViewById(R.id.click_settings);
        ((View) clickSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HandymanAccount.this, HandymanSettings.class));
            }
        });


        // Logout the account ->
        Object clickLogout = findViewById(R.id.click_logout);
        ((View) clickLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HandymanAccount.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HandymanAccount.this, SplashScreen.class));
            }
        });
        // Initialize variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.account);

        //Perform ItemSelectedListener bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HandymanDashboard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.jobs:
                        startActivity(new Intent(getApplicationContext()
                                , HandymanJobs.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.packs:
                        startActivity(new Intent(getApplicationContext()
                                , HandymanPacks.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chats:
                        startActivity(new Intent(getApplicationContext()
                                , HandymanChats.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
//                        startActivity(new Intent(getApplicationContext()
//                                , HandymanAccount.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                }
                return false;
            }
        });
    }
}