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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ClassOverview extends AppCompatActivity {

    Fragment currentFragment = null;
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
       /** FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment newFragment = new Notepad();
        currentFragment = newFragment;
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();**/
        Intent intent = new Intent(this,DrawPad.class);
        startActivity(intent);
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

        Fragment newFragment = new Voicenote();
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
        menu.add(Menu.NONE, 0, Menu.NONE, "Agregar Compañero");
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
        }else if(id == 0){
            Intent i = new Intent(this,AddClassmate.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
