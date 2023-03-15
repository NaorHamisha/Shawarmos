package com.example.shawarmos.models.interfaces;

import android.graphics.Bitmap;

import com.example.shawarmos.models.listeners.IImageOnCompleteListener;

public interface IStorage {
    void uploadImage(Bitmap imageBitmap, String imageName, String storageLocation, IImageOnCompleteListener listener);
}
