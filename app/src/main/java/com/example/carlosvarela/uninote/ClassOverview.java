package com.example.carlosvarela.uninote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ClassOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String className = bundle.getString("class");

        //getActionBar().setTitle(className);
        getSupportActionBar().setTitle(className);

    }

    public void onButtonClick(View view){
        ImageButton clickedButton = (ImageButton)view;
        clickedButton.setBackgroundColor(Color.parseColor("#26A69A"));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Notepad();

        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onCamera(View view){
        ImageButton clickedButton = (ImageButton)view;
        clickedButton.setBackgroundColor(Color.parseColor("#26A69A"));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Camera();
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
