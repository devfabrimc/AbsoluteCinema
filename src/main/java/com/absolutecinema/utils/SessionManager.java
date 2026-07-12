package com.absolutecinema.utils;

import com.absolutecinema.model.User;

public class SessionManager {

    private User currentUser;

    public void login(User user) {}

    public void logout() {}

    public User getCurrentUser() {
        return null;
    }

    public boolean isLoggedIn() {
        return false;
    }

    public boolean isAdmin() {
        return false;
    }
}