package com.example.android.socialloginandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ComplainAdpater extends ArrayAdapter<ComplainPOJO> {

    Context context;
    public ComplainAdpater(@NonNull Context context, ArrayList<ComplainPOJO> items) {
        super( context, 0,items );
        this.context  =context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from( getContext() ).inflate( R.layout.complain_list_item ,parent,false);

        }

        ComplainPOJO complainPOJO = getItem( position );

        TextView title = listItemView.findViewById( R.id.title );
        TextView des = listItemView.findViewById( R.id.description);
        TextView cat= listItemView.findViewById( R.id.category);
        TextView date = listItemView.findViewById( R.id.date );
        TextView name = listItemView.findViewById( R.id.name );

        if(complainPOJO != null){
            title.setText("Title: " +complainPOJO.getTitle() );
            des.setText( "Descsription: "+complainPOJO.getDescription() );
            date.setText( "Date: "+complainPOJO.getDate() );
            name.setText( "Name:  "+complainPOJO.getName() );
            cat.setText( "Category: "+complainPOJO.getCategory() );
        }
        return listItemView;
    }
}
