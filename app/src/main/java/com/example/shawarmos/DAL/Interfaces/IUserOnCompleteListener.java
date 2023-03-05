package com.example.shawarmos.DAL.Interfaces;

import com.google.firebase.auth.FirebaseUser;

public interface IUserOnCompleteListener {
    void onComplete(FirebaseUser firebaseUser, Exception ex);
}
