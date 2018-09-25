package com.example.android.socialloginandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Button signUp = findViewById( R.id.signUp );

        Button signIn = findViewById( R.id.signIn );

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {

            signIn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity( new Intent( MainActivity.this, SignIn.class ) );
                }
            } );

            signUp.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity( new Intent( MainActivity.this, SignUp.class ) );
                }
            } );
        }else {
            startActivity( new Intent( MainActivity.this,OtherActivity.class ) );
        }

    }
}
