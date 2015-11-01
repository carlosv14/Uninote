package com.example.carlosvarela.uninote;

/**
 * Created by Carlos Varela on 10/31/2015.
 */
import com.parse.ParseObject;
import com.parse.ParseClassName;

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
        pinInBackground();
        saveEventually();
    }

}