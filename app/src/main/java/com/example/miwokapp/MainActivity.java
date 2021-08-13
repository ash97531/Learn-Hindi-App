package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        NumberClickListener clickListener = new NumberClickListener();

        //Find the view that shows the number category
        TextView number = (TextView)findViewById(R.id.numbers);
        //Set a clicklistener on that view
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(i);
            }
        });

        //  __E__X__P__R__I__M__E__N__T__
//        number.setOnLongClickListener(new View.OnLongClickListener(){
//
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(getApplication().getBaseContext(),"Maximum quantity is 100 !!!",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        number.setOnClickListener(clickListener);
        //  __E__X__P__R__I__M__E__N__T__

        //find the view that show phrases category
        TextView phrase = (TextView) findViewById(R.id.phrases);
        phrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PhraseActivity.class);
                startActivity(i);
            }
        });

        //find the view that show colours category
        TextView color = (TextView) findViewById(R.id.colours);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ColourActivity.class);
                startActivity(i);
            }
        });

        //find the view that show family members category
        TextView family = (TextView) findViewById(R.id.family_members);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(i);
            }
        });
    }

//    public void opennumberlist(View view){
//        Intent i = new Intent(this, NumberActivity.class);
//        startActivity(i);
//    }
}