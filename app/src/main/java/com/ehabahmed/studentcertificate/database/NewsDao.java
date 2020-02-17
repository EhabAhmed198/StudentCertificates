package com.ehabahmed.studentcertificate.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {
    // test
    @Query("select * from news")
    LiveData<List<NewsEntity>> getAllNewsWithLiveData();
    @Query("select * from news")
    List<NewsEntity> getAllNews();
    @Insert
    void insertNews(NewsEntity newsEntity);
    @Query("DELETE FROM news")
    void deleteNews();
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void UpdateNews(NewsEntity newsEntity);
}
