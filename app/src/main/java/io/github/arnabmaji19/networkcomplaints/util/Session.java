package io.github.arnabmaji19.networkcomplaints.util;

import io.github.arnabmaji19.networkcomplaints.model.User;

//Singleton class for managing user sessions
public class Session {

    private static Session instance = new Session();

    private User user;
    private boolean isSessionAvailable = false;

    //private constructor
    private Session() {
    }

    public boolean isSessionAvailable() {
        return isSessionAvailable;
    }

    public User getUser() {
        return user;
    }

    public void create(User user) {
        this.user = user;
        isSessionAvailable = true;
    }

    public void clear() {
        this.user = null;
        isSessionAvailable = false;
    }

    public static Session getInstance() {
        return instance;
    }
}
