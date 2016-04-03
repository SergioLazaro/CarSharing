package AsyncTasks;

/**
 * Created by Sergio on 16/2/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Objects.Publication;


public class ListTask  extends AsyncTask<String,Void,String> {
    private Context context;

    private String listType;
    private static final String ip = "192.168.1.82";

    public ListTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        listType = (String)arg0[0];

        try {
            String link = "http://" + ip + ":8080/CarSharing-war/ListPostServlet";
            String data  = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(listType, "UTF-8");

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
        try {
            JSONObject json = new JSONObject(line);
            JSONArray jsonArray = json.getJSONArray("posts");
            ArrayList<Publication> array = new ArrayList<Publication>();
            ArrayList<HashMap<String,String>> infoToShow = new ArrayList<HashMap<String,String>>();
            //ArrayList<String> data = new ArrayList<String>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject elem = jsonArray.getJSONObject(i);
                Publication p = generatePublication(elem);
                array.add(p);
                //Populating info to show
                HashMap<String, String> din = new HashMap<String, String>(2);
                din.put("User",p.getUsername());
                din.put("DateFromTo", p.getFrom() + " " + p.getDate());
                infoToShow.add(din);
            }
            SimpleAdapter arrayAdapter = new SimpleAdapter(context, infoToShow, android.R.layout.two_line_list_item ,
                    new String[] { "User","DateFromTo" },
                    new int[] {android.R.id.text1, android.R.id.text2});
            List.setAdapter(arrayAdapter,array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Publication generatePublication(JSONObject elem){
        try {
            if(listType.equals("Car")){
                return new Publication(elem.get("date").toString(),elem.get("departure").toString(),
                        elem.get("destination").toString(), elem.get("carType").toString(),
                        elem.get("carYear").toString(), elem.get("username").toString());
            }
            else{
                return new Publication(elem.get("date").toString(),elem.get("departure").toString(),
                        elem.get("username").toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}
