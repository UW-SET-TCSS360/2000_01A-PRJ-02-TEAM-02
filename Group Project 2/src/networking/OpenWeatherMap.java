/**
 * 
 */
package networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import storage.WeatherType;
import storage.Window;

/**
 * Getting data from Open Weather Map. It is a paid application. 
 * To get all functions like hourly, daily or monthly forecast, 
 * we have to pay $180 / month. Using this module, we only have
 * some data for free. For example, we can get 5 days data and the current weather from the
 * Open Weeather Map. I use these data and simulate new data.

 * 
 * @author pham19@uw.edu, michaelphamn@gmail.com
 * 
 *
 */
public class OpenWeatherMap {
	/**
	 * Initial parameters for Open Weather Map API.
	 * apiBase: weather information at current day;
	 * apiForecase: weather information in the last 6 days;
	 * units: the units. For example, Fahrenheit or Cencius. 
	 * lang: language.
	 * apiKey: 594e9de078a3ccde7630bc9017a457ff.
	 * @throws JSONException 
	 * @throws IOException 
	 */
	private String apiBase = "http://api.openweathermap.org/data/2.5/weather?zip=";
	private String apiForecast = "http://api.openweathermap.org/data/2.5/forecast?zip=";
	private String apiKey = "594e9de078a3ccde7630bc9017a457ff";
	private String units = "imperial";
	private String lang = "en";
	
	/**
	 * The currently saved weather values.
	 */
	private HashMap<WeatherType, Double> myCurrent;
	
	/**
	 * The currently saved weather values.
	 */
	private HashMap<WeatherType, Double> current;

	/**
	 * The assumed minimum reasonable value for any given weather type.
	 */
	private Map<WeatherType, Double> myBase;

	/**
	 * The range of possible weather values (How much higher the highest value can
	 * be then the values base.
	 */
	private Map<WeatherType, Double> myRange;

	/**
	 * Provides exponents to control the flow of the simulator.
	 */


