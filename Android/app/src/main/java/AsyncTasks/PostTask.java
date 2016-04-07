package AsyncTasks;

/**
 * Created by Sergio on 16/2/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sergio.conpartirandroid.List;
import com.example.sergio.conpartirandroid.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Logger;


public class PostTask  extends AsyncTask<String,Void,String> {
    private Context context;

    private String postType;
    private static final String ip = ""; //Put your ip here

    public PostTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        postType = (String)arg0[0];

        try {
            String link = "http://" + ip + ":8080/CarSharing-war/PostServlet?version=android";
            String data = "";
            //Check postType to build the URL petition
            if(postType.equals("Car")){
                data = URLEncoder.encode("transport", "UTF-8") + "=" + URLEncoder.encode(postType, "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode((String) arg0[1], "UTF-8");
                data += "&" + URLEncoder.encode("departure", "UTF-8") + "=" + URLEncoder.encode((String) arg0[2], "UTF-8");
                data += "&" + URLEncoder.encode("destination", "UTF-8") + "=" + URLEncoder.encode((String) arg0[3], "UTF-8");
                data += "&" + URLEncoder.encode("cartype", "UTF-8") + "=" + URLEncoder.encode((String) arg0[4], "UTF-8");
                data += "&" + URLEncoder.encode("caryear", "UTF-8") + "=" + URLEncoder.encode((String) arg0[5], "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode((String) arg0[6], "UTF-8");
            }
            else{
                data = URLEncoder.encode("transport", "UTF-8") + "=" + URLEncoder.encode(postType, "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode((String) arg0[1], "UTF-8");
                data += "&" + URLEncoder.encode("departure", "UTF-8") + "=" + URLEncoder.encode((String) arg0[2], "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode((String) arg0[3], "UTF-8");
            }
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
        //line is a JSON Array
        Log.i("RESULT POST", line);
        try {
            JSONObject json = new JSONObject(line);
            boolean result = Boolean.parseBoolean(json.getString("result"));
            Log.i("VALUE POST","" + result);
            if(!result){
                Toast.makeText(context,"New post cannot be added",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"New post added successfully",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
