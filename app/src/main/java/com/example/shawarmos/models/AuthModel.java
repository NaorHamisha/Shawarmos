package com.example.shawarmos.models;

import com.example.shawarmos.models.listeners.IEmptyOnCompleteListener;
import com.example.shawarmos.models.listeners.IUserOnCompleteListener;

public class AuthModel extends BaseModel {

    private static final AuthModel _instance = new AuthModel();

    public static AuthModel instance() {
        return _instance;
    }

    private AuthModel() {}

    public String getCurrentUserId() {
        return auth.getCurrentUserUID();
    }

    public void login(String username, String password, IUserOnCompleteListener listener) {
        auth.login(username, password, listener);
    }

    public void register(String email, String password, IUserOnCompleteListener listener) {
        auth.register(email, password, listener);
    }

    public boolean isUserLoggedIn() {
        return auth.isLoggedIn();
    }

    public void signOut(IEmptyOnCompleteListener listener) {
        auth.signOut(listener);
    }

}
