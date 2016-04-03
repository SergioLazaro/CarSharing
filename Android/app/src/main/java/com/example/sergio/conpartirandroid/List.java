package com.example.sergio.conpartirandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import AsyncTasks.ListTask;
import Objects.Publication;

/**
 * Created by Sergio on 16/2/16.
 */
public class List extends Fragment {

    private static final String[] spinnerOptions = {"Car","Taxi"};
    private static String selected = "Car";
    private static ListView list;
    private static ArrayList<Publication> carPublications, taxiPublications;
    private static SimpleAdapter arrayAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_fragment, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Creating arrays to load data from AsyncTasks
        carPublications = new ArrayList<Publication>();
        taxiPublications = new ArrayList<Publication>();

        //Setting up the spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.listSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.post_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected = spinnerOptions[position];
                new ListTask(parentView.getContext()).execute(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //Setting up ListView
        list = (ListView) view.findViewById(R.id.listView);
        //new ListTask(view.getContext()).execute(selected);
        //list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                //Should start a new fragment with the data
                if(selected.equals("Car")){
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment fragment = new PostInfo();
                    //Setting arguments
                    Bundle arguments = new Bundle();
                    arguments.putString("Elements", carPublications.get(position).toString());
                    fragment.setArguments(arguments);
                    //Starting fragment transaction
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else{
                    android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment fragment = new PostInfo();
                    //Setting arguments
                    Bundle arguments = new Bundle();
                    arguments.putString("Elements", taxiPublications.get(position).toString());
                    fragment.setArguments(arguments);
                    //Starting fragment transaction
                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

    }

    public static void setAdapter(SimpleAdapter newAdapter, ArrayList<Publication> array){
        if(selected.equals("Car")){
            carPublications = array;
        }
        else{
            taxiPublications = array;
        }
        arrayAdapter = newAdapter;
        list.setAdapter(arrayAdapter);
    }
}
