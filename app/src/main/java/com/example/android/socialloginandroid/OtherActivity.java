package com.example.android.socialloginandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.http.HTTP;

public class OtherActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    ArrayList<ComplainPOJO> complaints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_other );

        ListView complaint_list = findViewById( R.id.other_list );
        final ComplainAdpater complainAdpater = new ComplainAdpater( this, complaints );
        complaint_list.setAdapter( complainAdpater );

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "complaints" );
        databaseReference.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                complaints.add( dataSnapshot.getValue( ComplainPOJO.class ) );
                complainAdpater.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        FloatingActionButton location = findViewById( R.id.location );
        location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( OtherActivity.this, TakeComplaint.class ) );
            }
        } );
        FloatingActionButton fab = findViewById( R.id.addComplaint );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( OtherActivity.this, TakeComplaint.class ) );
            }
        } );


        mSensorManager = ( SensorManager ) getSystemService( Context.SENSOR_SERVICE );
        mAccelerometer = mSensorManager
                .getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener( new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                Toast.makeText( OtherActivity.this, "Phone Is Shook: Sending message to Emergency Contact. ", Toast.LENGTH_SHORT ).show();
Log.i( "ONshake ","Phone is sahked send the mail and message." );

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }


}
