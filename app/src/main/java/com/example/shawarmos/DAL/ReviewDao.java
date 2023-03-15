package com.example.shawarmos.DAL;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.shawarmos.models.Review;

import java.util.List;
@Dao
public interface ReviewDao {
    @Query("select * from Review")
    LiveData<List<Review>> getAll();

    @Query("select * from Review where author = :username")
    LiveData<List<Review>> getAllReviewsByAuthor(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Review... reviews);

    @Delete
    void delete(Review review);
}
