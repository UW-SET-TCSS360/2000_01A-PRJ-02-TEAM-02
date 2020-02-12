package storage;

import java.util.LinkedList;

/**
 * A log stores all inputs over the last hour, (where the last 1440 updates
 * constitute an hour- 1 update every 2.5 seconds). 
 * 
 * As well, the mins and maxes for the last 25 hours, 30 days, and 25 months 
 * are stored. 
 * 
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
	 * Returns an array of values recording the high or lows from the last
	 * Window of time.
	 * @param window The window of time we want
	 * @param high True if we want the highs, false for lows
	 * @return An array with the most recent reading at the last index. Or null if no array found.
	 */
	public LinkedList<Double> getLog(Window window, boolean high) ;
	
}
