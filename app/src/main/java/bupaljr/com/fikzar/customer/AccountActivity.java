package bupaljr.com.fikzar.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.SplashScreen;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Go to the profile ->
        Object clickProfile = findViewById(R.id.click_profile);
        ((View) clickProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, UserProfileActivity.class));
            }
        });

        // Go to the hires ->
        Object clickHires = findViewById(R.id.click_hires);
        ((View) clickHires).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, JobsActivity.class));
            }
        });

        // Go to the chats ->
        Object clickChats = findViewById(R.id.click_chats);
        ((View) clickChats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, ChatsActivity.class));
            }
        });

        // -------------------------------------------------------//
        // Go to the Settings ->
        Object clickSettings = findViewById(R.id.click_settings);
        ((View) clickSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, SettingsActivity.class));
            }
        });

        // Go to the About us ->
        Object clickAboutUs = findViewById(R.id.click_about_us);
        ((View) clickAboutUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, AboutUsActivity.class));
            }
        });


        // Logout the account ->
        Object clickLogout = findViewById(R.id.click_logout);
        ((View) clickLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AccountActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountActivity.this, SplashScreen.class));
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
                                , DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                , SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.hire:
                        startActivity(new Intent(getApplicationContext()
                                , JobsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chats:
                        startActivity(new Intent(getApplicationContext()
                                , ChatsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:

                }
                return false;
            }
        });
    }
}