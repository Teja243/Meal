package com.example.personal.meal;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MealModel.class},version = 1,exportSchema = false)
public abstract class MealDatabase extends RoomDatabase {
    public abstract MealDao mealDao();

    private static MealDatabase database;
    public static MealDatabase getInstance(Context context) {
        if (database == null) {
            synchronized (MealDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(),MealDatabase.class,"meal")
                            .allowMainThreadQueries().fallbackToDestructiveMigration().build();

                }
            }
        }
        return database;
    }
}
