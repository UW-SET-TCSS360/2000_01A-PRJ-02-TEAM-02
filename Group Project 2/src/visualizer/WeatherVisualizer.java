/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONException;

import networking.OpenWeatherMap;
import storage.Storage;
import storage.WeatherType;
import storage.Window;

/**
 * A class which initializes the vantage vue simulator, containing the main method for the class overall.
 * @author Rory Fisher.
 *
 */
public class WeatherVisualizer {

	/**
	 * Activates the front and backend of the Vue simulator.
	 * 
	 * @param args Command line args, currently unused.
	 */
	public static void main(String[] args) {
		try {
			OpenWeatherMap aOWM = new OpenWeatherMap();		
			//Initialize the simulator.
			HashMap<WeatherType, Double[]> Months = aOWM.getInitialSets(Window.months);
			HashMap<WeatherType, Double[]> Days = aOWM.getInitialSets(Window.days);		
			HashMap<WeatherType, Double[]> Hours = aOWM.getInitialSets(Window.hours);
			aOWM.newDay();
			//Set up data to create a "Testing" copy of the storage.
			Storage aStorage = new Storage(Hours,Days,Months);
			//Run the storage generation with the test data.
			WeatherVisualizerWindow myWindow = new WeatherVisualizerWindow(aStorage, aOWM);
			//Generate the window.
			myWindow.display();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
