/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package storage;

/**
 * Possible values for returning data points
 * @author Geoffrey Woulf
 *
 */
public enum Window {
	current("2.5 Seconds"),
	hours("Hours"),
	days("Days"),
	months("Months");
	
	/**
	 * The description for a type of weather.
	 */
	private String myDescription;
	
	/**
	 * Initializes the description of a window of time.
	 * @param theDescription The windows name.
	 */
	private Window(String theDescription) {
		myDescription = theDescription;
	}
	
	@Override
	public String toString() {
		return myDescription;
	} 
}
