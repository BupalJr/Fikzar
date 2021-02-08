package bupaljr.com.fikzar.handyman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.customer.DashboardActivity;

public class HandymanMainActivity extends AppCompatActivity implements View.OnClickListener {

    // Initialize variable
    private TextView register, forgotPassword;
    private EditText inputLoginEmail, inputPassword;
    private Button btnLogin;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = findViewById(R.id.textView_register);
        register.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.textView_forgot_password);
        forgotPassword.setOnClickListener(this);

        inputLoginEmail = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);

        mAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_register:
                startActivity(new Intent(this, RegisterHandyman.class));
            case R.id.btn_login:
                userLogin();
                break;
            case R.id.textView_forgot_password:
                startActivity(new Intent(this, ForgotPasswordHandyman.class));
        }

    }

    private void userLogin() {
        String email = inputLoginEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (email.isEmpty()) {
            inputLoginEmail.setError("Email is required!");
            inputLoginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputLoginEmail.setError("Please enter a valid email");
            inputLoginEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            inputLoginEmail.setError("Password is required");
            inputLoginEmail.requestFocus();
            return;
        }

        if (password.length() < 6) {
            inputPassword.setError("Min password length is a 6 characters");
            inputPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Sign in through firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        //Redirect to the search dashboard
                        startActivity(new Intent(HandymanMainActivity.this, DashboardActivity.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(HandymanMainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(HandymanMainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }
}