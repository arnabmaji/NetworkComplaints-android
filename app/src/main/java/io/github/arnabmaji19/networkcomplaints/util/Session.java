package io.github.arnabmaji19.networkcomplaints.util;

//Singleton class for managing user sessions
public class Session {

    private static Session instance = new Session();

    private String username;
    private String userId;
    private String email;
    private boolean isSessionAvailable = false;

    //private constructor
    private Session() {
    }

    public boolean isSessionAvailable() {
        return isSessionAvailable;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void create(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        isSessionAvailable = true;
    }

    public void clear() {
        this.userId = null;
        this.username = null;
        this.email = null;
        isSessionAvailable = false;
    }

    public static Session getInstance() {
        return instance;
    }
}
