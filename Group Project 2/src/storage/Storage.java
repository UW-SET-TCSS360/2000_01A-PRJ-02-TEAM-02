/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package storage;

import java.util.HashMap;

/**
 * Stores weather info up to 26 months Allows efficient reads and writes for
 * specified weather via hasmap and enums
 * 
 * @author Geoffrey Woulf
 */
public class Storage {

	/**
	 * The map of the weather data logs.
	 * These logs further divide the data into various timeframes, as is detailed by the window 
	 * enum.
	 */
	private HashMap<WeatherType, Log> weather;

	/**
	 * Initializes a blank instance of the storage class, with initialized logs for each
	 * of the weather types supported.
	 */
	public Storage() {

		weather = new HashMap<WeatherType, Log>();

		for (WeatherType type : WeatherType.values()) {

			weather.put(type, new Log());

		}

	}

	/**
	 * Creates a test copy of the storage with pre-initialized days, hours, and
	 * months.
	 */
	public Storage(HashMap<WeatherType, Double[]> Hours, HashMap<WeatherType, Double[]> Days,
			HashMap<WeatherType, Double[]> Months) {
		weather = new HashMap<WeatherType, Log>();

		for (WeatherType type : WeatherType.values()) {
			
			weather.put(type, new Log(Hours.get(type), Days.get(type), Months.get(type)));

		}
	}

	/**
	 * Add a reading to the appropriate log
	 * 
	 * @param type        Enum specifying the kind of measurement
	 * @param measurement The value of the measurement
	 */
	public void add(WeatherType type, double measurement) {

		weather.get(type).logWeather(measurement);

	}

	/**
	 * Retrieve appropriate weather log for graphing purposes
	 * 
	 * @param type   The type of measure to look up
	 * @param window The period of time we are interested in
	 * @param high   Boolean flag- period high = true, period low = false
	 * @return The specified record of measures, or null if not found.
	 */
	public double[] getHistory(WeatherType type, Window window, boolean high) {

		return weather.get(type).getLog(window, high);

	}

	/**
	 * Returns the values for all WeatherTypes at a specific window of time. Calling
	 * getAllAtTime(WeatherType.CURRENT, 0) gets the most recent data. Calling
	 * getAllAtTime(WeatherType.DAY, 1) gets yesterdays set of data.
	 * 
	 * @param The window corresponding to the timescale the request is being made
	 *            at.
	 * @param The number of time units that reflects the most current window.
	 * @return A map that associates each measurement with its respective weather
	 *         type.
	 */
	public HashMap<WeatherType, Double> getAllAtTime(Window theWindow, int displacement) {
		if (displacement > 24) {
			throw new IllegalArgumentException();
		}
		HashMap<WeatherType, Double> toReturn = new HashMap<>();
		for (WeatherType t : weather.keySet()) {
			double[] data = weather.get(t).getLog(theWindow, true);
			//The current data is always at index zero.
			if (theWindow== Window.current) toReturn.put(t, data[displacement]);
			//The long term most recent is always at the last index.
			else toReturn.put(t, data[data.length-1-displacement]); // Get the desired element of the current time window, in T-1.
		}
		return toReturn;

	}

}
