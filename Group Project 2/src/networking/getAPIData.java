/**
 * 
 */
package networking;

import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import storage.Storage;
import storage.WeatherType;
import storage.Window;

/**
 * @author pham19@uw.edu
 * This class is the same Simulator class of Rory Fisher's project
 */
public class getAPIData {
	/**
	 * the address of the weather api
	 */
	private String apiAddress;
	
	/**
	 * the zip code
	 */
	private String zip;
	
	/**
	 * API ID 
	 */
	private String apiKey;
	
	/**
	 * The interval
	 */
	private Date from;
	private Date to;

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
	
	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	/**
	 * The currently saved weather values.
	 */
	private HashMap<WeatherType, Double> myCurrent;

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
	private Map<Window, Double> timeExponents;

	/**
	 * Generates a new simulator instance.
	 */
	public getAPIData(String apiAddress, String zip, String apiKey) {
		this.apiAddress = apiAddress;
		this.zip = zip;
		this.apiKey = apiKey;
		
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
		// Initialize the time exponents: The larger the units, the less there should be
		// in common with
		// the previous measurement. This won't provide logical values for each month,
		// but it will provide
		// somewhat believable flows of values over a small window of time.
		timeExponents = new HashMap<>();
		timeExponents.put(Window.current, 0.94);
		timeExponents.put(Window.hours, 0.85);
		timeExponents.put(Window.days, 0.65);
		timeExponents.put(Window.months, 0.38);

		// Initialize the base values for the updating weather types.
		myBase = new HashMap<>();
		myBase.put(WeatherType.temp, 60.);
		myBase.put(WeatherType.outtemp, 15.);
		myBase.put(WeatherType.humidity, 15.);
		myBase.put(WeatherType.outhumidity, 50.);
		myBase.put(WeatherType.rainRate, 0.1);
		myBase.put(WeatherType.rain, 0.);
		myBase.put(WeatherType.windchill, 10.);
		myBase.put(WeatherType.wind, 0.);
		myBase.put(WeatherType.winddir, 0.);
		myBase.put(WeatherType.barometric, 0.97);

		// Initializes the base values for the ranges.
		myRange = new HashMap<>();
		myRange.put(WeatherType.temp, 20.);
		myRange.put(WeatherType.outtemp, 90.);
		myRange.put(WeatherType.humidity, 80.);
		myRange.put(WeatherType.outhumidity, 50.);
		myRange.put(WeatherType.rainRate, 0.6);
		myRange.put(WeatherType.rain, 4.2);
		myRange.put(WeatherType.windchill, 30.);
		myRange.put(WeatherType.wind, 15.);
		myRange.put(WeatherType.winddir, 360.);
		myRange.put(WeatherType.barometric, 0.06);

		// Randomly generate the initial current values.
		myCurrent = new HashMap<>();
		myCurrent.put(WeatherType.temp, myBase.get(WeatherType.temp) + Math.random() * myRange.get(WeatherType.temp));
		myCurrent.put(WeatherType.outtemp,
				myBase.get(WeatherType.outtemp) + Math.random() * myRange.get(WeatherType.outtemp));
		myCurrent.put(WeatherType.humidity,
				myBase.get(WeatherType.humidity) + Math.random() * myRange.get(WeatherType.humidity));
		myCurrent.put(WeatherType.outhumidity,
				myBase.get(WeatherType.outhumidity) + Math.random() * myRange.get(WeatherType.outhumidity));
		myCurrent.put(WeatherType.rainRate,
				myBase.get(WeatherType.rainRate) + Math.random() * myRange.get(WeatherType.rainRate));
		myCurrent.put(WeatherType.rain, 0.);
		myCurrent.put(WeatherType.windchill,
				myBase.get(WeatherType.windchill) + Math.random() * myRange.get(WeatherType.windchill));
		myCurrent.put(WeatherType.wind, myBase.get(WeatherType.wind) + Math.random() * myRange.get(WeatherType.wind));
		myCurrent.put(WeatherType.winddir,
				myBase.get(WeatherType.winddir) + Math.random() * myRange.get(WeatherType.winddir));
		myCurrent.put(WeatherType.barometric,
				myBase.get(WeatherType.barometric) + Math.random() * myRange.get(WeatherType.barometric));
	}

