package bupaljr.com.fikzar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import bupaljr.com.fikzar.customer.MainActivity;
import bupaljr.com.fikzar.handyman.HandymanAccountSetup;
import bupaljr.com.fikzar.handyman.HandymanMainActivity;

public class SplashScreen extends AppCompatActivity {

    // Initialize variable
    Animation topAnim, bottomAnim, rightSideAnim, leftSideAnim;
    ImageView imageLogo, splashLogo;
    Button btnLookingHandyman, btnIAmHandyman;


    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        leftSideAnim = AnimationUtils.loadAnimation(this, R.anim.left_side_animation);
        rightSideAnim = AnimationUtils.loadAnimation(this, R.anim.right_side_animation);


        imageLogo = findViewById(R.id.image_logo);
        splashLogo = findViewById(R.id.splash_logo);
        btnIAmHandyman = findViewById(R.id.btn_i_am_handyman);
        btnLookingHandyman = findViewById(R.id.btn_looking_handyman);

        // Set animations
        btnIAmHandyman.setAnimation(bottomAnim);
        btnLookingHandyman.setAnimation(bottomAnim);

        imageLogo.setAnimation(topAnim);
        splashLogo.setAnimation(topAnim);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mUser != null) {
                    databaseReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Intent intent = new Intent(SplashScreen.this, HandymanMainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(SplashScreen.this, HandymanAccountSetup.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Intent intent = new Intent(SplashScreen.this, HandymanMainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };


        // To Customer dashboard
        btnLookingHandyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        });

        // To handyman dashboard
        btnIAmHandyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, HandymanMainActivity.class));
            }
        });
    }
}