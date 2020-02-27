package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject; 
import simulator.Simulator;
import storage.Storage;
import storage.WeatherType;
/**
 * @author pham19@uw.edu
 * This class is a basic network module that requests the API on a regular interval or a specific time. 
 * and saves the current API data to a map of weather types.
 * We can use any free weather API such as Google API or OpenWeatherMap API, but we mainly use OpenWeatherMap API
 * This API is free but limited in 5 days interval. 
 *
 */
public class _Network {
	Storage myStorage;
	
	//the address of the weather api
	private String apiAddress;
	
	//the zip code
	private String zip;
	
	//API ID 
	private String apiKey;
	
	//The interval
	private Date from;
	private Date to;

	/**
	 * The constructor of requestAPI
	 * @param url the address of the weather api. For example: http://api.openweathermap.org/data/2.5/weather
	 * @param zip the zip code. For example: 94040,us
	 * @param apiKey API Key. For example: 594e9de078a3ccde7630bc9017a457ff. Access to this link to get API key https://openweathermap.org/price
	 * @param from the interval
	 * @param to
	 */
	public _Network(String apiAddress, String zip, String apiKey) {
		super();
		this.apiAddress = apiAddress;
		this.zip = zip;
		this.apiKey = apiKey;
	}

	public String getAPIAddress() {
		return apiAddress;
	}

	public void setAPIAddress(String apiAddress) {
		this.apiAddress = apiAddress;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAPIKEY() {
		return apiKey;
	}

	public void setAPIKEY(String apiKey) {
		this.apiKey = apiKey;
	}	

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}
	
	/**
	 * Retrieves the most up to date values and sends them to the storage system.
	 */
	public Storage sendToStorage() {
		Storage storage = new Storage();
		URL url = new URL("http://" + apiAddress + "?zip=" + zip + "&appid=" + apiKey);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responsecode = con.getResponseCode();
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
		}
		return 
	}

//	/**
//	 * @param args
//	 * @throws IOException 
//	 * @throws JSONException 
//	 */
//	public static void main(String[] args) throws IOException, JSONException {
//		// TODO Auto-generated method stub
//		URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=94040,us&appid=594e9de078a3ccde7630bc9017a457ff");
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		con.setRequestMethod("GET");
//		con.setRequestProperty("User-Agent", "Mozilla/5.0");
//		int responsecode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		if(responsecode != 200) {
//			throw new RuntimeException("HttpResonseCode:" + responsecode);
//		} else {
//			BufferedReader in = new BufferedReader(
//		             new InputStreamReader(con.getInputStream()));
//			String inputLine;
//		     StringBuffer response = new StringBuffer();
//		     while ((inputLine = in.readLine()) != null) {
//		     	response.append(inputLine);
//		     }
//		     in.close();
//		   //print in String
//		     System.out.println(response.toString());
//		     //Read JSON response and print
//		     JSONObject myResponse = new JSONObject(response.toString());
//		     System.out.println("result after Reading JSON Response");
//		     System.out.println("statusCode- "+myResponse.toString());
//
//		}
//	}

}
