package bupaljr.com.fikzar.handyman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.StartChatActivity;
import bupaljr.com.fikzar.customer.DisplaySearch;
import bupaljr.com.fikzar.customer.ForgotPassword;
import bupaljr.com.fikzar.customer.MainActivity;

public class HandymanPaymentMethods extends AppCompatActivity {

    CardView debitCreditCardView;
    ImageView clickBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_payment_methods);

        debitCreditCardView = findViewById(R.id.debit_cardview);

        debitCreditCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HandymanPaymentMethods.this, HandymanCardPayment.class));
            }
        });

        // Go Back Arrow ->
        clickBack = findViewById(R.id.view_back_arrow);
        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HandymanPaymentMethods.this, HandymanPacks.class));
            }
        });
    }
}