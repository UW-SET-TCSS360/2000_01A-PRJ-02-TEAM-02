package storage;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Stores weather info up to 25 months, with incremental measurements of the first 25 days, and 25 hours.
 * 
 * @author Rory Fisher
 * @author Geoffrey Woulf
 */
public class Storage {

	private HashMap<WeatherType, LinkedLog> weather;
		
	/**
	 * 
	 */
	public Storage() {
		
		weather = new HashMap<WeatherType, LinkedLog>();
		
		for(WeatherType type : WeatherType.values()) {
			
			weather.put(type, new LinkedLog());
			
		}
		
	}
	
	/**
	 * 
	 */
	public Storage(HashMap<WeatherType, Double[]> theHours, HashMap<WeatherType, Double[]> theDays, HashMap<WeatherType, Double[]> theMonths) {
		
		weather = new HashMap<WeatherType, LinkedLog>();
		
		for(WeatherType type : WeatherType.values()) {
			
			weather.put(type, new LinkedLog(theHours.get(type), theDays.get(type), theMonths.get(type)));
			
		}
		
	}
	
	/**
	 * 
	 */
	public Storage(HashMap<WeatherType, Double[]> theHours, HashMap<WeatherType, Double[]> theHourLows,
			HashMap<WeatherType, Double[]> theDays, HashMap<WeatherType, Double[]> theDayLows,
			HashMap<WeatherType, Double[]> theMonths, HashMap<WeatherType, Double[]> theMonthLows) {
		
		weather = new HashMap<WeatherType, LinkedLog>();
		
		for(WeatherType type : WeatherType.values()) {
			
			weather.put(type, new LinkedLog(theHours.get(type), theHourLows.get(type), theDays.get(type), theDayLows.get(type), theMonths.get(type), theMonthLows.get(type)));
			
		}
		
	}

	/**
	 * Allows the bulk adding to all logs whose weather types are included in an input map.
	 * @param theWindow The time window being targete.d
	 * @param theMeasurements The map of measurements to be added, may support any proportion of the basic weather types as is listed in the weather types enumerator.
	 */
	public void addBulk(Window theWindow, HashMap<WeatherType, Double> theMeasurements) {
		for(WeatherType type : theMeasurements.keySet()) {
			
			weather.get(type).addAtTime(theWindow, theMeasurements.get(type));
			
		}
	}
	
	/**
	 * Allows the bulk adding to all logs whose weather types are included in an input map.
	 * @param theWindow The time window being targete.d
	 * @param theHighs The map of high measurements to be added, may support any proportion of the basic weather types as is listed in the weather types enumerator.
	 * @param theLows The map of low measurements to be added, may support any proportion of the basic weather types, but must at least support all weather types listed in the high measurements map.
	 */
	public void addBulk(Window theWindow, HashMap<WeatherType, Double> theHighs, HashMap<WeatherType, Double> theLows) {
		for(WeatherType type : theHighs.keySet()) {
			
			weather.get(type).addAtTime(theWindow, theHighs.get(type), theLows.get(type));
			
		}
	}
	
	/**
	 * Add a reading to the appropriate log
	 * 
	 * 	
	 * @param theType A value specifying the kind of measurement.
	 * @param theWindow  The time window this data is targeting.
	 * @param theMeasurement The value of the measurement.
	 */
	public void add(WeatherType theType, Window theWindow, double theMeasurement) {
		
		weather.get(theType).addAtTime(theWindow, theMeasurement);
		
	}
	
	/**
	 * Add a reading to the appropriate log
	 * 
	 * @param theType A value specifying the kind of measurement.
	 * @param theWindow  The time window this data is targeting.
	 * @param theHigh The high value for this time window.
	 * @param theLow The low value for this time window.
	 */
	public void add(WeatherType theType, Window theWindow, double theHigh, double theLow) {
		
		weather.get(theType).addAtTime(theWindow, theHigh, theLow);
		
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
	 * Gets the length of all logs such that the bounds of each log may be properly respected.
	 * Once the logs reach 25 elements, it may become unnecessary to continue calling this method, as 
	 * once the logs reach 25 elements, they will consistently be 25 elements long.
	 * 
	 * @param theWindow theWindow of time whose bounds we are checking.
	 * @return A map containing the lengths of each log. In most cases, all logs will be of equal length.
	 */
	public HashMap<WeatherType, Integer> getLogLengths(Window theWindow){
		HashMap<WeatherType, Integer> toReturn = new HashMap<>();
		for (WeatherType t : WeatherType.values()) {
			toReturn.put(t, weather.get(t).getLength(theWindow));
		}
		return toReturn;
		
	}
	
	/**
	 * Returns the values for all WeatherTypes at a specific window of time.
	 * Calling getAllAtTime(WeatherType.CURRENT, 0) gets the most recent data.
	 * Calling getAllAtTime(WeatherType.DAY, 1) gets yesterdays set of data.
	 * @param The window corresponding to the timescale the request is being made at.
	 * @param The number of time units that reflects the most current window.
	 * @return A map that associates each measurement with its respective weather type.
	 */
	public HashMap<WeatherType, Double> getAllAtTime(Window theWindow, int theOffset, boolean theHigh){
		HashMap<WeatherType, Double> toReturn =  new HashMap<>();
		for(WeatherType t : WeatherType.values()) {
			toReturn.put(t, weather.get(t).getAtTime(theWindow, theOffset, theHigh));
		}
		return toReturn;
		
	}
	
}
