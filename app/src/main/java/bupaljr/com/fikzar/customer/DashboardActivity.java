package bupaljr.com.fikzar.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import bupaljr.com.fikzar.MyViewHolder;
import bupaljr.com.fikzar.R;
import bupaljr.com.fikzar.Utils.Posts;
import bupaljr.com.fikzar.model.Customer;

public class DashboardActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 101;
    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference PostRef;
    StorageReference PostImageRef;

    ProgressDialog mLoadingBar;

    FirebaseRecyclerAdapter<Posts, MyViewHolder> adapter;
    FirebaseRecyclerOptions<Posts> options;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mLoadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        PostImageRef = FirebaseStorage.getInstance().getReference().child("PostImages");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadPost();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                , SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.hire:
                        startActivity(new Intent(getApplicationContext()
                                , JobsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.chats:
                        startActivity(new Intent(getApplicationContext()
                                , ChatsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext()
                                , AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void LoadPost() {
        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostRef, Posts.class).build();
        adapter = new FirebaseRecyclerAdapter<Posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Posts model) {
                holder.postDes.setText(model.getPostDesc());
                holder.postTime.setText(model.getPostTime());
                Picasso.get().load(model.getPostImageUrl()).into(holder.postImage);
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_post, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }
    }
}

