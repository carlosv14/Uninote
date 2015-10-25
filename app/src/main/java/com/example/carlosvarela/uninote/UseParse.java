package com.example.carlosvarela.uninote;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Carlos Varela on 10/25/2015.
 */
public class UseParse extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "NqoiN2iCFTNLSNjyJjFEIxD3JFbYkTd9HbJm2Zvj", "reZZBayjfg5HVFJMWC7wme4RmgxbasgWuPTjBCFN");
    }
}
