package storage;

import java.util.LinkedList;

/**
 * Allows for the tracking and retrieval of floating point data by the hour,
 * day, or month.
 * 
 * @author Rory Fisher
 */
public class LinkedLog implements Log {

	/**
	 * The list of hourly measurements.
	 */
	private LinkedList<Double> myHours;

	/**
	 * The list of daily measurements.
	 */
	private LinkedList<Double> myDays;

	/**
	 * The List of monthly measurements.
	 */
	private LinkedList<Double> myMonths;

	/**
	 * The list of hourly measurements.
	 */
	private LinkedList<Double> myHourLows;

	/**
	 * The list of daily measurements.
	 */
	private LinkedList<Double> myDayLows;

	/**
	 * The List of monthly measurements.
	 */
	private LinkedList<Double> myMonthLows;

	/**
	 * Creates a new linked log, with all logs being "Filled" by dummy data that is
	 * never going to appear in real weather measurements.
	 */
	public LinkedLog() {
		myHours = new LinkedList<Double>();
		myDays = new LinkedList<Double>();
		myMonths = new LinkedList<Double>();
		myHourLows = new LinkedList<Double>();
		myDayLows = new LinkedList<Double>();
		myMonthLows = new LinkedList<Double>();
		for (int i = 0; i < 25; i++) {
			myHours.add(-9999.);
			myHourLows.add(-9999.);
			myDays.add(-9999.);
			myDayLows.add(-9999.);
			myMonths.add(-9999.);
			myMonthLows.add(-9999.);

		}
	}

	/**
	 * Creates a new linked log, with the high and low logs being filled by the
	 * starter data provided by the following parameters. All arrays must be 25
	 * entries or less.
	 * 
	 * @param theHours  An array of the hourly data.
	 * @param theDays   An array of the daily data.
	 * @param theMonths An array of the monthly data.
	 */
	public LinkedLog(Double[] theHours, Double[] theDays, Double[] theMonths) {
		if (theHours.length > 25 || theDays.length > 25 || theMonths.length > 25)
			throw new IllegalArgumentException("One of the arrays was too large!");
		myHours = new LinkedList<Double>();
		myDays = new LinkedList<Double>();
		myMonths = new LinkedList<Double>();
		myHourLows = new LinkedList<Double>();
		myDayLows = new LinkedList<Double>();
		myMonthLows = new LinkedList<Double>();
		for (int i = 0; i < theHours.length; i++) {
			myHours.add(theHours[i]);
			myHourLows.add(theHours[i]);
		}
		for (int i = 0; i < theDays.length; i++) {
			myDays.add(theDays[i]);
			myDayLows.add(theDays[i]);
		}
		for (int i = 0; i < theMonths.length; i++) {
			myMonths.add(theMonths[i]);
			myMonthLows.add(theMonths[i]);
		}
	}

	/**
	 * Creates a new linked log, with the high and low logs being filled by the
	 * starter data provided by the following parameters. Tracks high and low data
	 * points separately.
	 * 
	 * Asserts that the high and low data arrays are of equal length.
	 * 
	 * @param theHours  An array of the hourly data.
	 * @param theDays   An array of the daily data.
	 * @param theMonths An array of the monthly data.
	 */
	public LinkedLog(Double[] theHours, Double[] theHourLows, Double[] theDays, Double[] theDayLows, Double[] theMonths,
			Double[] theMonthLows) {
		if (theHours.length > 25 || theDays.length > 25 || theMonths.length > 25)
			throw new IllegalArgumentException("One of the arrays was too large!");
		if (theHours.length != theHourLows.length || theDays.length != theDayLows.length
				|| theMonths.length != theMonthLows.length)
			throw new IllegalArgumentException("High/Low array length mismatch.");
		myHours = new LinkedList<Double>();
		myDays = new LinkedList<Double>();
		myMonths = new LinkedList<Double>();
		myHourLows = new LinkedList<Double>();
		myDayLows = new LinkedList<Double>();
		myMonthLows = new LinkedList<Double>();
		for (int i = 0; i < theHours.length; i++) {
			myHours.add(theHours[i]);
			myHourLows.add(theHourLows[i]);
		}
		for (int i = 0; i < theDays.length; i++) {
			myDays.add(theDays[i]);
			myDayLows.add(theDayLows[i]);
		}
		for (int i = 0; i < theMonths.length; i++) {
			myMonths.add(theMonths[i]);
			myMonthLows.add(theMonthLows[i]);
		}
	}

