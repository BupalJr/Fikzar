package bupaljr.com.fikzar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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


        imageLogo.setAnimation(topAnim);
        splashLogo.setAnimation(topAnim);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, ChooseMainActivity.class);
                startActivity(intent);

                // close this activity
                finish();
            }
        }, 5 * 1000); // wait for 5 seconds

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
    }
}