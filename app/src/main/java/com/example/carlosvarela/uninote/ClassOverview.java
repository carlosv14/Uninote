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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import layout.fragment_notes;

public class ClassOverview extends AppCompatActivity {

    Fragment currentFragment = null;
    Materia materia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("class");
        materia = Materia.fromJson(json);

        String title = "Default title";
        if(materia != null){
            materia.putProperties();
            title = materia.Materia;
        }

        //getActionBar().setTitle(className);
        ArrayAdapter<ParseObject> adapter = NotesFragment();
        getSupportActionBar().setTitle(title);
        ((fragment_notes)currentFragment).fillGrid(adapter);
    }

    private List<ParseObject> updateNotesList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("VoiceNote");
        query.whereEqualTo("Materia", ParseObject.createWithoutData("Materia", materia.objectId));
        try{
            return query.find();
        }catch (com.parse.ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayAdapter<ParseObject> NotesFragment(){
        final ArrayList<ParseObject> notes =(ArrayList) updateNotesList();
        System.out.println(notes.get(0).get("Name"));
        if(notes == null)
            return null;

        ArrayAdapter<ParseObject> adapter = new ArrayAdapter<ParseObject>(this, android.R.layout.simple_list_item_1, notes){
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);
                view.setPadding(10,10,10,10);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.WHITE);
                tv.setText((String) notes.get(position).get("Name"));
                tv.setBackgroundColor(Color.parseColor("#009688"));
                tv.setBackgroundResource(R.drawable.mic);
                return tv;
            }
        };
        Fragment newFragment = new fragment_notes();
        currentFragment = newFragment;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ((fragment_notes)currentFragment).notes = notes;
        return adapter;
    }

    public void onButtonClick(View view){
        ImageButton clickedButton = (ImageButton)view;
        clickedButton.setBackgroundColor(Color.parseColor("#26A69A"));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Notepad();
        currentFragment = newFragment;
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
        currentFragment = newFragment;
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onRecord(View view){
        ImageButton clickedButton = (ImageButton)view;
        clickedButton.setBackgroundColor(Color.parseColor("#26A69A"));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Voicenote(materia);
        currentFragment = newFragment;
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void recordButtonClick(View view) {
        Voicenote voicenoteFragment = (Voicenote) currentFragment;
        if (voicenoteFragment != null){
            voicenoteFragment.recordButtonClick(view);
            ImageButton mRecordButton = (ImageButton)findViewById(R.id.recordButton);
            if(mRecordButton != null)
                mRecordButton.setImageResource(voicenoteFragment.isRecording ? R.drawable.recordbuttonstop : R.drawable.recordbutton);
        }
    }
}
