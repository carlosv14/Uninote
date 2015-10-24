package com.example.carlosvarela.uninote;

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

import java.util.ArrayList;
import java.util.List;


public class Home extends ActionBarActivity {

    public ArrayList<String> classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
