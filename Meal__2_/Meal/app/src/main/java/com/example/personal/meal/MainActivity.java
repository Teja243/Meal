package com.example.personal.meal;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
   private EditText editText1;
    public EditText editText2;
   public FirebaseAuth firebaseAuth;
   public Button button1;
   public AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.eemail);
        editText2=findViewById(R.id.epass);
        button1=findViewById(R.id.login);
        firebaseAuth=FirebaseAuth.getInstance();



        MobileAds.initialize(this);
        adView=findViewById(R.id.aadmob);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }
    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }


    public void Signup(View view) {

        Intent intent=new Intent(MainActivity.this,SignIn.class);
          startActivity(intent);
    }


    public void Login(View view) {
        if (isOnline()) {

            String Email = editText1.getText().toString();
            String pass = editText2.getText().toString();

            if (Email.isEmpty()) {
                editText1.setError(getString(R.string.EnterEmail));

            }
            if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                editText1.setError(getString(R.string.enterproperemail));
                editText1.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                editText2.setError(getString(R.string.enterpassword));
                editText2.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                editText2.setError(getString(R.string.enterpassword));
                editText2.requestFocus();
                return;
            }
            if (pass.length() <= 5) {
                editText2.setError(getString(R.string.passwordminimumlengthis6));
                editText2.requestFocus();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, Meal.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.Entervaliddetails), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
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







