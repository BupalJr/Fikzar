package bupaljr.com.fikzar.handyman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.customer.ForgotPassword;
import bupaljr.com.fikzar.customer.MainActivity;

public class HandymanCardPayment extends AppCompatActivity {

    ImageView clickBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_card_payment);

        // Go Back Arrow ->
        clickBack = findViewById(R.id.view_back_arrow);
        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HandymanCardPayment.this, HandymanPaymentMethods.class));
            }
        });
    }
}