package com.example.carlosvarela.uninote;

/**
 * Created by Carlos Varela on 10/31/2015.
 */
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Materia")
public class Materia extends ParseObject {

    String Materia;
    String Catedratico;
    int Aula;
    int uv;
    int Seccion;
    int hour;
    int minute;

    public Materia(){
        
    }
    public void Data(String Materia,
            String Catedratico,
            int Aula,
            int uv,
            int Seccion,int hour, int minute){
        this.Materia = Materia;
        this.Aula = Aula;
        this.Catedratico = Catedratico;
        this.Seccion = Seccion;
        this.uv  = uv;
        this.hour = hour;
        this.minute  = minute;
    }

    
    public void PushClass(){
        put("Materia", this.Materia);
        put("Catedratico", this.Catedratico);
        put("Aula", this.Aula);
        put("Seccion", this.Seccion);
        put("UV", this.uv);
        put("Hora",this.hour);
        put("Minute",this.minute);
        setACL(new ParseACL(ParseUser.getCurrentUser()));
        pinInBackground();
        saveEventually();
    }

}