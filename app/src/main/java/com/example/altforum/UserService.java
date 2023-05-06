package com.example.altforum;

import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserService {
    private static final String TAG = UserService.class.getSimpleName();
    private FirebaseFirestore db;

    public UserService() {
        db = FirebaseFirestore.getInstance();
    }

    public void createUser(User user, final OnCompleteListener<Void> listener) {
        db.collection("users").document(user.getId())
                .set(user)
                .addOnCompleteListener(listener);
    }

    public void readUser(String userId, final OnSuccessListener<User> successListener, final OnFailureListener failureListener) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            successListener.onSuccess(user);
                        } else {
                            failureListener.onFailure(new Exception("Nincs ilyen felhasználó!"));
                        }
                    }
                })
                .addOnFailureListener(failureListener);
    }

    public void updateUser(User user, final OnCompleteListener<Void> listener) {
        Map<String, Object> userUpdates = user.toMap();
        db.collection("users").document(user.getId())
                .update(userUpdates)
                .addOnCompleteListener(listener);
    }

    public void deleteUser(String userId, final OnCompleteListener<Void> listener) {
        db.collection("users").document(userId)
                .delete()
                .addOnCompleteListener(listener);
    }
}


