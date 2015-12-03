package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AddClassmate extends AppCompatActivity  implements View.OnKeyListener {
    public ArrayList<Taking> classmates;
    public ArrayList<String > classmatesnames;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classmate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText username = (EditText) findViewById(R.id.editText9);
        username.setOnKeyListener(this);
        lv = (ListView) findViewById(R.id.listView);

    }
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        classmates = new ArrayList<>();
        classmatesnames = new ArrayList<>();
        EditText myEditText = (EditText) view;

        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity", "Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.editText9:
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classmatesnames);
                        ParseManager.GetTakingUsers(adapter);
                        lv.setAdapter(adapter);
                        /*result
                                .setText("Just pressed the ENTER key, " +
                                        "focus was on Text Box1. " +
                                        "You typed:\n" + myEditText.getText());*/
                        break;

                }
                return true;
            }

        }
        return false;

    }



}
