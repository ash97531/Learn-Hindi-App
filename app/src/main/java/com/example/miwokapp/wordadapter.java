package com.example.miwokapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class wordadapter extends ArrayAdapter <word>{
    private int mcolourresourceid;


    public wordadapter(Activity context, ArrayList<word> items,int color){
        super(context,0,items);
        mcolourresourceid=color;
    }
    
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        word currentword = getItem(position);


        TextView miwokword = (TextView) listItemView.findViewById(R.id.miwokword);
        miwokword.setText(currentword.getMiwokTranslation());

        TextView defaultword = (TextView) listItemView.findViewById(R.id.defaultword);
        defaultword.setText(currentword.getDefaultTranslation());

        ImageView defaultimage = (ImageView) listItemView.findViewById(R.id.image);
        defaultimage.setImageResource(currentword.getImage());
        defaultimage.setVisibility(currentword.getVisibility());


        //set colour on layout
        //set the theme color for list item
        View textcontainer = listItemView.findViewById(R.id.wordlayout);
        //find the colour that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mcolourresourceid);
        //set the background colour of wordlayout view
        textcontainer.setBackgroundColor(color);

        textcontainer = listItemView.findViewById(R.id.play_pause_layout);
        textcontainer.setBackgroundColor(color);



//        return super.getView(position, convertView, parent);
        return listItemView;
    }


}
