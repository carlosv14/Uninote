package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Varela on 10/31/2015.
 */
public  class ParseManager {

    public static ArrayList<Materia> GetClasses(final ArrayAdapter<Materia> adapter){
        final ArrayList<Materia> materias = new ArrayList<>();
        ParseQuery<Materia> query = ParseQuery.getQuery(Materia.class);
        query.findInBackground(new FindCallback<Materia>() {
            @Override
            public void done(List<Materia> results, ParseException e) {
                for (Materia a : results) {
                    a.RefreshClass();
                    System.out.println(a.Materia);
                    adapter.add(a);
                }
            }
        });
        //adapter.clear();
        //adapter.remove(adapter.getItem(0));
        adapter.notifyDataSetChanged();
        return materias;
    }

    protected static void LogOut(){
        ParseUser.logOut();
    }
}
