/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package storage;

import java.util.Arrays;

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
public class Log {
	
	/**
	 * The max value witnessed over each hour.
	 */
	private double[] hourMax;
	/**
	 * The minimum value witnessed over each hour.
	 */
	private double[] hourMin;
	/**
	 * The max value witnessed over each day.
	 */
	private double[] dayMax;
	/**
	 * The minimum value witnessed over each day.
	 */
	private double[] dayMin;
	/**
	 * The max value witnessed over each month.
	 */
	private double[] monthMax;
	/**
	 * The minimum value witnessed over each month.
	 */
	private double[] monthMin;
	/**
	 * An array of 1440 in length for tracking data on the 2.5 second scale.
	 */
	private double[] current;
	
	/**
	 * The current number of entries that have been processed by the hours array.
	 */
	private int hourHead;
	/**
	 * The current number of entries that have been processed by the days array.
	 */
	private int dayHead;
	/**
	 * The current number of entries that have been processed by the months array.
	 */
	private int monthHead;
	/**
	 * The current number of entries that have been processed by the current array.
	 */
	private int currentHead;
	
	/**
	 * The last index of the hour array.
	 */
	private int hourTail;
	/**
	 * The last index of the day array.
	 */
	private int dayTail;
	/**
	 * The last index of the month array.
	 */
	private int monthTail;
	/**
	 * The last index of the current array.
	 */
	private int currentTail;
	
	/**
	 * The number of entries that has been entered into the current array.
	 * Resets to 0 at 1440.
	 */
	private int countCurrent;
	/**
	 * The number of entries that has been entered into the hour array.
	 * Resets to 0 after 25.
	 */
	private int countHour;
	/**
	 * The number of entries that has been entered into the day array.
	 * Resets to 0 after 30.
	 */
	private int countDay;
	
	/**
	 * Default constructor instantiates buffers for saving weather logs,
	 * as well as initializing state variables like arrays etc.
	 */
	public Log() {
		
		this.hourMax = new double[25];
		this.hourMin = new double[25];
		this.hourHead = 0;
		this.hourTail = 24;
		
		this.dayMax = new double[30];
		this.dayMin = new double[30];
		this.dayHead = 0;
		this.dayTail = 29;
		
		this.monthMax = new double[25];
		this.monthMin = new double[25];
		this.monthHead = 0;
		this.monthTail = 24;
		
		this.current = new double[1440];
		this.currentHead = 0;
		this.currentTail = 1439;
		
		this.countCurrent = 0;
		this.countDay = 0;
		this.countHour = 0;
		
	}
	
	/**
	 * Creates the default buffers with pre-filled "Max" values for a given window of time.
	 * Exists for testing purposes.
	 * @param theHours The Hour simulator data.
	 * @param theDays The Day simulator data.
	 * @param theMonths the Month Simulator data.
	 */
	protected Log(Double[] theHours, Double[] theDays, Double[] theMonths) {
		this.hourMax = new double[25];
		for (int i = 0; i<theHours.length; i++) {
			hourMax[i] = theHours[i];
		} 
		this.hourMin = new double[25];
		this.hourHead = theHours.length;
		this.hourTail = 24;
		
		this.dayMax = new double[30];
		this.dayMin = new double[30];
		for (int i = 0; i<theDays.length; i++) {
			dayMax[i] = theDays[i];
		}
		this.dayHead = theDays.length;
		this.dayTail = 29;
		
		this.monthMax = new double[25];
		for (int i = 0; i<theMonths.length; i++) {
			monthMax[i] = theMonths[i];
		}
		this.monthMin = new double[25];
		this.monthHead = theMonths.length;
		this.monthTail = 24;
		
		this.current = new double[1440];
		this.currentHead = 0;
		this.currentTail = 1439;
		
		this.countCurrent = 0;
		this.countDay = 25;
		this.countHour = 25;
	}
	
	/**
	 * Add a weather reading
	 * @value reading The weather reading to store
	 */
	public void logWeather(double reading) {
		

		
		// We adjust the head
		currentHead = (currentHead + 1) % current.length;
		
		// We add the new reading at the head
		current[currentHead] = reading;
		// We move the tail if required
		if(currentTail == currentHead) {
			
			currentTail = currentTail > 1439 ? 0 : currentTail + 1;  
			
		}

		// We increment the number of additions
		++countCurrent;
		
		// If an hour has passed
		if(countCurrent % 1440 == 0) {
			
			// We reset the timer
			countCurrent = 0;
			
			// We add another entry to the hours 
			this.addHour();
			
		}
		
	}
	
