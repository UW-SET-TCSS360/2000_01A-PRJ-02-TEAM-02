/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;

import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import storage.WeatherType;

/**
 * A specialized JFrame that is designed to show comprehensive weather
 * information at a time. It can be run with a live feed of weather updates, or
 * called with specific weather data to create a static viewing window on past data.
 */
public class UpperWeatherPanel extends JPanel {

	/**
	 * Randomly Generated serial ID.
	 */
	private static final long serialVersionUID = 210812046764856983L;

	/**
	 * The numbers format used in an upperPanelWindow's UI.
	 */
	private DecimalFormat myFormat = new DecimalFormat("#0.000");
	
	/**
	 * The specific types of weather information our interface is ready to display.
	 */
	private WeatherType[] supportedTypes = { WeatherType.wind, WeatherType.winddir, WeatherType.rain,
			WeatherType.barometric, WeatherType.temp, WeatherType.humidity, WeatherType.outtemp,
			WeatherType.outhumidity};

	/**
	 * A string driven map of the types of weather data.
	 */
	private Map<WeatherType, Double> myData;
	/**
	 * A map which associates the JLabels for displaying each element of weather
	 * information.
	 */
	private Map<WeatherType, JLabel> myDisplayElements;

	/**
	 * Generates a window that is used to display current weather information.
	 * The input map MUST contain values for the wind, wind direction, rain, barometric pressure,
	 * temp, humidity, outdoor temperature, or outdoor humidity, otherwise this method will throw
	 * an illegal argument exception.
	 * 
	 * @param theData the Weather Data, in accordance with our data types.
	 */
	public UpperWeatherPanel(Map<WeatherType, Double> theData) {
		super();
		myData = new HashMap<>();
		myData.putAll(theData);
		setupDisplayBits();
		setLayout(new GridLayout(2, 4));

	}

	/**
	 * Testing Version of the constructor, initializes the upper display with
	 * default data.
	 */
	public UpperWeatherPanel() {
		super();
		setLayout(new GridLayout(2, 4));
		myData = new HashMap<>();
		myData.put(supportedTypes[0], 15.2);
		myData.put(supportedTypes[1], 23.2);
		myData.put(supportedTypes[2], 0.3);
		myData.put(supportedTypes[3], 1.05);
		myData.put(supportedTypes[4], 72.3);
		myData.put(supportedTypes[5], 30.7);
		myData.put(supportedTypes[6], 52.2);
		myData.put(supportedTypes[7], 86.3);
		myData.put(WeatherType.windchill, 50.0);
		setupDisplayBits();
	}

	/**
	 * Generates and stores the JLabels in a organized and easily accessed way.
	 */
	private void setupDisplayBits() {
		// Setup Wind Display
		myDisplayElements = new HashMap<>();
		for (int i = 0; i < supportedTypes.length; i++) {
			JLabel aLabel = new JLabel(getTypeString(i));
			aLabel.setBorder(BorderFactory.createBevelBorder(0));
			myDisplayElements.put(supportedTypes[i], aLabel);
			add(aLabel);
		}
	}

	/**
	 * Automatically generates an appropriate string for the specific measurement
	 * and its units.
	 * 
	 * @param index The data measurements index.
	 * @return A string representing the current data in a human readable form.
	 */
	private String getTypeString(int index) {
		String toReturn;
		if (index == 6) {
			toReturn = "          " + supportedTypes[index] + " (w/Windchill): " + 
					myFormat.format(myData.get(supportedTypes[index]))
					+ " " + supportedTypes[index].getUnits() + "\n (" + myFormat.format(myData.get(WeatherType.windchill)) + " "
					+ supportedTypes[index].getUnits() + ")";
		} // When outdoor temps are displayed, it is displayed both with and without
			// windchill. The - 6 is a default wind chill value that will be updated.
		else if (index == 1) toReturn = "          " + supportedTypes[index] + ": " + getDirection(myData.get(supportedTypes[index]));
		//Use a word to describe the wind direction.
		else
			toReturn = "          " + supportedTypes[index] + ": " + myFormat.format(myData.get(supportedTypes[index])) + " "
					+ supportedTypes[index].getUnits();

		return toReturn;
	}

	/**
	 * Takes an angle that is in degrees, and returns a direction starting from straight north, and rotating degrees clockwise.
	 * AKA, the "Degrees from north" method.
	 * @param theDegree A double representing a value that is between from 0 to 360.
	 * @return A string describing the direction the given angle points in.
	 */
	private String getDirection(Double theDegree) {
		if (theDegree<0 || theDegree>360)
			throw new IllegalArgumentException();
		String toReturn;
		if (theDegree<30 && theDegree>=0 || theDegree>=330 && theDegree <=360) toReturn="North";
		else if (theDegree>=30 && theDegree<60) toReturn = "North-East";
		else if (theDegree>=60 && theDegree<120) toReturn = "East";
		else if (theDegree>=120 && theDegree<150) toReturn = "South-East";
		else if (theDegree>=150 && theDegree<210) toReturn = "South";
		else if (theDegree>=210 && theDegree<240) toReturn = "South-West";
		else if (theDegree>=240 && theDegree<300) toReturn = "West";
		else toReturn = "NorthWest";
		return toReturn;
	}

	/**
	 * Updates the data and display labels.
	 * 
	 * @param A map of "Weather data types" to the newest data values. May be
	 *          partially filled, in which case only the correct display elements
	 *          will be updated.
	 */
	public void updateData(Map<WeatherType, Double> someData) {
		myData.put(WeatherType.windchill, someData.get(WeatherType.windchill));
		//First, update the data on windchill, as it is not supported by its own UI panel, and is instead a sub-element of another panel.
		for (int i = 0; i < supportedTypes.length; i++) {
			WeatherType type = supportedTypes[i]; // Get the Datatype.
			if (someData.containsKey(type)) { // See if it was included in the list of updated data.
				myData.put(type, someData.get(type)); // Update our local copy of the data.
				String toSet = getTypeString(i);
				myDisplayElements.get(type).setText(toSet); // Update the text of our display elements.
				// Only if we have new data.
			}
		}
	}

}
