package com.example.personal.meal;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

class MealRepository {
    private static MealDao myDao;
    final LiveData<List<MealModel>>getalldata;
    public MealRepository(Context context){
        MealDatabase database= MealDatabase.getInstance(context);
        myDao=database.mealDao();
        getalldata=myDao.getalldata();

    }
    public String searchmeal(String id){
        String meal=myDao.checkMeal(id);
        return meal;
    }
    LiveData<List<MealModel>>getallmeals(){
        return getalldata;
    }
    public void insert(MealModel mealModel){
        new insert(myDao).execute(mealModel);
    }

    public void delete(MealModel mealModel){
        new delete().execute(mealModel);
    }

    class insert extends AsyncTask<MealModel,Void,Void> {
        MealDao mealDao;

        insert(MealDao myDao) {
            this.mealDao = myDao;
        }

        @Override
        protected Void doInBackground(MealModel... mealModels) {
            mealDao.insert(mealModels[0]);
            return null;
        }
    }
        public class delete extends AsyncTask<MealModel,Void,Void> {



            @Override
            protected Void doInBackground(MealModel... mealModels) {
                myDao.delete(mealModels[0]);
                return null;
            }

    }
}
