package com.example.altforum;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CommentAsyncTask extends AsyncTask<Void, Void, QuerySnapshot> {

    private static final String COMMENTS_COLLECTION = "comments";

    private FirebaseFirestore db;
    private CollectionReference commentsRef;

    private CommentServiceCallback callback;
    private String userId;
    private String username;
    private String text;
    private String commentId;
    private String updateText;
    private boolean isDelete;

    public void CommentService(CommentServiceCallback callback) {
        this.callback = callback;
        db = FirebaseFirestore.getInstance();
        commentsRef = db.collection(COMMENTS_COLLECTION);
    }

    public void getAllComments() {
        execute();
    }

    public void addComment(String userId, String username, String text) {
        this.userId = userId;
        this.username = username;
        this.text = text;
        execute();
    }

    public void updateComment(String commentId, String text) {
        this.commentId = commentId;
        this.updateText = text;
        execute();
    }

    public void deleteComment(String commentId) {
        this.commentId = commentId;
        this.isDelete = true;
        execute();
    }

    public void getCommentsByUserId(String userId) {
        Query query = commentsRef.whereEqualTo("userId", userId);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (callback != null) {
                callback.onCommentsLoaded(queryDocumentSnapshots);
            }
        }).addOnFailureListener(e -> {
            if (callback != null) {
                callback.onCommentsLoadError(e);
            }
        });
    }

    @Override
    protected QuerySnapshot doInBackground(Void... voids) {
        if (isDelete) {
            deleteCommentInBackground(commentId);
        } else if (commentId != null) {
            updateCommentInBackground(commentId, updateText);
        } else if (userId != null && username != null && text != null) {
            addCommentInBackground(userId, username, text);
        } else {
            return null;
        }
        return null;
    }



    private void getAllCommentsInBackground() {
        commentsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (callback != null) {
                    callback.onCommentsLoaded(queryDocumentSnapshots);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (callback != null) {
                    callback.onCommentsLoadError(e);
                }
            }
        });
    }


    private void addCommentInBackground(String userId, String username, String text) {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("userId", userId);
        commentData.put("username", username);
        commentData.put("text", text);
        commentsRef.add(commentData).addOnSuccessListener(documentReference -> {
            if (callback != null) {
                callback.onCommentAdded(documentReference.getId());
            }
        }).addOnFailureListener(e -> {
            if (callback != null) {
                callback.onCommentAddError(e);
            }
        });
    }

    private void updateCommentInBackground(String commentId, String text) {
        DocumentReference commentDoc = commentsRef.document(commentId);
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("text", text);
        commentDoc.update(updateData).addOnSuccessListener(aVoid -> {
            if (callback != null) {
                callback.onCommentUpdated(commentId);
            }
        }).addOnFailureListener(e -> {
            if (callback != null) {
                callback.onCommentUpdateError(e);
            }
        });
    }

    private void deleteCommentInBackground(String commentId) {
        DocumentReference commentDoc = commentsRef.document(commentId);
        commentDoc.delete().addOnSuccessListener(aVoid -> {
            if (callback != null) {
                callback.onCommentDeleted(commentId);
            }
        }).addOnFailureListener(e -> {
            if (callback != null) {
                callback.onCommentDeleteError(e);
            }
        });
    }

    public interface CommentServiceCallback {
        void onCommentsLoaded(QuerySnapshot querySnapshot);

        void onCommentsLoadError(Exception e);

        void onCommentAdded(String commentId);

        void onCommentAddError(Exception e);

        void onCommentUpdated(String commentId);

        void onCommentUpdateError(Exception e);

        void onCommentDeleted(String commentId);

        void onCommentDeleteError(Exception e);
    }
}