	public OpenWeatherMap() {		
		String location = "98402,us";			
		JSONObject obj;
		String description;		
		try {
			obj = fetch(location);
						
			// Initialize the base values for the updating weather types.
			myBase = new HashMap<>();
			myBase.put(WeatherType.temp, 60.);
			myBase.put(WeatherType.outtemp, obj.getJSONObject("main").getDouble("temp"));
			myBase.put(WeatherType.humidity, 15.);
			myBase.put(WeatherType.outhumidity, 50.);
			myBase.put(WeatherType.rainRate, 0.1);
			/**
			 * Open Weather Map doesn't contain rain, simulating the rain base on the description of current weather  
			 */
			 description = obj.getJSONArray("weather").getJSONObject(0).get("description").toString();
			/**
			 * description of current weather may clear sky, few clouds, scattered clouds, broken clouds, shower rain, rain,
			 * thunderstorm, snow, or mist
			 */			
			double ran = Math.random();
			int MaxShowerRain = 16;
			int MinShowerRain = 4;
			
			int MaxRain = 4;
			int MinRain = 0;
			
			int MaxThunderstom = 100;
			int MinThunderstom = 20;
			
			double rain = 0.0;
			switch(description) {
				case "shower rain":
					rain = MaxShowerRain + ran * MinShowerRain;
					break;
				case "rain":
					rain = MinRain + ran * MaxRain;
					break;
				case "thunderstorm":
					rain = MaxThunderstom + ran * MinThunderstom;;
					break;
			}
			myBase.put(WeatherType.rain, rain);
			myBase.put(WeatherType.windchill, 10.);
			myBase.put(WeatherType.wind, obj.getJSONObject("wind").getDouble("speed"));
			myBase.put(WeatherType.winddir, obj.getJSONObject("wind").getDouble("deg"));
			myBase.put(WeatherType.barometric, obj.getJSONObject("main").getDouble("pressure"));
			
			// Initializes the base values for the ranges.
			myRange = new HashMap<>();
			myRange.put(WeatherType.temp, 2.);
			myRange.put(WeatherType.outtemp, 9.);
			myRange.put(WeatherType.humidity, 8.);
			myRange.put(WeatherType.outhumidity, 5.);
			myRange.put(WeatherType.rainRate, 00.6);
			myRange.put(WeatherType.rain, 0.42);
			myRange.put(WeatherType.windchill, 3.);
			myRange.put(WeatherType.wind, 1.5);
			myRange.put(WeatherType.winddir, 36.);
			myRange.put(WeatherType.barometric, 0.006);
			
			
			//generate the initial current values.
			myCurrent = new HashMap<WeatherType, Double>();		
			
			current = new HashMap<WeatherType, Double>();	
			/**
			 * Simulate indoor temperature
			 */
			myCurrent.put(WeatherType.temp, myBase.get(WeatherType.temp) + ran * myRange.get(WeatherType.temp));
			
			/**
			 * Outdoor temperature from Open Weather
			 */
			myCurrent.put(WeatherType.outtemp, myBase.get(WeatherType.outtemp) + ran * myRange.get(WeatherType.outtemp));
			
			/**
			 * Simulate indoor humidity
			 */
			myCurrent.put(WeatherType.humidity,myBase.get(WeatherType.humidity) + ran * myRange.get(WeatherType.humidity));
			
			/**
			 * Outdoor humidity from Open Weather
			 */
			myCurrent.put(WeatherType.outhumidity, obj.getJSONObject("main").getDouble("humidity"));
			
			/**
			 * Simulate rain rate
			 */
			myCurrent.put(WeatherType.rainRate, myBase.get(WeatherType.rainRate) + ran * myRange.get(WeatherType.rainRate));
			
			/**
			 * Simulate rain
			 */
			myCurrent.put(WeatherType.rain, myBase.get(WeatherType.rain) + ran * myRange.get(WeatherType.rain));
			
			/**
			 * Simulate wind chill
			 */
			myCurrent.put(WeatherType.windchill,myBase.get(WeatherType.windchill) + ran * myRange.get(WeatherType.windchill));
			
			/**
			 * wind speed, wind direction, and pressure from Open Weather
			 */
			myCurrent.put(WeatherType.wind, myBase.get(WeatherType.wind) + ran * myRange.get(WeatherType.wind));
			myCurrent.put(WeatherType.winddir, myBase.get(WeatherType.winddir) + ran * myRange.get(WeatherType.winddir));
			myCurrent.put(WeatherType.barometric,myBase.get(WeatherType.barometric) + ran * myRange.get(WeatherType.barometric));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
	 * retrieve a string list of the current weather (for example:) at a specific zipcode 
	 * {"coord":{"lon":2.35,"lat":48.86},
	 *	"weather":[{"id":701,"main":"Mist","description":"mist","icon":"50n"}],
	 *	"base":"stations",
	 *	"main":{"temp":10.37,"pressure":1037,"humidity":93,"temp_min":10,"temp_max":11},
	 *	"visibility":6000,
	 *	"wind":{"speed":2.1,"deg":270},
	 *	"clouds":{"all":90},
	 *	"dt":1513884600,
	 *	"sys":{"type":1,"id":5610,"message":0.0029,"country":"FR","sunrise":1513842107,"sunset":1513871796},
	 *	"id":2988507,"name":"Paris","cod":200
	 * }
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
	
	public void updateCurrent() {
		Double ran = Math.random();
		for (WeatherType t : myCurrent.keySet()) {
			// Make the new value, which is going to be 95% the current value, and 5% some
			// random value in
			// the same range.
			double currentValue = 0;
			if (t == WeatherType.rain) {
				currentValue = myCurrent.get(t) + myCurrent.get(WeatherType.rainRate) * 1.0 / 1440.0;
				// Our storage is built for the weather requests to be made every 2.5 seconds.
			} else if (t == WeatherType.rainRate) {
				if(ran > 0.5) {
					currentValue = myCurrent.get(t) + ran * myRange.get(t); // Rain speed follows less logical rules
					// then other measures.
				} else {
					currentValue = myCurrent.get(t) - ran * myRange.get(t); // Rain speed follows less logical rules
					// then other measures.
				}
				
			} else if (t == WeatherType.windchill) {
				// Use commonly available wind chill formula if the temperature is below 50
				// degrees.
				// https://web.archive.org/web/20110918010232/http://www.weather.gov/os/windchill/index.shtml
				// Thank you national weather service.
				if (myCurrent.get(WeatherType.outtemp) <= 50.0) {
					currentValue = 35.74 + myCurrent.get(WeatherType.outtemp) * 0.6215
							- 35.75 * Math.pow(myCurrent.get(WeatherType.wind), 0.16)
							+ 0.4275 * myCurrent.get(WeatherType.outtemp)
									* Math.pow(myCurrent.get(WeatherType.wind), 0.16);
					// What an ugly formula.
				} else
					currentValue = myCurrent.get(WeatherType.outtemp);
				// Else assume percieved temperature is the current temperature.

			} else {
				if(ran > 0.5) {
					currentValue = myCurrent.get(t) + ran * myRange.get(t);
				} else {
					currentValue = myCurrent.get(t) - ran * myRange.get(t);
				}
			}
			// Update the current value if the current value isn't related to rain.
			// If the current value IS rain, update it if the current humidity is high
			// enough for it to rain.
			if (t != WeatherType.rain || myCurrent.get(WeatherType.outhumidity) > 75)
				myCurrent.put(t, currentValue);
		}
	}
	
	public HashMap<WeatherType, Double> getCurrent() {
		HashMap<WeatherType, Double> toReturn = new HashMap<>();
		toReturn.putAll(myCurrent);
		return toReturn;
	}
}
