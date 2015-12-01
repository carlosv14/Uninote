package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public static ParseObject uploadAudioToParse(File audioFile, ParseObject po, String columnName) {

        if (audioFile != null) {
            Log.d("EB", "audioFile is not NULL: " + audioFile.toString());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = null;
            try {
                in = new BufferedInputStream(new FileInputStream(audioFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int read;
            byte[] buff = new byte[1024];
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] audioBytes = out.toByteArray();

            // Create the ParseFile
            ParseFile file = new ParseFile(audioFile.getName(), audioBytes);
            po.put(columnName, file);

            // Upload the file into Parse Cloud
            file.saveInBackground();
            po.saveInBackground();
        }
        return po;
    }
}
