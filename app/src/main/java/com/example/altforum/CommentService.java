package com.example.altforum;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CommentService {

    private static final String COMMENTS_COLLECTION = "comments";

    private FirebaseFirestore db;
    private CollectionReference commentsRef;

    public CommentService() {
        db = FirebaseFirestore.getInstance();
        commentsRef = db.collection(COMMENTS_COLLECTION);
    }

    public Task<QuerySnapshot> getAllComments() {
        return commentsRef.get();
    }

    public Task<DocumentReference> addComment(String userId, String username, String text) {
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("userId", userId);
        commentData.put("username", username);
        commentData.put("text", text);
        commentData.put("timestamp", FieldValue.serverTimestamp());

        // Generate a unique id for the comment
        String commentId = commentsRef.document().getId();
        commentData.put("commentId", commentId);

        return commentsRef.add(commentData);
    }



    public Task<Void> updateComment(String commentId, String text) {
        DocumentReference commentDoc = commentsRef.document(commentId);
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("text", text);
        return commentDoc.update(updateData);
    }

    public Task<Void> deleteComment(String commentId) {
        DocumentReference commentDoc = commentsRef.document(commentId);
        return commentDoc.delete();
    }

    public Query getCommentsByUserId(String userId) {
        return commentsRef.whereEqualTo("userId", userId);
    }


    public Task<QuerySnapshot> getAllCommentsOrderedByTime() {
        CollectionReference commentsRef = db.collection(COMMENTS_COLLECTION);
        return commentsRef.orderBy("timestamp", Query.Direction.DESCENDING).get();
    }
}

