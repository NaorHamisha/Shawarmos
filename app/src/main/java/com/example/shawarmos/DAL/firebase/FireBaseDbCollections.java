package com.example.shawarmos.DAL.firebase;

import com.example.shawarmos.DAL.BaseModel;
import com.example.shawarmos.DAL.Interfaces.IEmptyOnCompleteListener;
import com.example.shawarmos.DAL.Model;
import com.example.shawarmos.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireBaseDbCollections {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FireBaseDbCollections() {}

    public void createUser(User user, IEmptyOnCompleteListener listener) {
        Map<String, Object> json = user.toJson();
        db.collection(User.COLLECTION)
                .document(user.getUserId())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void updateUser(User user, IEmptyOnCompleteListener listener) {
        Map<String, Object> json = user.toJson();
        db.collection(User.COLLECTION).document(user.getUserId())
                .set(json).addOnCompleteListener(task -> listener.onComplete());
    }

    public void getUserById(String userId, Model.Listener<User> listener) {
        db.collection(User.COLLECTION).document(userId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = User.fromJson(documentSnapshot.getData());
                        listener.onComplete(user);
                    }
                });
    }
}
