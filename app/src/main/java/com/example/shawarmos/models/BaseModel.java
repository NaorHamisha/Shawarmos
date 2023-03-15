package com.example.shawarmos.models;

import com.example.shawarmos.models.firebase.FireBaseStorage;
import com.example.shawarmos.models.firebase.FireBaseAuthentication;
import com.example.shawarmos.models.firebase.FireBaseDbCollections;
import com.example.shawarmos.models.interfaces.IAuth;
import com.example.shawarmos.models.interfaces.IDbCollections;
import com.example.shawarmos.models.interfaces.IStorage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class BaseModel {
    protected Executor executor = Executors.newSingleThreadExecutor();

    protected IDbCollections dbCollections = new FireBaseDbCollections();
    protected IStorage storage = new FireBaseStorage();
    protected IAuth auth = new FireBaseAuthentication();

    public interface Listener<T>{
        void onComplete(T data);
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
}
