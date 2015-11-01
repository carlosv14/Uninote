package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by Carlos Varela on 10/31/2015.
 */
public  class ParseManager {



    protected static void LogOut(){
        ParseUser.logOut();
    }
}
