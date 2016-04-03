package AsyncTasks;

/**
 * Created by Sergio on 16/2/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.conpartirandroid.MainActivity;
import com.example.sergio.conpartirandroid.Post;
import com.example.sergio.conpartirandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import Objects.User;


public class SignupTask  extends AsyncTask<String,Void,String> {
    private Context context;

    private User userObject;
    private String user;
    private static final String ip = "192.168.1.82";

    public SignupTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        user = (String)arg0[0];
        userObject = getUserObject(user);

        try {
            String link = "http://" + ip + ":8080/CarSharing-war/SignupServlet?version=android";
            String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(userObject.getEmail(), "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(userObject.getPassword(), "UTF-8");
            data += "&" + URLEncoder.encode("birth", "UTF-8") + "=" + URLEncoder.encode(userObject.getBirth(), "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(userObject.getGender(), "UTF-8");
            data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(userObject.getName(), "UTF-8");
            data += "&" + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(userObject.getSurname(), "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(String line){
        Log.i("RESULT SIGN UP", line);
        try {
            JSONObject json = new JSONObject(line);
            boolean result = Boolean.parseBoolean(json.getString("result"));
            Log.i("VALUE SIGN UP", "" + result);
            if (result) {
                Toast.makeText(context, "User " + userObject.getEmail() + " registered.",
                        Toast.LENGTH_SHORT).show();
                MainActivity.setUsername(userObject.getEmail());
            } else {
                Toast.makeText(context, "There was a problem while adding user " +
                                userObject.getName() + " " + userObject.getSurname(),
                        Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private User getUserObject(String user){
        Scanner s = new Scanner(user);
        s.useDelimiter(":");
        User newUser = null;
        while(s.hasNext()){
            newUser = new User(s.next(),s.next(),s.next(),s.next(),s.next(),s.next());
        }
        return newUser;
    }



}
