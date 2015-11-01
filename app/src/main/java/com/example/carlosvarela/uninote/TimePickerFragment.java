package com.example.carlosvarela.uninote;

import android.app.Dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Carlos Varela on 10/31/2015.
 */
public  class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle bundle = new Bundle();
        bundle.putInt("Hour",hourOfDay);
        bundle.putInt("Minute", minute);
        AddClass activity = (AddClass) getActivity();
        activity.saveData(bundle);
    }


}