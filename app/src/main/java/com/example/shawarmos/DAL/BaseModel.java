package com.example.shawarmos.DAL;

import com.example.shawarmos.DAL.firebase.FireBaseAuthentication;
import com.example.shawarmos.DAL.firebase.FireBaseDbCollections;
import com.example.shawarmos.DAL.firebase.FireBaseStorage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class BaseModel {
    protected Executor executor = Executors.newSingleThreadExecutor();

    protected FireBaseDbCollections dbCollections = new FireBaseDbCollections();
    protected FireBaseStorage storage = new FireBaseStorage();
    protected FireBaseAuthentication auth = new FireBaseAuthentication();

    public interface Listener<T>{
        void onComplete(T data);
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
}
