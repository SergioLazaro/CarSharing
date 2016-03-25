package com.example.sergio.conpartirandroid;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import AsyncTasks.LoginTask;
import AsyncTasks.PostTask;
import AsyncTasks.SignupTask;
import Objects.Publication;

/**
 * Created by Sergio on 16/2/16.
 */
public class Post extends Fragment {

    private static final String[] spinnerOptions = {"Car","Taxi"};
    private static final String[] cities = {"-","Bologna", "Firenze", "Genova", "Milano", "Napoli",
            "Padova", "Pisa", "Roma", "Siena", "Torino", "Venezia", "Verona"};
    private String selected = "Car";
    private String selectedFrom = "-";
    private String selectedTo = "-";

    private EditText carType, carYear, dateText;
    private TextView labelToChange;
    private LinearLayout destinationLayout, carnameLayout, cartypeLayout;

    private Calendar myCalendar;

    private String showMessage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Getting arguments
        if(getArguments() != null){
            showMessage = getArguments().getString("Username");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.post_fragment, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(MainActivity.getUsername().equals("unknown")){
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = new Login();
            //Setting arguments
            Bundle arguments = new Bundle();
            arguments.putString("Login", "Needed");
            fragment.setArguments(arguments);
            //Starting fragment transaction
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        if(showMessage != null){
            Toast.makeText(getActivity(), "Welcome " + showMessage, Toast.LENGTH_SHORT).show();
        }

        //Setting up our options Spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.post_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selected.equals("Car") && spinnerOptions[position].equals("Taxi")) {
                    labelToChange.setText("City: ");
                    destinationLayout.setVisibility(View.INVISIBLE);
                    carnameLayout.setVisibility(View.INVISIBLE);
                    cartypeLayout.setVisibility(View.INVISIBLE);
                }
                else{
                    labelToChange.setText("From: ");
                    destinationLayout.setVisibility(View.VISIBLE);
                    carnameLayout.setVisibility(View.VISIBLE);
                    cartypeLayout.setVisibility(View.VISIBLE);
                }
                selected = spinnerOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        labelToChange = (TextView) view.findViewById(R.id.labelToChange);
        //Set from/to destination spinners
        configureFromToSpinners(view);

        //Set calendar EditText to show Dialog with calendar
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dateText = (EditText) view.findViewById(R.id.calendarText);
        dateText.setGravity(Gravity.CENTER_HORIZONTAL);
        dateText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        carType = (EditText) view.findViewById(R.id.carname);
        carYear = (EditText) view.findViewById(R.id.caryear);

        Button button = (Button) view.findViewById(R.id.button);
        //Button listener
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirm(v);
            }
        });

        //Getting layouts
        destinationLayout = (LinearLayout) view.findViewById(R.id.destinationLayout);
        carnameLayout = (LinearLayout) view.findViewById(R.id.carnameLayout);
        cartypeLayout = (LinearLayout) view.findViewById(R.id.cartypeLayout);
    }

    private void updateLabel(){
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(myCalendar.getTime()));
    }

    private void configureFromToSpinners(View view){
        //Setting up FROM Spinner
        Spinner fromSpinner = (Spinner) view.findViewById(R.id.fromSpinner);
        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.cities, android.R.layout.simple_spinner_item);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedFrom = cities[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //Setting up TO Spinner
        Spinner toSpinner = (Spinner) view.findViewById(R.id.toSpinner);
        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.cities, android.R.layout.simple_spinner_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedTo = cities[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    /**
     * Method called when CONFIRM button is clicked
     * @param v
     */
    private void confirm(View v){
        boolean valuesChecked = checkValues();
        if(selected.equals("Car") && valuesChecked){
            Publication p = new Publication(dateText.getText().toString(),selectedFrom,selectedTo,
                    carType.getText().toString(),carYear.getText().toString(),MainActivity.getUsername());
            //Starting Async task
            new PostTask(v.getContext()).execute("Car",p.getDate(),p.getFrom(),p.getTo(),
                    p.getCarname(),p.getCaryear());

            //Throw AsynTask to do the Sign up and change to List fragment view
            Toast.makeText(getActivity(), "Post added successfuly",
                    Toast.LENGTH_SHORT).show();
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new List());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(selected.equals("Taxi") && valuesChecked){
            Publication p = new Publication(dateText.getText().toString(),selectedFrom,MainActivity.getUsername());

            //Starting Async task
            new PostTask(v.getContext()).execute("Taxi",p.getDate(),p.getFrom());

            //Throw AsynTask to do the Sign up and change to List fragment view
            Toast.makeText(getActivity(), "Post added successfuly",
                    Toast.LENGTH_SHORT).show();
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new List());
            ft.addToBackStack(null);
            ft.commit();
        }
        else{   //When some field is empty
            Toast.makeText(getActivity(), "Some fields are empty. Please write it.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method used to check every line inserted by users to avoid SQL Injection.
     * This method must be available on every file.
     * @param line
     * @return true if [line] doesnt contains words like DROP, INSERT INTO, DELETE FROM OR UPDATE.
     */
    private boolean checkSQLInjection(String line){
        if(!line.contains("DROP") || !line.contains("INSERT INTO") || !line.contains("DELETE FROM")
                || !line.contains("UPDATE") || !line.contains("<") || !line.contains(">")
                || !line.contains("OR")){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method used to check that all fields are not empty
     * @return true if, and only if, all fields are not empty
     */
    private boolean checkValues(){
        boolean valid = false;
        if(selected.equals("Car")){
            if(!dateText.getText().toString().equals("") && !selectedFrom.equals("-") && !selectedTo.equals("-")
                    && !carType.getText().toString().equals("") && !carYear.getText().toString().equals("")){
                valid = true;
            }
        }
        else{
            if(!dateText.getText().toString().equals("") && !selectedFrom.equals("-")){
                valid = true;
            }
        }
        return valid;
    }
}
