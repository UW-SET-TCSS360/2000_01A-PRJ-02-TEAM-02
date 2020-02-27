/**
 * 
 */
package networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * @author pham19@uw.edu, michaelphamn@gmail.com
 *
 */
public class OpenWeatherMap {
	private String apiBase;
	private String apiForecast;
	private String apiKey;
	private String units;
	private String lang;

	
	/**
	 * Initial parameters for Open Weather Map API.
	 * apiBase: weather information at current day;
	 * apiForecase: weather information in the last 6 days;
	 * units: the units. For example, Fahrenheit or Cencius. 
	 * lang: language.
	 * apiKey: 594e9de078a3ccde7630bc9017a457ff.
	 */
	public OpenWeatherMap() {
		apiBase = "http://api.openweathermap.org/data/2.5/weather?zip=";
		apiForecast = "http://api.openweathermap.org/data/2.5/forecast?zip=";
		apiKey = "594e9de078a3ccde7630bc9017a457ff";
		units = "imperial";
		lang = "en";
	}
	
	/**
	 * get the base url that is associated with the open weather map api.
	 * @return the base url
	 */
	public String getApiBase() {
		return apiBase;
	}

	/**
	 * get the forecast url that is associated with the open weather map api.
	 * @return the forecast url
	 */
	public String getApiForecast() {
		return apiForecast;
	}

	/**
	 * @param apiBase
	 * set the base url that is associated with the open weather map api.
	 */
	public void setApiBase(String apiBase) {
		this.apiBase = apiBase;
	}

	/**
	 * @param apiBase
	 * set the forecast url that is associated with the open weather map api.
	 */
	public void setApiForecast(String apiForecast) {
		this.apiForecast = apiForecast;
	}

	/**
	 * get the unit, can be either imperial or metric.
	 * @return imperial or metric.
	 */
	public String getUnits() {
		return units;
	}

	/**
	 * @param units imperial or metric.
	 * set the units, can be either imperial or metric.
	 */
	public void setUnits(String units) {
		this.units = units;
	}

	/**
	 * get the API key is associated with the open weather map api.
	 * @return the api key
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 * REQUIRED: specify your API key with this method.
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * Set the language
	 * @param lang en
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Return a sentence describing the weather
	 * @throws IOException 
	 */
	public JSONObject fetch(final String location) throws IOException, JSONException {
		String apiUrl = apiBase + location + "&appid=" + apiKey + "&mode=json&units=" + units + "&lang=" + lang;		
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();		
		JSONObject obj = new JSONObject(response.toString());
		return obj;
	}


	/**
	 * retrieve a string list of the current weather (for example:)
	 * 
	 *{"coord":{"lon":2.35,"lat":48.86},
	 *"weather":[{"id":701,"main":"Mist","description":"mist","icon":"50n"}],
	 *"base":"stations",
	 *"main":{"temp":10.37,"pressure":1037,"humidity":93,"temp_min":10,"temp_max":11},
	 *"visibility":6000,
	 *"wind":{"speed":2.1,"deg":270},
	 *"clouds":{"all":90},
	 *"dt":1513884600,
	 *"sys":{"type":1,"id":5610,"message":0.0029,"country":"FR","sunrise":1513842107,"sunset":1513871796},
	 *"id":2988507,"name":"Paris","cod":200}
	 */

