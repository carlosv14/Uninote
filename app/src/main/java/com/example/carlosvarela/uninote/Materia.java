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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ParseClassName("Materia")
public class Materia extends ParseObject implements Serializable {

    String objectId;
    String Materia;
    String Catedratico;
    int Aula;
    int uv;
    int Seccion;
    int hour;
    int minute;

    public Materia(){
        objectId = "";
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

    public String getMateriaName() {
        return getString("Materia");
    }

    public void RefreshClass(){
        this.objectId = getString("objectId");
        this.Materia = getString("Materia");
        this.Catedratico = getString("Catedratico");
        this.Aula = getInt("Aula");
        this.Seccion = getInt("Seccion");
        this.uv = getInt("UV");
        this.hour = getInt("Hora");
        this.minute = getInt("Minute");
    }

    public void putProperties(){
        put("Materia", this.Materia);
        put("Catedratico", this.Catedratico);
        put("Aula", this.Aula);
        put("Seccion", this.Seccion);
        put("UV", this.uv);
        put("Hora",this.hour);
        put("Minute",this.minute);
        if(this.objectId != ""){
            put("objectId",this.objectId);
        }
    }

    public void PushClass(){
        putProperties();
        setACL(new ParseACL(ParseUser.getCurrentUser()));
        pinInBackground();
        saveEventually();
    }

    public String toJson(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("Materia",Materia);
            jsonObject.put("Catedratico", Catedratico);
            jsonObject.put("Aula", Aula);
            jsonObject.put("Seccion",Seccion);
            jsonObject.put("UV", uv);
            jsonObject.put("Hora", hour);
            jsonObject.put("Minute", minute);
            jsonObject.put("objectId", this.getObjectId());
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public static Materia fromJson(String json){
        try {

            JSONObject object = new JSONObject(json);
            com.example.carlosvarela.uninote.Materia materia = new Materia();
            materia.Materia = (String) object.get("Materia");
            materia.Catedratico = (String) object.get("Catedratico");
            materia.Aula = (int) object.get("Aula");
            materia.Seccion = (int) object.get("Seccion");
            materia.uv = (int) object.get("UV");
            materia.hour = (int) object.get("Hora");
            materia.minute = (int) object.get("Minute");
            materia.objectId = (String) object.get("objectId");
            return materia;
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
        return null;
    }
}