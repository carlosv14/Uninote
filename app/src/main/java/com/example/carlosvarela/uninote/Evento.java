package com.example.carlosvarela.uninote;

import com.parse.ParseACL;
import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Carlos Varela on 11/20/2015.
 */
@ParseClassName("Evento")
public class Evento extends ParseObject {
    String Nombre;
    Date Fecha;

    public Evento(){

    }

    public void Data(String nombre,
                     Date fecha){
        this.Nombre = nombre;
        this.Fecha  = fecha;
    }


    public void PushClass(){
        put("Nombre", this.Nombre);
        put("Fecha", this.Fecha);
        setACL(new ParseACL(ParseUser.getCurrentUser()));
        pinInBackground();
        saveEventually();
    }
}
