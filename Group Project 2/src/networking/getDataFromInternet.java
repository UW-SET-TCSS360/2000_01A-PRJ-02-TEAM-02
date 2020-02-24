package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject; 

/**
 * 
 */

/**
 * @author micha
 *
 */
public class getDataFromInternet {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=94040,us&appid=594e9de078a3ccde7630bc9017a457ff");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responsecode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		if(responsecode != 200) {
			throw new RuntimeException("HttpResonseCode:" + responsecode);
		} else {
			BufferedReader in = new BufferedReader(
		             new InputStreamReader(con.getInputStream()));
			String inputLine;
		     StringBuffer response = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		     }
		     in.close();
		   //print in String
		     System.out.println(response.toString());
		     //Read JSON response and print
		     JSONObject myResponse = new JSONObject(response.toString());
		     System.out.println("result after Reading JSON Response");
		     System.out.println("statusCode- "+myResponse.toString());

		}
	}

}
