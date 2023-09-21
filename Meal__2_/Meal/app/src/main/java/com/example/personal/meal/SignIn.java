package com.example.personal.meal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity
{

    private EditText email,passwd;
    private Button signin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.semail);
        passwd=findViewById(R.id.spass);
        signin=findViewById(R.id.signin);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String semail=email.getText().toString();
                String spaswd=passwd.getText().toString();

                if (semail.isEmpty()&&spaswd.isEmpty())
                {
                    Toast.makeText(SignIn.this, getString(R.string.Enteremailandpassword), Toast.LENGTH_SHORT).show();
                }
                else if (semail.isEmpty())
                {
                    email.setError(getString(R.string.Pleaseenteremail));
                    email.requestFocus();
                }
                else if (spaswd.isEmpty())
                {
                    passwd.setError(getString(R.string.Pleaseenterpassword));
                    passwd.requestFocus();
                }
               else if (spaswd.length() <=5) {
                    passwd.setError(getString(R.string.passwordminimumlengthis6));
                    passwd.requestFocus();

                }
                else if (!(semail.isEmpty()&&spaswd.isEmpty()))
                {
                    firebaseAuth.createUserWithEmailAndPassword(semail,spaswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(SignIn.this, getString(R.string.signupsuccessfull), Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(SignIn.this,MainActivity.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Intent intent=new Intent(SignIn.this,MainActivity.class);

                            }


                        }
                    });

                }
                else
                {
                    Toast.makeText(SignIn.this, getString(R.string.Error), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
