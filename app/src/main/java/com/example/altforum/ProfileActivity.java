package com.example.altforum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;

public class ProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = ProfileActivity.class.getName();

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView descriptionTextView;
    private TextView joinedTextView;

    private String userId;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.i(LOG_TAG, "onCreate belépési pont!");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i(LOG_TAG, currentUser.toString());
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        Log.i(LOG_TAG,userId.toString());

        userService = new UserService();

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        joinedTextView = findViewById(R.id.joinedTextView);

        Log.i(LOG_TAG, "readUser előtt!");
        userService.readUser(userId, new OnSuccessListener<User>() {
            @Override
            public void onSuccess(User user) {
                nameTextView.setText(user.getUsername());
                Log.i(LOG_TAG, nameTextView.toString());
                emailTextView.setText(user.getEmail());
                Log.i(LOG_TAG, emailTextView.toString());
                descriptionTextView.setText(user.getDescription());
                Log.i(LOG_TAG, descriptionTextView.toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd/HH:mm");
                String joinedDate = dateFormat.format(user.getJoined());
                joinedTextView.setText("Csatlakozott: " + joinedDate);
                Log.i(LOG_TAG, joinedTextView.toString());
                Toast.makeText(ProfileActivity.this, "Sikeres adatolvasás", Toast.LENGTH_SHORT).show();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure of the read operation
                Toast.makeText(ProfileActivity.this, "Sikertelen adatolvasás", Toast.LENGTH_SHORT).show();
                Log.e("ProfileActivity", "Sikertelen adatolvasás", e);
                Log.i(LOG_TAG, "fail?!");
            }
        });
        Log.i(LOG_TAG, "readUser után!");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.profileItem) {
            Toast.makeText(this, "Profil kivalasztva", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Profil kivalasztva");
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.logoutItem) {
            Toast.makeText(this, "Kijelentkezes kivalasztva", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void backToFeed(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}
