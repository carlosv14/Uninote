package com.example.carlosvarela.uninote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class Home extends ActionBarActivity {

    public ArrayList<String> classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            classes = new ArrayList<String>();
            GridView lv = (GridView)findViewById(android.R.id.list);
            classes.add("Algebra");
            classes.add("Sociologia");
            classes.add("Historia de Honduras");
            classes.add("Espanol");
            if(lv != null)
                lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classes){
                    public View getView(int position, View convertView, ViewGroup parent) {

                        // Return the GridView current item as a View
                        View view = super.getView(position,convertView,parent);

                        // Convert the view as a TextView widget
                        TextView tv = (TextView) view;

                        // set the TextView text color (GridView item color)
                        tv.setTextColor(Color.WHITE);

                        // Set the TextView text (GridView item text)
                        tv.setText(classes.get(position));

                        // Set the TextView background color
                        tv.setBackgroundColor(Color.parseColor("#009688"));

                        // Return the TextView widget as GridView item
                        return tv;
                    }
                });

            if (lv != null) {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {

                        Intent i = new Intent(Home.this, ClassOverview.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("class", classes.get(position));
                        i.putExtras(bundle);
                        startActivity(i);


                    }
                });
            }
        } else {
            Intent intent = new Intent(this,MainActivity.class);
            startActivityForResult(intent, 1);
       }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                classes = new ArrayList<String>();
                GridView lv = (GridView)findViewById(android.R.id.list);
                classes.add("Algebra");
                classes.add("Sociologia");
                classes.add("Historia de Honduras");
                classes.add("Espanol");
                if(lv != null)
                    lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classes){
                        public View getView(int position, View convertView, ViewGroup parent) {

                            // Return the GridView current item as a View
                            View view = super.getView(position,convertView,parent);

                            // Convert the view as a TextView widget
                            TextView tv = (TextView) view;

                            // set the TextView text color (GridView item color)
                            tv.setTextColor(Color.WHITE);

                            // Set the TextView text (GridView item text)
                            tv.setText(classes.get(position));

                            // Set the TextView background color
                            tv.setBackgroundColor(Color.parseColor("#009688"));

                            // Return the TextView widget as GridView item
                            return tv;
                          }
                    });

                if (lv != null) {
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            Intent i = new Intent(Home.this, ClassOverview.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("class", classes.get(position));
                            i.putExtras(bundle);
                            startActivity(i);


                        }
                    });
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent intent = new Intent(this,MainActivity.class);
                startActivityForResult(intent, 1);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        menu.add(Menu.NONE, 0, Menu.NONE, "Agregar Clase");
        menu.add(Menu.NONE, 1, Menu.NONE,"Agregar Evento");
        menu.add(Menu.NONE, 2, Menu.NONE, "Logout");
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
            Intent i = new Intent(this,AddClass.class);
            startActivity(i);
        }else if(id == 1){
            id = -1;
            Intent intent = new Intent(this, Event.class);
            startActivity(intent);
        }else if(id == 2){
            id = -1;
            ParseManager.LogOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();

        }

        return super.onOptionsItemSelected(item);
    }


}
