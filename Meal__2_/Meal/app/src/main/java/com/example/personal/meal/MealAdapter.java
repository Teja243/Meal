package com.example.personal.meal;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MealAdapter  extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {
   private final Context context;
   public ArrayList<MealModel>mealModels;
   public SharedPreferences sPreferences;
   public SharedPreferences.Editor editor;

    public MealAdapter(Meal meal, ArrayList<MealModel> mealModels) {
        this.context=meal;
        this.mealModels=mealModels;
    }

    @NonNull
    @Override
    public MealAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.info,viewGroup,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final MealAdapter.MyViewHolder myViewHolder,final int i) {
         final MealModel meal=mealModels.get(i);
        Glide.with(context).load(mealModels.get(i).simage).into(myViewHolder.imageView1);
        myViewHolder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mealdetails=new String[4];
                mealdetails[0]=mealModels.get(i).getSimage();
                mealdetails[1]=mealModels.get(i).getSname();
                mealdetails[2]=mealModels.get(i).getSdesc();
                mealdetails[3]=mealModels.get(i).getSid();

                Intent intent=new Intent(context,Mealinfo.class);
                intent.putExtra(context.getString(R.string.data2),mealdetails);
                context.startActivity(intent);

                sPreferences=context.getSharedPreferences(context.getString(R.string.data3),Context.MODE_PRIVATE);
                editor=sPreferences.edit();

                editor.putString(context.getString(R.string.name1), meal.sname);
                editor.apply();
                Intent in=new Intent(context,NewAppWidget.class);
                in.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ints=AppWidgetManager.getInstance(context).getAppWidgetIds(
                        new ComponentName(context.getApplicationContext(),NewAppWidget.class));
                in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ints);
                context.sendBroadcast(in);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mealModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1=itemView.findViewById(R.id.mealing);
        }
    }
}
