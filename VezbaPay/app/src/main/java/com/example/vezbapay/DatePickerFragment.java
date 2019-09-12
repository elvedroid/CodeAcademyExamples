package com.example.vezbapay;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDateSetListener listener;

    public DatePickerFragment(OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int datOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getContext(), this, year, month, datOfMonth);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int datOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, datOfMonth);

        if (listener != null) {
            listener.setDate(calendar);
        }
    }
}
