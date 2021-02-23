package bupaljr.com.fikzar.handyman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import bupaljr.com.fikzar.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class HandymanAccountSetup extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    CircleImageView hProfilePic;
    EditText hJobs, hLocation, hAbout;
    Button btnSubmit;
    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_account_setup);

        hProfilePic = findViewById(R.id.input_profile_image);
        hLocation = findViewById(R.id.h_location);
        hJobs = findViewById(R.id.h_jobs);
        hAbout = findViewById(R.id.h_about);
        btnSubmit = findViewById(R.id.btn_submit);

        mLoadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("HandymanUsers");
        storageReference = FirebaseStorage.getInstance().getReference().child("HandymanStorage");


        hProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });

    }

    private void SaveData() {

        String location = hLocation.getText().toString();
        String jobs = hJobs.getText().toString();
        String about = hAbout.getText().toString();


        if (location.isEmpty()) {
            showError(hLocation, "Please, it is a required!");
        } else if (jobs.isEmpty()) {
            showError(hJobs, "Please, it is a required!");
        } else if (about.isEmpty()) {
            showError(hAbout, "Please, it is a required!");
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
                                hashMap.put("location", location);
                                hashMap.put("jobs", jobs);
                                hashMap.put("about", about);
                                hashMap.put("profileImage", uri.toString());
                                hashMap.put("status", "offline");

                                databaseReference.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Intent intent = new Intent(HandymanAccountSetup.this, HandymanDashboard.class);
                                        startActivity(intent);
                                        mLoadingBar.dismiss();
                                        Toast.makeText(HandymanAccountSetup.this, "Hooray!! Registeration completed", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mLoadingBar.dismiss();
                                        Toast.makeText(HandymanAccountSetup.this, e.toString(), Toast.LENGTH_SHORT).show();
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
            hProfilePic.setImageURI(imageUri);
        }

    }
}