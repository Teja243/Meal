package com.example.personal.meal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Meal extends AppCompatActivity {

   private RecyclerView recyclerView;
   public ArrayList<MealModel>mealModels;
   public static RoomModel roomModel;
   public FirebaseAuth auth;
   public final String mealurl = "https://www.themealdb.com/api/json/v1/1/categories.php";
   public String string1="str";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        recyclerView = findViewById(R.id.recyler);
        mealModels=new ArrayList<>();
        roomModel=ViewModelProviders.of(this).get(RoomModel.class);
        new MyTask().execute(mealurl);
        auth=FirebaseAuth.getInstance();


    }
    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){

            return false;
        }
        return true;
    }
    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    InputStream inputStream = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner != null) {
                        return scanner.next();
                    } else {
                        return null;

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute (String s){
                super.onPostExecute(s);
                if (isOnline()) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.categories));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject value = jsonArray.getJSONObject(i);
                        String id = value.getString(getString(R.string.idCategory));
                        String name = value.getString(getString(R.string.strCategory));
                        String image = value.getString(getString(R.string.strCategoryThumb));
                        String desc = value.getString(getString(R.string.strCategoryDescription));
                        mealModels.add(new MealModel(id, name, image, desc));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new GridLayoutManager(Meal.this, 2));
                recyclerView.setAdapter(new MealAdapter(Meal.this, mealModels));

            }
                else
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(Meal.this);
                    builder.setTitle(getString(R.string.MealInfo));
                    builder.setMessage(getString(R.string.pleasecheckyourconnection));
                    builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.fav:
                    string1 = "favo";
                    roomModel.getFav().observe(this, new Observer<List<MealModel>>() {
                        @Override
                        public void onChanged(@Nullable List<MealModel> movieModels) {
                            recyclerView.setLayoutManager(new GridLayoutManager(Meal.this, 2));
                            recyclerView.setAdapter(new MealAdapter(Meal.this, (ArrayList<MealModel>) movieModels));
                        }
                    });
                    break;
                case R.id.log:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Meal.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.meal:
                    new MyTask().execute(mealurl);
                    break;

            }
            return super.onOptionsItemSelected(item);
        }




}
