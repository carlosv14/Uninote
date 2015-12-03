package com.example.carlosvarela.uninote;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Carlos Varela on 11/30/2015.
 */
@ParseClassName("Taking")
public class Taking extends ParseObject {
    String objectId;
    ParseUser User;
    int Seccion;
    public Taking(){
        objectId = "";
    }
    public void Data(int Seccion){
        this.Seccion = Seccion;
        this.User = ParseUser.getCurrentUser();
    }



    public void RefreshClass(){
        this.objectId = getString("objectId");
        this.Seccion = getInt("Seccion");
        this.User = getParseUser("User");
    }

    public void putProperties(){
        put("Seccion", this.Seccion);
        put("User",this.User);
        if(this.objectId != ""){
            put("objectId",this.objectId);
        }
    }

    public void PushClass(){
        putProperties();
        pinInBackground();
        saveEventually();
    }

    public String toJson(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("Seccion",Seccion);
            jsonObject.put("User", User);
            jsonObject.put("objectId", this.getObjectId());
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public static Taking fromJson(String json){
        try {

            JSONObject object = new JSONObject(json);
            com.example.carlosvarela.uninote.Taking taking = new Taking();
            taking.Seccion = (int) object.get("Seccion");
            taking.User = (ParseUser) object.get("User");
            taking.objectId = (String) object.get("objectId");
            return taking;
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
        return null;
    }
}