	@Override
	public void addAtTime(Window theWindow, Double theMeasurement) {
		switch (theWindow) {
		case hours:
			myHours.add(theMeasurement);
			myHourLows.add(theMeasurement);
			if (myHours.size() > 25) {
				myHours.removeFirst();
				myHourLows.removeFirst();
			}
			break;
		case days:
			myDays.add(theMeasurement);
			myDayLows.add(theMeasurement);
			if (myDays.size() > 25) {
				myDays.removeFirst();
				myDayLows.removeFirst();
			}
			break;
		case months:
			myMonths.add(theMeasurement);
			myMonthLows.add(theMeasurement);
			if (myMonths.size() > 25) {
				myMonths.removeFirst();
				myMonthLows.removeFirst();
			}
			break;
		}
	}

	@Override
	public void addAtTime(Window theWindow, Double theHigh, Double theLow) {
		switch (theWindow) {
		case hours:
			myHours.add(theHigh);
			myHourLows.add(theLow);
			if (myHours.size() > 25) {
				myHours.removeFirst();
				myHourLows.removeFirst();
			}
			break;
		case days:
			myDays.add(theHigh);
			myDayLows.add(theLow);
			if (myDays.size() > 25) {
				myDays.removeFirst();
				myDayLows.removeFirst();
			break;
			}
		case months:
			myMonths.add(theHigh);
			myMonthLows.add(theLow);
			if (myMonths.size() > 25) {
				myMonths.removeFirst();
				myMonthLows.removeFirst();
			}
			break;
		}
	}

	@Override
	public LinkedList<Double> getLog(Window theWindow, boolean theHigh) {
		// Make a defensively copied list.
		LinkedList<Double> toReturn = new LinkedList<>();
		// Populate it to conform with the request.
		switch (theWindow) {
		case hours:
			if (theHigh)
				toReturn.addAll(myHours);
			else
				toReturn.addAll(myHourLows);
			break;
		case days:
			if (theHigh)
				toReturn.addAll(myDays);
			else
				toReturn.addAll(myDayLows);
			break;
		case months:
			if (theHigh)
				toReturn.addAll(myMonths);
			else
				toReturn.addAll(myMonthLows);
			break;
		}
		return toReturn;
	}
	
	/**
	 * Provides the current size of a specific log, so that the user can avoid out of bounds checks.
	 * @param theWindow The timeframe of the log the user wishes to get a time for.
	 * @return The length of the log in number of elements.
	 */
	public int getLength(Window theWindow) {
		int toReturn = 0;
		switch(theWindow) {
		case hours:
			toReturn = myHours.size();
			break;
		case days:
			toReturn = myDays.size();
			break;
		case months:
			toReturn = myMonths.size();
			break;
		}
		return toReturn;
	}

	@Override
	public Double getAtTime(Window theWindow, int theOffset, boolean theHigh) {
		// Bounds Check.
		if (theOffset < 0 || theOffset > 24)
			throw new IndexOutOfBoundsException("Error: Requested a data value outside of supported range.");
		Double toReturn = 0.;

		switch (theWindow) {
		case hours:
			if (theHigh)
				toReturn = myHours.get(myHours.size() - 1 - theOffset);
			else
				toReturn = myHourLows.get(myHourLows.size() - 1 - theOffset);
			break;
		case days:
			if (theHigh)
				toReturn = myDays.get(myDays.size() - 1 - theOffset);
			else
				toReturn = myDayLows.get(myDayLows.size() - 1 - theOffset);
			break;
		case months:
			if (theHigh)
				toReturn = myMonths.get(myMonths.size() - 1 - theOffset);
			else
				toReturn = myMonthLows.get(myMonthLows.size() - 1 - theOffset);
			break;
		}
		return toReturn;
	}

}
