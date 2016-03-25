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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import Objects.Publication;

/**
 * Created by Sergio on 16/2/16.
 */
public class List extends Fragment {

    private static final String[] spinnerOptions = {"Car","Taxi"};
    private String selected = "Car";
    private ListView list;
    private ArrayList<HashMap<String,String>> array;
    private ArrayList<Publication> carPublications, taxiPublications;
    private SimpleAdapter arrayAdapter;

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
        //Populate both arrays
        populateCars();
        populateTaxis();

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
                Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
                if(selected.equals("Car")){

                }
                else{
                    //Start AsyncTask to get Taxis
                }
                populateArray(selected, parentView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //Setting up ListView
        list = (ListView) view.findViewById(R.id.listView);
        array = new ArrayList<HashMap<String,String>>();
        populateArray(selected,view);
        arrayAdapter = new SimpleAdapter (view.getContext(), array, android.R.layout.two_line_list_item ,
                new String[] { "User","DateFromTo" },
                new int[] {android.R.id.text1, android.R.id.text2});
        list.setAdapter(arrayAdapter);

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

    private void populateArray(String type, View view){
        ArrayList<HashMap<String, String>> newArray = new ArrayList<HashMap<String, String>>();
        if(type.equals("Car")){
            for(int i = 0; i < carPublications.size(); i++){
                HashMap<String, String> din = new HashMap<String, String>(2);
                Publication p = carPublications.get(i);
                din.put("User",p.getUsername());
                din.put("DateFromTo", p.getFrom() + " - " + p.getTo() + " " + p.getDate());
                newArray.add(din);
            }
            array = newArray;
            restartAdapter(newArray,view);
        }
        else{
            for(int i = 0; i < taxiPublications.size(); i++){
                HashMap<String, String> din = new HashMap<String, String>(2);
                Publication p = taxiPublications.get(i);
                din.put("User",p.getUsername());
                din.put("DateFromTo", p.getFrom()  + " " + p.getDate());
                newArray.add(din);
            }
            array = newArray;
            restartAdapter(newArray, view);
        }

    }

    public void populateCars(){
        //Adding some elements
        Publication p1 = new Publication("25/02/2016","Torino","Milano","BMW s1","2014",MainActivity.getUsername());
        Publication p2 = new Publication("25/02/2016","Bologna","Firenze","Fiat punto","2014",MainActivity.getUsername());
        carPublications.add(p1);
        carPublications.add(p2);
    }
    public void populateTaxis(){
        Publication p1 = new Publication("22/02/2016","Torino",MainActivity.getUsername());
        taxiPublications.add(p1);
    }

    private void restartAdapter(ArrayList<HashMap<String,String>> newArray, View view){
        arrayAdapter = new SimpleAdapter (view.getContext(), newArray, android.R.layout.two_line_list_item ,
                new String[] { "User","DateFromTo" },
                new int[] {android.R.id.text1, android.R.id.text2});
        list.setAdapter(arrayAdapter);
    }

    public static void modifyArrayList(ArrayList<String> newArray){
       // array = newArray;
    }
}
