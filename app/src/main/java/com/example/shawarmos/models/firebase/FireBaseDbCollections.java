package com.example.shawarmos.models.firebase;

import androidx.annotation.NonNull;

import com.example.shawarmos.models.interfaces.IDbCollections;
import com.example.shawarmos.models.listeners.IEmptyOnCompleteListener;
import com.example.shawarmos.models.PostModel;
import com.example.shawarmos.entities.Review;
import com.example.shawarmos.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FireBaseDbCollections implements IDbCollections {

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

    public void getUserById(String userId, PostModel.Listener<User> listener) {
        db.collection(User.COLLECTION).document(userId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = User.fromJson(documentSnapshot.getData());
                        listener.onComplete(user);
                    }
                });
    }

    public void addReview(Review review, IEmptyOnCompleteListener listener) {
        db.collection(Review.COLLECTION)
                .document(review.getReviewId()).set(review.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();
                    }
                });
    }

    public void getAllReviewsSince(Long since, PostModel.Listener<List<Review>> callback){
        db.collection(Review.COLLECTION)
                .whereGreaterThanOrEqualTo(Review.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Review> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Review review = Review.fromJson(json.getData());
                                list.add(review);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }
}
