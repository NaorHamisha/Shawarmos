package com.example.shawarmos.DAL.Interfaces;

import com.example.shawarmos.models.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface IUserOnCompleteListener {
    void onComplete(String userId, Exception ex);
}

