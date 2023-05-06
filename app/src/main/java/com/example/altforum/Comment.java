package com.example.altforum;

import com.google.firebase.Timestamp;

public class Comment {
    private String commentId;
    private String userId;
    private String username;
    private String text;
    private Timestamp timestamp;

    public Comment(String userId, String username, String text, Timestamp timestamp) {
        //this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}


