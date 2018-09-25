package com.example.android.socialloginandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class SignUp extends AppCompatActivity {

    EditText email,username,password,phone1,phone2;
    Button signUp,signIn;

    FirebaseAuth mauth;
    DatabaseReference databaseReference;




    UploadTask uploadTask;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.RegisterProgressBar);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        username = findViewById(R.id.Username);
        phone1=findViewById( R.id.phone1 );
        phone2= findViewById( R.id.phone2 );

        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        signIn = findViewById(R.id.logInBtn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,SignIn.class));
                finish();
            }
        });
        signUp = findViewById(R.id.signupButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                if (! username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()
                        &&!phone1.getText().toString().isEmpty() && !phone2.getText().toString().isEmpty()
                         ) {

                    progressBar.setVisibility(View.VISIBLE);


                    final String name_content,password_content,email_content,phone1_content,phone2_content;
                    final int agE;
                    final float weigh,heigh;
                    name_content= username.getText().toString().trim();
                    password_content = password.getText().toString().trim();
                    email_content=  email.getText().toString().trim();
                    phone1_content = phone1.getText().toString().trim();
                    phone2_content = phone2.getText().toString().trim();


                    mauth.createUserWithEmailAndPassword(email_content,password_content).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String user_id = mauth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = databaseReference.child(user_id);
                                current_user_db.child("username").setValue(name_content);
                                current_user_db.child("email").setValue(email_content);
                                current_user_db.child("phone1").setValue(phone1_content);
                                current_user_db.child("phone2").setValue(phone2_content);

                                startActivity(new Intent(SignUp.this,OtherActivity.class));
                                finish();
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUp.this, "Successfully Registered !", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });



                }else {
                    Toast.makeText(SignUp.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
