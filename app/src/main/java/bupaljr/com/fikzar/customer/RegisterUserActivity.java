package bupaljr.com.fikzar.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.handyman.HandymanMainActivity;
import bupaljr.com.fikzar.handyman.RegisterHandyman;
import bupaljr.com.fikzar.model.User;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSignUp;

    private EditText editTextFullName, editTextUserName, editTextEmail, editTextPassword;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    ImageView clickBack;

    DatabaseReference MainPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        mAuth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btn_submit);
        btnSignUp.setOnClickListener(this);

        editTextFullName = findViewById(R.id.input_fullname);
        editTextUserName = findViewById(R.id.input_username);
        editTextEmail = findViewById(R.id.input_email);
        editTextPassword = findViewById(R.id.input_password);

        progressBar = findViewById(R.id.progressBar);

        // Go Back Arrow ->
        clickBack = findViewById(R.id.view_back_arrow);
        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterUserActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                registerUser();
//                registerDatabase();
                break;
        }
    }


    public void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String userName = editTextUserName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (userName.isEmpty()) {
            editTextUserName.setError("Username is required");
            editTextUserName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be longer than 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        MainPost = FirebaseDatabase.getInstance().getReference().child("UserData");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(RegisterUserActivity.this);
                mDialog.setMessage("Please wait....");
                mDialog.show();


                HashMap<String, Object> map = new HashMap<>();
                map.put("FullName", editTextFullName.getText().toString());
                map.put("UserName", editTextUserName.getText().toString());
                map.put("Email", editTextEmail.getText().toString());
//                map.put("Password", editTextPassword.getText().toString());

                MainPost.push()
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterUserActivity.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        editTextFullName.setText("");
                        editTextUserName.setText("");
                        editTextEmail.setText("");

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            User user = new User(fullName, userName, email);

                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegisterUserActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);

                                                        // Redirect to login layout
                                                    } else {
                                                        Toast.makeText(RegisterUserActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });

                                        } else {
                                            Toast.makeText(RegisterUserActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                        mDialog.dismiss();
                    }
                });
            }
        });
    }
}