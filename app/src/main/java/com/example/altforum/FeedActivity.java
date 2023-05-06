package com.example.altforum;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class FeedActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();

    private EditText commentEditText;
    private Button commentButton;
    private CommentService commentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //teszt();

        commentEditText = findViewById(R.id.comment_edit_text);
        commentButton = findViewById(R.id.comment_button);

        commentService = new CommentService();

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser.getUid();
                String username = currentUser.getDisplayName();

                UserService userService = new UserService();
                userService.readUser(userId, new OnSuccessListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        String username = user.getUsername();

                        String text = commentEditText.getText().toString();

                        commentService.addComment(userId, username, text).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                commentEditText.setText("");
                                Toast.makeText(FeedActivity.this, "Sikeres bejegyzés!", Toast.LENGTH_SHORT).show();
                                displayComments();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FeedActivity.this, "Sikertelen bejegyzés!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FeedActivity.this, "Nem sikerült betölteni a felhasználói adatokat", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        displayComments();
    }

    public void displayComments() {
        CommentService commentService = new CommentService();
        commentService.getAllCommentsOrderedByTime().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Comment> comments = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String id = document.getId();
                    String userId = document.getString("userId");
                    String username = document.getString("username");
                    String text = document.getString("text");
                    String commentId = document.getString("commentId");
                    Timestamp timestamp = document.getTimestamp("timestamp");
                    Comment comment = new Comment(userId, username, text, timestamp);
                    comments.add(comment);
                }
                LinearLayout layout = findViewById(R.id.comments_layout);
                layout.removeAllViews();
                for (Comment comment : comments) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/HH:mm");
                    String timestampString = dateFormat.format(comment.getTimestamp().toDate());

                    LinearLayout commentLayout = new LinearLayout(this);
                    commentLayout.setOrientation(LinearLayout.VERTICAL);

                    TextView usernameTextView = new TextView(this);
                    usernameTextView.setText(comment.getUsername());
                    usernameTextView.setTextSize(24);
                    usernameTextView.setTypeface(null, Typeface.BOLD);
                    commentLayout.addView(usernameTextView);

                    TextView timestampTextView = new TextView(this);
                    timestampTextView.setText(timestampString);
                    commentLayout.addView(timestampTextView);

                    TextView textView = new TextView(this);
                    textView.setText(comment.getText());
                    textView.setTextSize(20);
                    commentLayout.addView(textView);

                    layout.addView(commentLayout);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(16, 24, 16, 24);
                    commentLayout.setLayoutParams(layoutParams);
                    commentLayout.setPadding(32, 32, 32, 32);
                    commentLayout.setBackgroundResource(R.drawable.comment_box);
                }

            } else {
                Log.d(LOG_TAG, "Hiba a kommentek megjelenítésében: ", task.getException());
            }
        });
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
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayComments();
    }

    @Override
    protected void onStop() {
        super.onStop();
        displayComments();
    }
}