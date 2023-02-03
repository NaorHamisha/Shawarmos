package com.example.shawarmos.DAL;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shawarmos.models.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {

    private FirebaseFirestore firestoreDb;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    FirebaseModel() {
        firestoreDb = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        firestoreDb.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void getAllReviewsSince(Long since, Model.Listener<List<Review>> callback){
        firestoreDb.collection(Review.COLLECTION)
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

    public void login(String username, String password, Model.Listener<Boolean> listener) {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                boolean isSuccess = task.isSuccessful();
                if (isSuccess) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG-AUTH", "signInWithEmail:success");
                    currentUser = mAuth.getCurrentUser();
                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG-AUTH", "signInWithEmail:failure", task.getException());
                    //Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

                String str = currentUser.getEmail();
                Uri str2 = currentUser.getPhotoUrl();

                listener.onComplete(isSuccess);
            }
        });
    }

    public void registerUser(String username, String password, Model.Listener<Boolean> listener) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            currentUser = mAuth.getCurrentUser();
                            //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }

                        listener.onComplete(task.isSuccessful());
                    }
                });
    }

    public void signOutUser() {
        mAuth.signOut();
    }

    public void addReview(Review review, Model.Listener<Void> listener) {
        firestoreDb.collection(Review.COLLECTION)
                .document(review.getReviewId()).set(review.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            listener.onComplete(uri.toString());
                        }
                    });
                }
        });
    }

    public boolean isLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();
        return (user != null);
    }
}
