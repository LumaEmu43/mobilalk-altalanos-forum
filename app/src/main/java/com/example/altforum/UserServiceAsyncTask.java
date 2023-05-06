package com.example.altforum;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class UserServiceAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = UserServiceAsyncTask.class.getSimpleName();

    private UserService userService;
    private User user;
    private String userId;
    private int operation;
    private OnUserServiceCompletedListener listener;

    public UserServiceAsyncTask(UserService userService, User user, int operation, OnUserServiceCompletedListener listener) {
        this.userService = userService;
        this.user = user;
        this.operation = operation;
        this.listener = listener;
    }

    public UserServiceAsyncTask(UserService userService, String userId, int operation, OnUserServiceCompletedListener listener) {
        this.userService = userService;
        this.userId = userId;
        this.operation = operation;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        switch (operation) {
            case 1:
                userService.createUser(user, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User created successfully");
                            listener.onUserServiceCompleted(user, null);
                        } else {
                            Log.e(TAG, "Error creating user", task.getException());
                            listener.onUserServiceCompleted(null, task.getException());
                        }
                    }
                });
                break;
            case 2:
                userService.readUser(userId, new OnSuccessListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Log.d(TAG, "User read successfully");
                        listener.onUserServiceCompleted(user, null);
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error reading user", e);
                        listener.onUserServiceCompleted(null, e);
                    }
                });
                break;
            case 3:
                userService.updateUser(user, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User updated successfully");
                            listener.onUserServiceCompleted(user, null);
                        } else {
                            Log.e(TAG, "Error updating user", task.getException());
                            listener.onUserServiceCompleted(null, task.getException());
                        }
                    }
                });
                break;
            case 4:
                userService.deleteUser(userId, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User deleted successfully");
                            listener.onUserServiceCompleted(null, null);
                        } else {
                            Log.e(TAG, "Error deleting user", task.getException());
                            listener.onUserServiceCompleted(null, task.getException());
                        }
                    }
                });
                break;
        }
        return null;
    }

    public interface OnUserServiceCompletedListener {
        void onUserServiceCompleted(User user, Exception e);
    }
}