	/**
	 * Updates the hour array.
	 */
	private void addHour() {
		
		// We record the mins and maxes from the last hour
		hourMax[hourHead] = Arrays.stream(current).max().orElse(0);
		hourMin[hourHead] = Arrays.stream(current).min().orElse(0);
		
		// We increment the head
		hourHead = hourHead > 23 ? 0 : hourHead + 1;
		
		// We update the tail
		if (hourHead == hourTail) {
			
			hourTail = hourTail == 24 ? 0 : hourTail + 1;
			
		}
		
		// We increment the hour counter
		countHour += 1;
		if(countHour > 23) {
			
			// If 24hrs have passed we update the day entries and reset the counter
			countHour = 0;
			addDay();
			
		}
		
		
	}
	
	/**
	 * We add the average of our last 24hrs 
	 */
	private void addDay() {
		
		double days = hourMax[hourHead - 1 > -1 ? hourHead - 1 : 24];
		for (int i = 0, j = hourHead; i < 24; i++) {
			
			--j;
			
			if(j < 0) {
				
				j = 24;
				
			}

			days = Math.max(days, hourMax[j]);
			
		}
		
		dayMax[dayHead] = days;
		
		for (int i = 0, j = hourHead; i < 24; i++) {
			
			--j;
			
			if(j < 0) {
				
				j = 24;
				
			}

			days = Math.min(days, hourMin[j]);
			
		}
		
		dayMin[dayHead] = days;
		
		dayHead = dayHead == 29 ? 0 : dayHead + 1;
		
		if(dayHead == dayTail) {
			
			dayTail = dayTail == 29 ? 0 : dayTail + 1;
			
		}
		
		countDay += 1;
		if(countDay > 29) {
			
			countDay = 0;
			addMonth();
			
		}
		
	}
	
	/**
	 * We add the last month of data to our months. We track only the 
	 * last 25 months, after which the data is overwritten.
	 */
	private void addMonth() {
		
		monthMax[monthHead] = Arrays.stream(dayMax).max().orElse(0);
		monthMin[monthHead] = Arrays.stream(dayMin).min().orElse(0);
		
		monthHead = monthHead == 25 ? 0 : monthHead + 1;
		
		if(monthHead == monthTail) {
			
			monthTail = monthTail == 25 ? 0 : monthTail + 1;
			
		}
		
	}
	
	/**
	 * Returns an array of values recording the high or lows from the last
	 * Window of time.
	 * @param window The window of time we want
	 * @param high True if we want the highs, false for lows
	 * @return An array with the most recent reading at the last index. Or null if no array found.
	 */
	public double[] getLog(Window window, boolean high) {
		
		double[] array;
		
		switch(window) {
		
			case days:
				array = high ? makeArray(dayMax, dayHead) : 
					makeArray(dayMin, dayHead);
				break;
				
			case hours:
				array = high ? makeArray(hourMax, hourHead) : 
					makeArray(hourMin, hourHead);
				break;
				
			case months:
				array = high ? makeArray(monthMax, monthHead) : 
					makeArray(monthMin, monthHead);
				break;
			case current:
				array = makeCurrent();
				break;
			default:
				array = null;
		}
		
		
		return array;
	}
	
	/**
	 * Helper function to put the array into proper order
	 * @param array The array
	 * @param head The head of the log
	 * @return An array with the most recent reading at the last index
	 */
	private double[] makeArray(double[] array, int head) {
		
		int size = 25;
		double[] newArray = new double[size]; 
		
		for(int i = size - 1, j = head; i > -1; --i) {
			
			j = j - 1 > -1 ? j - 1 : array.length - 1;
			
			newArray[i] = array[j];
			
		}
		
		return newArray;
		
	}
	
	/**
	 * A specialized method for making the array of current values to return.
	 * @return An array where the most current value is 0.
	 */
	private double[] makeCurrent() {
		int size = countCurrent;
		double[] array = new double[size];
		
		for(int i = 0, j = currentHead; i < size; i++, j--) {
			
			j = j < 0 ? size - 1 : j;
			array[i] = current[j];
			
		}

		return array;
		
	}
	
}
