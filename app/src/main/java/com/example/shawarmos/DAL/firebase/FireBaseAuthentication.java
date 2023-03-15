package com.example.shawarmos.DAL.firebase;

import com.example.shawarmos.DAL.Interfaces.IEmptyOnCompleteListener;
import com.example.shawarmos.DAL.Interfaces.IUserOnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseAuthentication {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public FireBaseAuthentication() {}

    public String getCurrentUserUID() {

        return firebaseAuth.getUid();
    }

    public boolean isLoggedIn() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        return currentUser != null;
    }

    public void login(String email, String password, IUserOnCompleteListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> onAuthenticationComplete(task, listener));
    }

    public void register(String email, String password, IUserOnCompleteListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> onAuthenticationComplete(task, listener));
    }

    public void signOut(IEmptyOnCompleteListener listener) {
        firebaseAuth.signOut();
        listener.onComplete();
    }

    private void onAuthenticationComplete(Task<AuthResult> task, IUserOnCompleteListener listener) {
        FirebaseUser user;
        Exception ex = null;
        String userId = null;

        if (task.isSuccessful()) {
            user = firebaseAuth.getCurrentUser();
        } else {
            user = null;
            ex = task.getException();
        }

        userId = (user != null) ? user.getUid() : null;

        listener.onComplete(userId, ex);
    }
}
