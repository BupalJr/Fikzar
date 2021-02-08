package bupaljr.com.fikzar.handyman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import bupaljr.com.fikzar.R;

public class ForgotPasswordHandyman extends AppCompatActivity {

    FirebaseAuth auth;
    private EditText inputForgotEmail;
    private Button btnSubmit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputForgotEmail = (EditText) findViewById(R.id.input_forgot_email);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }

        });
    }

    private void resetPassword() {
        String email = inputForgotEmail.getText().toString().trim();

        if (email.isEmpty()) {
            inputForgotEmail.setError("Email is required!");
            inputForgotEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputForgotEmail.setError("Please provide valid email");
            inputForgotEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordHandyman.this, "Check you email to reset your password!", Toast.LENGTH_LONG).show();
                    ;
                } else {
                    Toast.makeText(ForgotPasswordHandyman.this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}









