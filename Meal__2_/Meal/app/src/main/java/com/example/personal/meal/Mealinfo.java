package com.example.personal.meal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;

public class Mealinfo extends AppCompatActivity {
   private ImageView imageView;
   public TextView tvname, tvdesc;
   public ArrayList<MealModel> mealModels;
   public MaterialFavoriteButton materialFavoriteButton;
   private String ftitle, fdesc, fid, fimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealinfo);
        tvname = findViewById(R.id.tname);
        tvdesc = findViewById(R.id.tdesc);
        imageView = findViewById(R.id.image);
        materialFavoriteButton = findViewById(R.id.mfav);
        mealModels = new ArrayList<>();

        String[] data=getIntent().getStringArrayExtra("data");
        Glide.with(this).load(data[0]).into(imageView);
        tvname.setText(data[1]);
        tvdesc.setText(data[2]);
        setTitle(data[1]);

        fid=data[3];
        fimage=data[0];
        ftitle=data[1];
        fdesc=data[2];


        String save = Meal.roomModel.findMeal(fid);

        if (save != null) {
            materialFavoriteButton.setFavorite(true, true);

        } else {
            materialFavoriteButton.setFavorite(false, true);
        }


        materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                MealModel mealModel = new MealModel();
                if (favorite) {
                    mealModel.setSid(fid);
                    mealModel.setSimage(fimage);
                    mealModel.setSname(ftitle);
                    mealModel.setSdesc(fdesc);
                    materialFavoriteButton.setFavorite(true);
                    Meal.roomModel.insertmeal(mealModel);
                } else {
                    mealModel.setSid(fid);
                    Meal.roomModel.deletemeal(mealModel);
                    Toast.makeText(Mealinfo.this, getString(R.string.delete), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
