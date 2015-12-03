package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
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

    public static ArrayList<Taking> GetTakingUsers( final ArrayAdapter<String> classmates){
        final ArrayList<Taking> takings = new ArrayList<>();
        ParseQuery<Taking> query = ParseQuery.getQuery(Taking.class);
       query.whereEqualTo("Seccion", 6);
        query.findInBackground(new FindCallback<Taking>() {
            @Override
            public void done(List<Taking> results, ParseException e) {
                for (Taking a : results) {
                   a.RefreshClass();
                    System.out.println(a.User);
                    try {
                        classmates.add(a.User.fetch().getUsername()+" <"+a.User.fetch().getEmail()+">");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        //adapter.clear();
        //adapter.remove(adapter.getItem(0));
        classmates.notifyDataSetChanged();
        return takings;
    }


    protected static void LogOut(){
        ParseUser.logOut();
    }
}
