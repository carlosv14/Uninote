package com.example.carlosvarela.uninote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public int SignUp(View view){
        EditText username = (EditText)findViewById(R.id.editText8);
        EditText password =  (EditText)findViewById(R.id.editText6);
        EditText passwordc =  (EditText)findViewById(R.id.editText7);
        EditText email =  (EditText)findViewById(R.id.editText7);
        EditText nombre =  (EditText)findViewById(R.id.editText3);
        EditText apellido =  (EditText)findViewById(R.id.editText4);
        if(password.getText().toString().equals(passwordc.getText().toString())){

        CreateUser(username.getText().toString(),password.getText().toString(),email.getText().toString(),nombre.getText().toString(),apellido.getText().toString());
        return 1;
        }  else {
            return  0;
        }
    }
    protected void CreateUser(String username, String password, String email, String nombre, String apellido ){


        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.put("nombre", nombre);
        user.put("apellido", apellido);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
