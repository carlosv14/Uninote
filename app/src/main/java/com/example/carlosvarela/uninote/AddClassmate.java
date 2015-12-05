package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AddClassmate extends AppCompatActivity {
    public ArrayList<Taking> classmates;
    public ArrayList<String > classmatesnames;
    ArrayAdapter<String> adapter;
    private ListView lv;
    Intent i;
    String objId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent();
        objId = i.getStringExtra("ID");
        setContentView(R.layout.activity_add_classmate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText username = (EditText) findViewById(R.id.editText9);
        lv = (ListView) findViewById(R.id.listView);
        classmates = new ArrayList<>();
        classmatesnames = new ArrayList<>();
        classmatesnames.add("");
         adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classmatesnames);
         classmates=ParseManager.GetTakingUsers(adapter);
        classmatesnames.remove(0);
        lv.setAdapter(adapter);
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AddClassmate.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            ParseUser user;
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                final ParseACL acl = new ParseACL();
               for (Taking classm: classmates
                        ) {
                    try {
                        String data = classm.User.fetch().getUsername()+" <"+classm.User.fetch().getEmail()+">";
                        if(data.equals(lv.getItemAtPosition(position))){
                           user = classm.User;
                            break;
                        }
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                }

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
                query.whereEqualTo("objectId", objId);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> results, ParseException e) {
                        for (ParseObject a : results) {
                            ParseACL p = a.getACL();
                            p.setReadAccess(user,true);
                            a.setACL(p);
                            a.pinInBackground();
                            a.saveEventually();

                        }

                    }
                });


            }
        });
    }




}
