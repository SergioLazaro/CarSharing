package com.example.sergio.conpartirandroid;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import AsyncTasks.LoginTask;

/**
 * Created by Sergio on 15/2/16.
 */
public class Login extends Fragment {

    private EditText email,password;
    private Button button;
    private String showMessage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Getting arguments
        if(getArguments() != null){
            showMessage = getArguments().getString("Login");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(showMessage != null && showMessage.equals("Needed")){
            Toast.makeText(getActivity(), "You need to login.",
                    Toast.LENGTH_SHORT).show();
        }

        email = (EditText) view.findViewById(R.id.emailLogin);
        password = (EditText) view.findViewById(R.id.passwordLogin);

        button = (Button) view.findViewById(R.id.buttonLogin);
        //Button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(v,email.getText().toString(), password.getText().toString());
            }
        });

    }

    private void confirm(View v, String email, String password){
        if(checkSQLInjection(email) && checkSQLInjection(password)){
            Log.i("Email",email);
            Log.i("Password",password);
            new LoginTask(v.getContext()).execute(email,password);
            //Always allowing access with the email inserted
            MainActivity.setUsername(email);
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = new Post();
            //Setting arguments
            Bundle arguments = new Bundle();
            arguments.putString("Username", email);
            fragment.setArguments(arguments);
            //Starting fragment transaction
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            Toast.makeText(getActivity(), "Invalid username and password",
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
