package com.example.shawarmos.DAL;

import com.example.shawarmos.DAL.Interfaces.IEmptyOnCompleteListener;
import com.example.shawarmos.DAL.Interfaces.IUserOnCompleteListener;
import com.example.shawarmos.DAL.firebase.FireBaseAuthentication;

public class AuthModel extends BaseModel {

    private static final AuthModel _instance = new AuthModel();

    private FireBaseAuthentication m_Auth = new FireBaseAuthentication();

    public static AuthModel instance() {
        return _instance;
    }

    private AuthModel() {}

    public String getCurrentUserId() {
        return m_Auth.getCurrentUserUID();
    }

    public void login(String username, String password, IUserOnCompleteListener listener) {
        m_Auth.login(username, password, listener);
    }

    public void register(String email, String password, IUserOnCompleteListener listener) {
        m_Auth.register(email, password, listener);
    }

    public boolean isUserLoggedIn() {
        return m_Auth.isLoggedIn();
    }

    public void signOut(IEmptyOnCompleteListener listener) {
        m_Auth.signOut(listener);
    }

}
