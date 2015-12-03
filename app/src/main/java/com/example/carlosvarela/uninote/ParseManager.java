package com.example.carlosvarela.uninote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    takings.add(a);
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

    public static ParseObject uploadImageToParse(Bitmap image, ParseObject po, String columnName){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        System.out.println(byteArray.length);
        ParseFile file = new ParseFile("Image_Tests.png", byteArray);
        po.put(columnName, file);
        po.put("Type", "ImageNote");

        // Upload the file into Parse Cloud

        try {
            file.save();
            po.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return po;
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
            System.out.println(audioBytes.length);
            // Create the ParseFile
            ParseFile file = new ParseFile(audioFile.getName(), audioBytes);
            po.put(columnName, file);
            po.put("Type", "VoiceNote");

            // Upload the file into Parse Cloud
            file.saveInBackground();
            po.saveInBackground();
        }
        return po;
    }
}
