package com.example.altforum;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String email;
    private String description;
    private Date joined;
    private String username;

    public User() {
    }

    public User(String id, String email, String description, Date joined, String username) {
        this.id = id;
        this.email = email;
        this.description = description;
        this.joined = joined;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public Date getJoined() {
        return joined;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("email", email);
        result.put("description", description);
        result.put("joined", joined.getTime());
        result.put("username", username);
        return result;
    }

}
