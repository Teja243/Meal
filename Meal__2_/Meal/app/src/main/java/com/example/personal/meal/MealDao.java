package com.example.personal.meal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insert(MealModel mealModel);

    @Delete
    public void delete(MealModel mealModel);

    @Query("SELECT * FROM MealDetails")
    LiveData<List<MealModel>>getalldata();

    @Query("SELECT sid FROM MealDetails WHERE sid==:id")
    String checkMeal(String id);


}
