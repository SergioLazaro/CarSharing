package AsyncTasks;

/**
 * Created by Sergio on 16/2/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.conpartirandroid.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class LoginTask  extends AsyncTask<String,Void,String> {
    private Context context;

    private String email, password;
    private static final String ip = ""; //Put your ip here

    public LoginTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        email = (String)arg0[0];
        password = (String) arg0[1];

        try {
            String link = "http://" + ip + ":8080/CarSharing-war/LoginServlet?version=android";
            String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            //data += "&" + URLEncoder.encode("version","UTF-8") + "=" + URLEncoder.encode("android","UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
        Log.i("RESULT",line);
        try {
            JSONObject json = new JSONObject(line);
            boolean result = Boolean.parseBoolean(json.getString("result"));
            Log.i("VALUE","" + result);
            if(!result){
                Toast.makeText(context, "User " + email + " does not exist", Toast.LENGTH_SHORT).show();
            }
            else{
                MainActivity.setUsername(email);
                Toast.makeText(context, "Welcome " + email, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
