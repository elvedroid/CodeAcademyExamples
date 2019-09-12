package com.example.vezbapay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnDateSetListener {

    EditText etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etDate = findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment fragment = new DatePickerFragment(MainActivity.this);
                fragment.show(getSupportFragmentManager(), null);
            }
        });

        final EditText etPrice = findViewById(R.id.etPrice);

        Spinner spinner = findViewById(R.id.spiner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (etPrice.length() == 0) {
                    return;
                }

                double previousPrice = Double.valueOf(etPrice.getText().toString());

                String[] values = getResources().getStringArray(R.array.values);
                if (values[position].equalsIgnoreCase("mkd")) {
                    double newPrice = previousPrice * 61.5;
                    etPrice.setText(String.valueOf(newPrice));
                } else if (values[position].equalsIgnoreCase("eur")) {
                    double newPrice = previousPrice / 61.5;
                    etPrice.setText(String.valueOf(newPrice));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnPay = findViewById(R.id.buttonPAY);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etName = findViewById(R.id.etName);
                if (etName.length() == 0) {
                    etName.setError("Name must be set");
                    etName.requestFocus();
                    return;
                }


                EditText etSurname = findViewById(R.id.etSurname);

//                Ako surname i price se prazni da se javi error(Isto kako i name),
//                Ako price ima vrednost 0, da se javi error (Price must be more than 0)
//                Ako price e pogolemo od 1 000 000 MKD, da se javi error (Price must be less then 1 000 000 MKD)
//                Ako price e pogolemo od 200 000 EURO, da se javi error (Price must be less then 200 000 EUR)
//                Da se postavi pocetna vrednost na etDate da bide momentalniot datum.
//                Na kraj da se kreira objekt Transaction i da se prikaze Toast (Uspesno kreirana transakcija)
            }
        });


    }


    @Override
    public void setDate(Calendar calendar) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMMM-dd",
                Locale.getDefault());
        String date = simpleDateFormat.format(calendar.getTime());

        etDate.setText(date);
    }

}
