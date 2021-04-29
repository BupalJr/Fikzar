package bupaljr.com.fikzar.handyman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.customer.ForgotPassword;
import bupaljr.com.fikzar.customer.MainActivity;
import bupaljr.com.fikzar.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterHandyman extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CODE = 101;
    ImageView clickBack;
    private CircleImageView profileImage;
    private EditText etFullName, etUserName, etEmail, etPassword, etCity, etJobs, etStatement;

    private Button btnSignUp;

    private ProgressBar progressBar;

    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    ProgressDialog mLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_handyman);


        mAuth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btn_submit);

        clickBack = findViewById(R.id.view_back_arrow);

        profileImage = findViewById(R.id.input_profile_image);

        etFullName = findViewById(R.id.input_fullname);
        etUserName = findViewById(R.id.input_username);
        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        etCity = findViewById(R.id.input_city);
        etJobs = findViewById(R.id.input_jobs);
        etStatement = findViewById(R.id.input_statement);

        progressBar = findViewById(R.id.progressBar);


        mLoadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("HandymanUsers");
        storageReference = FirebaseStorage.getInstance().getReference().child("HandymanStorage");

        // Go Back Arrow ->
        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterHandyman.this, HandymanMainActivity.class));
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });
    }


    private void SaveData() {

        String fullname = etFullName.getText().toString();
        String username = etUserName.getText().toString();
        String email = etEmail.getText().toString();
        String city = etCity.getText().toString();
        String jobs = etJobs.getText().toString();
        String statement = etStatement.getText().toString();


        if (city.isEmpty()) {
            showError(etCity, "Please, it is a required!");
        } else if (jobs.isEmpty()) {
            showError(etJobs, "Please, it is a required!");
        } else if (statement.isEmpty()) {
            showError(etStatement, "Please, it is a required!");
        } else if (imageUri == null) {
            Toast.makeText(this, "Please select a profile image", Toast.LENGTH_SHORT).show();
        } else {
            mLoadingBar.setTitle("Adding Profile Details");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            storageReference.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        storageReference.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("fullname", fullname);
                                hashMap.put("username", username);
                                hashMap.put("email", email);
                                hashMap.put("jobs", jobs);
                                hashMap.put("statement", statement);
                                hashMap.put("profileImage", uri.toString());
                                hashMap.put("status", "offline");

                                databaseReference.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Intent intent = new Intent(RegisterHandyman.this, HandymanDashboard.class);
                                        startActivity(intent);
                                        mLoadingBar.dismiss();
                                        Toast.makeText(RegisterHandyman.this, "Hooray!! Registeration completed", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mLoadingBar.dismiss();
                                        Toast.makeText(RegisterHandyman.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                }
            });
        }

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }

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
        String fullName = etFullName.getText().toString().trim();
        String userName = etUserName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String jobs = etJobs.getText().toString().trim();
        String statement = etStatement.getText().toString().trim();


        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
            return;
        }

        if (userName.isEmpty()) {
            etUserName.setError("Username is required");
            etUserName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please provide valid email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password should be longer than 6 characters");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("HandymanUsers");
        storageReference = FirebaseStorage.getInstance().getReference().child("HandymanStorage");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(RegisterHandyman.this);
                mDialog.setMessage("Please wait....");
                mDialog.show();


                HashMap<String, Object> map = new HashMap<>();
                map.put("FullName", etFullName.getText().toString());
                map.put("UserName", etUserName.getText().toString());
                map.put("Email", etEmail.getText().toString());
                map.put("City", etCity.getText().toString());
                map.put("Jobs", etJobs.getText().toString());
                map.put("Statement", etStatement.getText().toString());

                databaseReference.push()
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterHandyman.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etFullName.setText("");
                        etUserName.setText("");
                        etEmail.setText("");
                        etCity.setText("");
                        etJobs.setText("");
                        etStatement.setText("");

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            User user = new User(fullName, userName, email, city, jobs, statement);

                                            FirebaseDatabase.getInstance().getReference("HandymanProfile")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegisterHandyman.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();

                                                        // Redirect to login layout
                                                    } else {
                                                        Toast.makeText(RegisterHandyman.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                    }
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            });
                                        } else {
                                            Toast.makeText(RegisterHandyman.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
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