	/**
	 * Provides initial sets of 25 data entries for all weather types that are
	 * supported. Should only be run shortly after initialization, to create the
	 * simulated data for a large window of time, as this method generates large
	 * batches of data.
	 * 
	 * When hours is called after days, the first hour will be the last value from the previous day.
	 * There exists an exception to this: Hours graphs for rain will start at a minimum value.
	 * 
	 * When days is called after months, the value of the first day will be the last value from the previous month.
	 */
	public HashMap<WeatherType, Double[]> getInitialSets(Window theTimeWindow) {
		HashMap<WeatherType, Double[]> initialSet = new HashMap<>();
		// We have to generate new data for every key in the data set.
		// So Keys*25 data elements.
		for (WeatherType t : myCurrent.keySet()) {
			Double[] initialData = new Double[25];
			//Generate the new data set.
			//There is no rain at the start of a new day.
			if(t == WeatherType.rain && theTimeWindow == Window.hours) {
				myCurrent.put(t, 0.0);
			}
			initialData[0] = myCurrent.get(t);
			// The oldest data is the "Current Data."
			for (int i = 1; i < 25; i++) {
				//Inform the new data with the previous data.
				if (t != WeatherType.rain || theTimeWindow != Window.hours) myCurrent.put(t, myCurrent.get(t) * timeExponents.get(theTimeWindow)
						+ (1 - timeExponents.get(theTimeWindow)) * (myBase.get(t) + Math.random() * myRange.get(t)));
				else {
					//If it is rain over the hours, let it accumulate.
					myCurrent.put(t, myCurrent.get(t)+Math.random()*myRange.get(WeatherType.rain)/24);
					//Assume it rains ~ 15% of the time.
				}
				//Otherwise generate the weather data as normal.
				initialData[i] = myCurrent.get(t);
				// Generate a hopefully logical sequence of data for a seasonless simulator
				// planet.
				// Each value will be informed by the previous value.
			}
			initialSet.put(t, initialData);
		}
		return initialSet;
	}

	/**
	 * Recalculates the current value, taking into account the realistic ranges for
	 * a value, and the previous value. this has no comprehension of seasons.
	 */
	public void updateCurrent() {
		for (WeatherType t : myCurrent.keySet()) {

			// Make the new value, which is going to be 95% the current value, and 5% some
			// random value in
			// the same range.
			double currentValue = 0;
			if (t == WeatherType.rain) {
				currentValue = myCurrent.get(t) + myCurrent.get(WeatherType.rainRate) * 1.0 / 1440.0;
				// Our storage is built for the weather requests to be made every 2.5 seconds.
			} else if (t == WeatherType.rainRate) {
				currentValue = myBase.get(t) + Math.random() * myRange.get(t); // Rain speed follows less logical rules
																				// then other measures.
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
				currentValue = myCurrent.get(t) * timeExponents.get(Window.current)
						+ (1 - timeExponents.get(Window.current)) * (myBase.get(t) + Math.random() * myRange.get(t));
			}

			// Update the current value if the current value isn't related to rain.
			// If the current value IS rain, update it if the current humidity is high
			// enough for it to rain.
			if (t != WeatherType.rain || myCurrent.get(WeatherType.outhumidity) > 75)
				myCurrent.put(t, currentValue);
		}
	}

	/**
	 * On a new day, reset specific values to reflect a reality where we measure
	 * these values within the scope of that specific day, such as rain.
	 */
	public void newDay() {
		myCurrent.put(WeatherType.rain, 0.0);
	}

	/**
	 * Returns a copy of the current simulator values.
	 * 
	 * @return A HashMap which contains all current simulator values.
	 */
	public HashMap<WeatherType, Double> getCurrent() {
		HashMap<WeatherType, Double> toReturn = new HashMap<>();
		toReturn.putAll(myCurrent);
		return toReturn;
	}

}
