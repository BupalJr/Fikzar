package bupaljr.com.fikzar.handyman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import bupaljr.com.fikzar.R;

public class HandymanPacks extends AppCompatActivity implements View.OnClickListener {

    private Button buy50, buy100, buy200, buyAny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_packs);

        buy50 = findViewById(R.id.buy_50_credit_button);
        buy50.setOnClickListener(this);
        buy100 = findViewById(R.id.buy_100_credit_button);
        buy100.setOnClickListener(this);
        buy200 = findViewById(R.id.buy_200_credit_button);
        buy200.setOnClickListener(this);
        buyAny = findViewById(R.id.buy_credit_button);
        buyAny.setOnClickListener(this);


        // Initialize variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.packs);

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
                    case R.id.chats:
                        startActivity(new Intent(getApplicationContext()
                                , HandymanChats.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext()
                                , HandymanAccount.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_50_credit_button:
                startActivity(new Intent(this, HandymanPaymentMethods.class));

            case R.id.buy_credit_button:
                startActivity(new Intent(this, HandymanPaymentMethods.class));

            case R.id.buy_200_credit_button:
                startActivity(new Intent(this, HandymanPaymentMethods.class));

            case R.id.buy_100_credit_button:
                startActivity(new Intent(this, HandymanPaymentMethods.class));

        }
    }
}