	/**
	 * Return a sentence describing the forecast weather
	 */
	public JSONObject fetch(String location, int nbDay) throws IOException, JSONException {
		String apiUrl = apiForecast + location + "&appid=" + apiKey + "&mode=json&units=" + units + "&lang=" + lang + "&cnt=" + nbDay;
		URL url = new URL(apiUrl);		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();		
		JSONObject obj = new JSONObject(response.toString());
		return obj;
	}

//	/**
//	       /**    
//	 * retrieve a string list of weather for the next (max) 5 day  (40 periods of 3 hours) indicated by dayIndex 0 <=
//	 * dayIndex <= 40 (Note: 0 & 40 give the forecast for all the periods). (for example PeriodIndex=3 :)
//	 * 
//	 * {"city":{"id":2802985,"name":"location","coord":{"lon":5.8581,"lat":50.7019},"country":"FR","population":0},
//	 * "cod":"200", "message":0.1309272, 
//	 * "cnt":3, 
//	 * "list":
//	 *   
//	 * [{"dt":1505386800,
//	 * "temp":{"day":11.62,"min":10.59,"max":12.39,"night":11.01,"eve":10.59,"morn":10.98},
//	 * "pressure":1006.58, "humidity":100,
//	 * "weather":[{"id":502,"main":"Rain","description":"heavy intensity
//	 * rain","icon":"10d"}], "speed":6.73, "deg":259, "clouds":92, "rain":17.33},   *
//	 * 
//	 * {"dt":1505473200,
//	 * "temp":{"day":14.96,"min":8.43,"max":14.96,"night":8.43,"eve":12.71,"morn":9.46},
//	 * "pressure":1014.87, "humidity":89,
//	 * "weather":[{"id":500,"main":"Rain","description":"light
//	 * rain","icon":"10d"}], "speed":4.8, "deg":249, "clouds":20, "rain":0.36},
//	 * 
//	 * {"dt":1505559600,
//	 * "temp":{"day":13.85,"min":8.09,"max":14.5,"night":8.44,"eve":12.5,"morn":8.09},
//	 * "pressure":1013.37, "humidity":95,
//	 * "weather":[{"id":501,"main":"Rain","description":"moderate
//	 * rain","icon":"10d"}], "speed":5.38, "deg":241, "clouds":44, "rain":5.55} ]}
//	 *
//	 * @throws JSONException 
//	 * @throws IOException 
//	 * @throws ClientProtocolException 
//	 */ 
//
//
//	public String[] fetchCurrentWeather(String location) throws IOException, JSONException {
//		String[] result = new String[11];
//		result[0] = "error";
//		try {
//			JSONObject obj = fetch(location);
//			result[0] = obj.getJSONArray("weather").getJSONObject(0).get("description").toString();
//			result[1] = obj.getJSONObject("main").get("temp").toString();
//			result[2] = location;
//			result[3] = obj.getJSONArray("weather").getJSONObject(0).get("id").toString();
//			result[4] = obj.getJSONObject("main").get("pressure").toString();
//			result[5] = obj.getJSONObject("main").get("humidity").toString();
//			result[6] = obj.getJSONObject("main").get("temp_min").toString();
//			result[7] = obj.getJSONObject("main").get("temp_max").toString();
//			result[8] = obj.getJSONObject("wind").get("speed").toString();
//			result[9] = obj.getJSONObject("wind").get("deg").toString();
//			result[10] = obj.getJSONObject("clouds").get("all").toString();			
//			return result;
//		} catch (Exception e) {
//			return result;
//		}
//	}
//
//	public String[] fetchForecast(String location, int dayIndex) throws IOException, JSONException {
//		String[] result = new String[11];
//		String localUnits = "fahrenheit";
//		if ((dayIndex >= 1) && (dayIndex <= 6)) {
//			JSONObject jsonObj = null;
//			try {
//				jsonObj = fetch(location, (dayIndex));				
//			} catch (IOException | JSONException e) {
//				return null;
//			}
//			
//			// Getting the list node
//			JSONArray list;
//			try {
//				list = jsonObj.getJSONArray("list");
//			} catch (JSONException e) {
//				return null;
//			}
//			// Getting the required element from list by dayIndex
//			JSONObject item = list.getJSONObject(dayIndex - 1);
//			result[0] = item.getJSONArray("weather").getJSONObject(0).get("description").toString();
//			JSONObject main = item.getJSONObject("main");
//			result[1] = main.get("temp").toString();
//			result[2] = location;
//			result[3] = item.getJSONArray("weather").getJSONObject(0).get("id").toString();
//			result[4] = main.get("pressure").toString();
//			result[5] = main.get("humidity").toString();
//			result[6] = main.get("temp_min").toString();
//			result[7] = main.get("temp_max").toString();
//			JSONObject wind = item.getJSONObject("wind");
//			result[8] = wind.get("speed").toString();
//			result[9] = wind.get("deg").toString();
//			result[10] = localUnits;
//		} else {
//			return null;
//		}
//		return result;
//	}
//
//	
//
//	public static void main(String[] args) {
//		OpenWeatherMap owm = new OpenWeatherMap();
//		try {
//			String[] fetchForecast = owm.fetchForecast("94040,us", 6);			
////			String sentence = "("+fetchForecast[3]+") In " + fetchForecast[2] + " the weather is " + fetchForecast[0] + ".  " + fetchForecast[1] + " degrees " + fetchForecast[10];
////			System.out.println(sentence);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
