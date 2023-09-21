package com.example.personal.meal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class RoomModel extends AndroidViewModel {
    private final MealRepository mealRepository;
    LiveData<List<MealModel>>getallFav;
    public RoomModel(@NonNull Application application) {
        super(application);
        mealRepository=new MealRepository(application);
        getallFav=mealRepository.getalldata;

    }
    public LiveData<List<MealModel>>getFav(){
        return getallFav;
    }
    public void insertmeal(MealModel mealModel){
        mealRepository.insert(mealModel);

    }
    public void deletemeal(MealModel mealModel){
        mealRepository.delete(mealModel);
    }
    public String findMeal(String id){
        return mealRepository.searchmeal(id);

    }
}
