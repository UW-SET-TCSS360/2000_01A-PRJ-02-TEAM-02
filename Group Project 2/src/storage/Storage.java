package storage;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Stores weather info up to 26 months
 * Allows efficient reads and writes for specified weather via hasmap and enums
 * 
 * @author Geoffrey Woulf
 */
public class Storage {

	private HashMap<WeatherType, Log> weather;
		
	public Storage() {
		
		weather = new HashMap<WeatherType, Log>();
		
		for(WeatherType type : WeatherType.values()) {
			
			weather.put(type, new LinkedLog());
			
		}
		
	}
	
	/**
	 * Add a reading to the appropriate log
	 * 
	 * @param type Enum specifying the kind of measurement
	 * @param measurement The value of the measurement
	 */
	public void add(WeatherType type, Window theWindow, double measurement) {
		
		weather.get(type).addAtTime(theWindow, measurement);
		
	}
	
	/**
	 * Retrieve appropriate weather log for graphing purposes
	 * 
	 * @param type The type of measure to look up
	 * @param window The period of time we are interested in
	 * @param high Boolean flag- period high = true, period low = false
	 * @return The specified record of measures, or null if not found.
	 */
	public LinkedList<Double> getHistory(WeatherType type, Window window, boolean high) {
		
		return weather.get(type).getLog(window, high);
		
	}
	
	/**
	 * Returns the values for all WeatherTypes at a specific window of time.
	 * Calling getAllAtTime(WeatherType.CURRENT, 0) gets the most recent data.
	 * Calling getAllAtTime(WeatherType.DAY, 1) gets yesterdays set of data.
	 * @param The window corresponding to the timescale the request is being made at.
	 * @param The number of time units that reflects the most current window.
	 * @return A map that associates each measurement with its respective weather type.
	 */
	public HashMap<WeatherType, Double> getAllAtTime(Window theWindow, int displacement){
		HashMap<WeatherType, Double> toReturn =  new HashMap<>();
		for(WeatherType t : WeatherType.values()) {
			LinkedList<Double> data = weather.get(t).getLog(theWindow, false);
			toReturn.put(t, data.getLast()); //Get the desired element of the current time window, in T-1.
		}
		return toReturn;
		
	}
			
}
