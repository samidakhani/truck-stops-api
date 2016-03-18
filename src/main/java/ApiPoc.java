import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * Created by sdakhani on 3/17/16.
 */
public class ApiPoc {

    public static void main(String[] args){

        try {

            URL url = new URL("http://api.mygasfeed.com/stations/radius/33.624631/-117.879147/0.5/diesel/distance/8w8dgi79x1.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String response="";
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                response +=output;
            }

            JSONObject jsonObj = new JSONObject(response);
            JSONArray stations = jsonObj.getJSONArray("stations");
            JSONObject actulStation = stations.getJSONObject(0);
            String stationName = actulStation.getString("station");
            String station = actulStation.getString("station");
            String diesel_price = actulStation.getString("diesel_price");
            String last_updated = actulStation.getString("reg_date");

            JSONObject obj = new JSONObject();
            obj.put("station", station);
            obj.put("diesel_price", diesel_price);
            obj.put("last_updated", last_updated);

            System.out.print(obj);

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e){

        }

    }

}
