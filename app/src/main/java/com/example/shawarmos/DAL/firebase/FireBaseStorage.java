package com.example.shawarmos.DAL.firebase;

import android.graphics.Bitmap;

import com.example.shawarmos.DAL.Interfaces.IImageOnCompleteListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FireBaseStorage {

    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    public FireBaseStorage() {}

    public void uploadImage(Bitmap imageBitmap, String imageName, String storageLocation, IImageOnCompleteListener listener) {
        StorageReference storageReference = storage.getReference();
        StorageReference imageReference = storageReference.child(storageLocation + imageName + ".jpg");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        UploadTask uploadTask = imageReference.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnCompleteListener(task -> imageReference.getDownloadUrl().addOnSuccessListener(uri -> listener.onComplete(uri.toString())));
    }
}
