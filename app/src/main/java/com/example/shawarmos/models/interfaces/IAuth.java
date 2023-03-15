package com.example.shawarmos.models.interfaces;

import com.example.shawarmos.models.listeners.IEmptyOnCompleteListener;
import com.example.shawarmos.models.listeners.IUserOnCompleteListener;

public interface IAuth {

    String getCurrentUserUID();

    boolean isLoggedIn();

    void login(String email, String password, IUserOnCompleteListener listener);

    void register(String email, String password, IUserOnCompleteListener listener);

    void signOut(IEmptyOnCompleteListener listener);
}
