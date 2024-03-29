package com.example.shawarmos.models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shawarmos.models.dao.ReviewDao;
import com.example.shawarmos.MyApplication;
import com.example.shawarmos.entities.Review;

@Database(entities = {Review.class}, version = 1000)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ReviewDao reviewDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {

        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "Shawarmos.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}
