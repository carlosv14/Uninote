package com.example.carlosvarela.uninote;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddClass extends AppCompatActivity {

    public String Materia;
    public String Catedratico;
    public int uv;
    public int Aula;
    public int Seccion;
    public int hour;
    public int minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar Clase");
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    public void saveData(Bundle Data){
        this.hour = Data.getInt("Hour");
        this.minute= Data.getInt("Minute");
    }


    public void saveClass(View view){

        try {
            Materia = ((EditText) findViewById(R.id.editText15)).getText().toString();
            Catedratico = ((EditText) findViewById(R.id.editText16)).getText().toString();
            uv = Integer.valueOf(((EditText) findViewById(R.id.editText18)).getText().toString());
            Seccion = Integer.valueOf(((EditText) findViewById(R.id.editText17)).getText().toString());
            Aula = Integer.valueOf(((EditText) findViewById(R.id.editText20)).getText().toString());
            Materia materia = new Materia();
            materia.Data(this.Materia,this.Catedratico,this.Aula,this.uv,this.Seccion,this.hour,this.minute);
            materia.PushClass();
            this.finish();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }
        }

}
