package com.example.shawarmos.models.interfaces;

import com.example.shawarmos.entities.Review;
import com.example.shawarmos.entities.User;
import com.example.shawarmos.models.PostModel;
import com.example.shawarmos.models.listeners.IEmptyOnCompleteListener;

import java.util.List;

public interface IDbCollections {

     void createUser(User user, IEmptyOnCompleteListener listener);

     void updateUser(User user, IEmptyOnCompleteListener listener);

     void getUserById(String userId, PostModel.Listener<User> listener);

     void addReview(Review review, IEmptyOnCompleteListener listener);

     void getAllReviewsSince(Long since, PostModel.Listener<List<Review>> callback);
}
