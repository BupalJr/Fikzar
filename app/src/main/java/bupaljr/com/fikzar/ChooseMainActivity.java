package bupaljr.com.fikzar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bupaljr.com.fikzar.customer.MainActivity;
import bupaljr.com.fikzar.handyman.HandymanMainActivity;

public class ChooseMainActivity extends AppCompatActivity {


    Button btnLookingHandyman, btnIAmHandyman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_main);

        btnIAmHandyman = findViewById(R.id.btn_i_am_handyman);
        btnLookingHandyman = findViewById(R.id.btn_looking_handyman);


        // To Customer dashboard
        btnLookingHandyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMainActivity.this, MainActivity.class));
            }
        });

        // To handyman dashboard
        btnIAmHandyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMainActivity.this, HandymanMainActivity.class));
            }
        });
    }
}