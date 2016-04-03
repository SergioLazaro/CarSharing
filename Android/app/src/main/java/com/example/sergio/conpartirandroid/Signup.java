package com.example.sergio.conpartirandroid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import AsyncTasks.LoginTask;
import AsyncTasks.SignupTask;
import Objects.User;

/**
 * Created by Sergio on 15/2/16.
 */
public class Signup extends Fragment {

    private EditText name,surname,birth,email,password;
    private RadioButton genderM, genderF;
    private Button button;
    private String nameInserted, surnameInserted, birthInserted, genderInserted, emailInserted,
        passwordInserted;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (EditText) view.findViewById(R.id.nameSignup);
        surname = (EditText) view.findViewById(R.id.surnameSignup);
        birth = (EditText) view.findViewById(R.id.birthSignup);
        email = (EditText) view.findViewById(R.id.emailSignup);
        password = (EditText) view.findViewById(R.id.passwordSignup);

        genderM = (RadioButton) view.findViewById(R.id.maleSignup);
        genderM.setChecked(true);   //Gender male checked by default
        genderInserted = "male";
        genderF = (RadioButton) view.findViewById(R.id.femaleSignup);
        //Setting the listener to RadioButton "Male"
        genderM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        //Setting the listener to RadioButton "Female"
        genderF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        button = (Button) view.findViewById(R.id.buttonSignup);
        //Button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInserted = name.getText().toString();
                surnameInserted = surname.getText().toString();
                birthInserted = birth.getText().toString();
                emailInserted = email.getText().toString();
                passwordInserted = password.getText().toString();
                //Create new user
                User user = new User(nameInserted,surnameInserted,birthInserted,
                        genderInserted,emailInserted,passwordInserted);
                confirm(v,user);
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.maleSignup:
                if (checked)
                    genderM.setChecked(true);   //Gender male checked by default
                    genderF.setChecked(false);   //Gender male checked by default
                    genderInserted = "M";
                    break;
            case R.id.femaleSignup:
                if (checked)
                    genderM.setChecked(false);   //Gender male checked by default
                    genderF.setChecked(true);   //Gender male checked by default
                    genderInserted = "F";
                    break;
        }
    }

    private void confirm(View v, User user){
        if(checkSQLInjection(user.getName()) && checkSQLInjection(user.getSurname()) &&
                checkSQLInjection(user.getBirth()) && checkSQLInjection(user.getEmail())
                && checkSQLInjection(user.getPassword())){
            new SignupTask(v.getContext()).execute(user.toString());
            //Throw AsynTask to do the Sign up and change to List fragment view
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new List());
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            Toast.makeText(getActivity(), "Maybe you are doing something wrong.",
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
}
