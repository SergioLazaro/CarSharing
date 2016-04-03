package com.example.sergio.conpartirandroid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Scanner;

import Objects.Publication;

/**
 * Created by Sergio on 20/2/16.
 */
public class PostInfo extends Fragment {

    private String info;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Getting arguments
        if(getArguments() != null){
            info = getArguments().getString("Elements");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.post_info_fragment, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Publication display = getDisplay();

        //Display username
        TextView usernameText = (TextView) view.findViewById(R.id.displayUsername);
        usernameText.setText(display.getUsername());
        //Display Date
        TextView dateText = (TextView) view.findViewById(R.id.displayDate);
        dateText.setText(display.getDate());
        //Display From
        TextView fromText = (TextView) view.findViewById(R.id.displayFrom);
        fromText.setText(display.getFrom());

        if(!display.getTo().equals("")){    //Check if there is a Car Publication
            //Display To
            TextView toText = (TextView) view.findViewById(R.id.displayTo);
            toText.setText(display.getTo());
        }
        else{       //Otherwise, we have to change 'from' to 'city'
            TextView fromOrCity = (TextView) view.findViewById(R.id.fromOrCityText);
            fromOrCity.setText("City: ");
            //Do not display To layout:
            LinearLayout destinationLayout = (LinearLayout) view.findViewById(R.id.destinationLayout);
            destinationLayout.setVisibility(View.INVISIBLE);
        }

        if(display.getCarname().equals("") && display.getCaryear().equals("")){
            //Hide car type and car year layouts
            LinearLayout carTypeLayout = (LinearLayout) view.findViewById(R.id.cartypeLayout);
            carTypeLayout.setVisibility(View.INVISIBLE);
            LinearLayout carYearLayout = (LinearLayout) view.findViewById(R.id.caryearLayout);
            carYearLayout.setVisibility(View.INVISIBLE);
        }
        else{
            //Display Car type
            TextView cartypeText = (TextView) view.findViewById(R.id.displayCarType);
            cartypeText.setText(display.getCarname());
            //Display Car year
            TextView caryearText = (TextView) view.findViewById(R.id.displayCarYear);
            caryearText.setText(display.getCaryear());
        }

    }

    private Publication getDisplay(){
        Scanner s = new Scanner(info);
        s.useDelimiter(":");
        return new Publication(s.next(),s.next(),s.next(),s.next(),s.next(),s.next());
    }
}
