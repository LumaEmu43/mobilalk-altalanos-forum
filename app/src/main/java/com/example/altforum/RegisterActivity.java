package com.example.altforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private FirebaseAuth mAuth;

    //Register adattagok létrehozása
    EditText emailEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Firebase init
        Log.i(LOG_TAG,"Auth elott");
        mAuth = FirebaseAuth.getInstance();
        Log.i(LOG_TAG,"Auth utan");

        //Lekérem a regisztrációs adatokat
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);
        Log.i(LOG_TAG, "onCreate végigfutott!");
    }

    public void register(View view) {
        String email = emailEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String repassword = passwordAgainEditText.getText().toString();

        if (!password.equals(repassword)) {
            Log.e(LOG_TAG, "Nem egyeznek a jelszavak");
            Toast.makeText(RegisterActivity.this, "Nem egyeznek a jelszavak!", Toast.LENGTH_LONG).show();
        }

        Log.i(LOG_TAG, "Regisztrált: " + username + ", e-mail: " + email);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uid = mAuth.getCurrentUser().getUid();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(uid)
                            .set(new User(uid, email, null, new Date(), username))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(LOG_TAG, "Firestore user addolva: " + uid);

                                    Intent intent = new Intent(RegisterActivity.this, FeedActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(LOG_TAG, "Firestore hiba", e);
                                }
                            });

                    Log.d(LOG_TAG, "Sikeres regisztráció!");
                    Toast.makeText(RegisterActivity.this, "Sikeres regisztráció!", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(LOG_TAG, "Sikertelen regisztráció!", task.getException());
                    Toast.makeText(RegisterActivity.this, "Sikertelen regisztráció!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goBack(View view) {
        finish();
    }

}