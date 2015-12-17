package com.example.carlosvarela.uninote;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.Serializable;
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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        query.whereEqualTo("SeccionMateria", materia.Seccion);
        try{
            return query.find();
        }catch (com.parse.ParseException e){
            e.printStackTrace();
        }
        return null;
    }


    public ArrayAdapter<ParseObject> NotesFragment(){
        final ArrayList<ParseObject> notes =(ArrayList) updateNotesList();
        if(notes == null)
            return null;

        ArrayAdapter<ParseObject> adapter = new ArrayAdapter<ParseObject>(this, R.layout.note_grid_item, R.id.noteTextItem,notes){
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);
                view.setPadding(10, 10, 10, 10);
                LinearLayout linearLayout = (LinearLayout) view;
                TextView tv =(TextView) linearLayout.findViewById(R.id.noteTextItem);
                tv.setTextColor(Color.WHITE);
                ParseObject note = notes.get(position);
                tv.setText((String) note.get("Name"));
                //tv.setBackgroundColor(Color.parseColor("#009688"));
                //System.out.println(note.get("Type"));
                ImageView imageView = (ImageView)linearLayout.findViewById(R.id.noteImageItem);
                if( note.get("Type").equals("VoiceNote"))
                    imageView.setImageResource(R.drawable.mic);
                else if(note.get("Type").equals("ImageNote"))
                    imageView.setImageResource(R.drawable.camera);
                return linearLayout;
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
       /** FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Notepad();
        currentFragment = newFragment;
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();**/


        Intent intent = new Intent(this,DrawPad.class);
        intent.putExtra("Materia", (Serializable) materia);
        startActivity(intent);
    }

    public void onCamera(View view){
        ImageButton clickedButton = (ImageButton)view;
        clickedButton.setBackgroundColor(Color.parseColor("#26A69A"));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Camera(materia);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
