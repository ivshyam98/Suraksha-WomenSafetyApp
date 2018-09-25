package com.example.android.socialloginandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TakeComplaint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_take_complaint );

        final EditText title = findViewById( R.id.title);
        final EditText des = findViewById( R.id.details );
        final EditText date = findViewById( R.id.date );
        final EditText cat = findViewById( R.id.category );
        final EditText name = findViewById( R.id. com_name );
        Button submit = findViewById( R.id.submit );
        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title_content,des_content,nam_content,date_content,cat_content;

                title_content= title.getText().toString().trim();
                des_content = des.getText().toString().trim();
                date_content=  date.getText().toString().trim();
                cat_content = cat.getText().toString().trim();
                nam_content = name.getText().toString().trim();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "complaints" );

                ComplainPOJO complainPOJO = new ComplainPOJO(nam_content ,title_content,des_content,date_content,cat_content );

                databaseReference.push().setValue( complainPOJO ).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                } );

            }
        } );
    }
}
