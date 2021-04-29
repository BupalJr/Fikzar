package bupaljr.com.fikzar.customer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import bupaljr.com.fikzar.R;

public class SearchActivity extends AppCompatActivity {

    // Initialize variable
    Button displayLocality;
    FusedLocationProviderClient fusedLocationProviderClient;
    LinearLayout llMovingHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        displayLocality = findViewById(R.id.input_locality);

        // Initialize fussedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        llMovingHelp = findViewById(R.id.click_moving_help);

        llMovingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, DisplaySearch.class));
            }
        });
        // Get location
        displayLocality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check permission
                if (ActivityCompat.checkSelfPermission(SearchActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // When permission granted
                    getLocation();
                } else {
                    // When permission denied
                    ActivityCompat.requestPermissions(SearchActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }

            @SuppressLint("MissingPermission")
            private void getLocation() {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // Initialize location
                        Location location = task.getResult();

                        if (location != null) {
                            Geocoder geocoder = new Geocoder(SearchActivity.this
                                    , Locale.getDefault());
                            // Initialize address list
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );

                                // Display the post code
                                displayLocality.setText(Html.fromHtml(
                                        "<font color='#6200EE'><b></b><br></font>"
                                                + addresses.get(0).getPostalCode()
                                ));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }

        });

        // Set  Selected
        bottomNavigationView.setSelectedItemId(R.id.search);

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
                        startActivity(new Intent(getApplicationContext()
                                , AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}