package storage;

import java.util.List;

/**
 * A log stores all inputs over the last the last 25 hours, 25 days, and 25 months 
 * are stored. 
 * 
 * This can be divided into highs and lows, otherwise if only single data value is given, it is logged in both the high and low array.
 * 
 * @author Rory Fisher
 * @author Geoffrey Woulf
 *
 */
public interface Log {

	/**
	 * Adds weather data to long term storage at a given time frame.
	 * @param theWindow The time window this measurement is appropriate for.
	 * @param theMeasurement The measurement that was logged.
	 */
	public void addAtTime(Window theWindow, Double theMeasurement);
	
	/**
	 * Adds weather data to long term storage at a given time frame.
	 * Records one data value as a high, the other as a low.
	 * @param theWindow The time window this measurement is appropriate for.
	 * @param theHigh The Higher measurement that was logged over a window of time.
	 * @param TheLow Weather The Lower measurement that was logged over a window of time.
	 */
	public void addAtTime(Window theWindow, Double theHigh, Double theLow);
	
	/**
	 * Returns an array of values recording the high or lows from the last
	 * Window of time.
	 * @param window The window of time we want
	 * @param high True if we want the highs, false for lows
	 * @return An array with the most recent reading at the last index. Or null if no array found.
	 */
	public List<Double> getLog(Window theWindow, boolean theHigh);
	
	/**
	 * Returns the value at a specific time.
	 * @param theWindow The time window we are looking at.
	 * @param theOffset How many time units in the past we are looking at, between 0-24.
	 * @param theHigh Whether we are seeking a high or a low value from this time window.
	 */
	public Double getAtTime(Window theWindow, int theOffset, boolean theHigh);
	
}
