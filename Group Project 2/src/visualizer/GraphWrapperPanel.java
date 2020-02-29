/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import javax.swing.JTabbedPane;
import javax.swing.Timer;

import storage.Storage;
import storage.WeatherType;
import storage.Window;

/**
 * Initializes and manages the live data feed of the main user interface of the
 * Weather Visualizer Window.
 * 
 * @author Rory Fisher.
 */
public class GraphWrapperPanel extends JTabbedPane implements WeatherDataItem{

	/**
	 * A version ID.
	 */
	private static final long serialVersionUID = 5400286261880061827L;

	/**
	 * The current values that will be added to the storage at the end of the timer.
	 */
	private HashMap<WeatherType, Double> myCurrent;

	/**
	 * The storage element integrated into this specific UI.
	 */
	private Storage myStorage;

	/**
	 * The timer that has control over the simulated network of this window, which
	 * drives this window to call the storage for new data types.
	 */
	private Timer myTimer;
	
    /**
	 * Creates a new window which is able to display weather data from the vue
	 * weather system.
	 */
	public GraphWrapperPanel(Storage theStorage, HashMap<WeatherType, Double> startingData) {
		// Will later accept inputs for whatever event generators and data systems we
		// use in the project, but currently it has no inputs.
		setBounds(0, 0, 483, 354);	
		myCurrent = new HashMap<>();
		myCurrent.putAll(startingData);
		myStorage = theStorage;
		//Initializes the graphics tabs.
		addGraphicsTabs();
		//Add a new timer that runs for one hour, loads the new data into the storage system, and 
		myTimer = new Timer(3600000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				removeAll();
				myStorage.addBulk(Window.hours, myCurrent);
				addGraphicsTabs();
			}
			
		});
		myTimer.start();
	}

	
	/**
	 * Sets the window to have its various graphical interfaces for displaying long
	 * term weather data.
	 * 
	 * @param myLowerFrame The tabbed pane to be configured.
	 */
	private void addGraphicsTabs() {
		WeatherType[] supported = { WeatherType.temp, WeatherType.outtemp, WeatherType.humidity,
				WeatherType.outhumidity, WeatherType.rain, WeatherType.wind, WeatherType.barometric };
		// Create the many graphics frames, in nested tabs.
		for (WeatherType t : supported) {
			JTabbedPane aInnerFrame = new JTabbedPane();
			add(t.toString(), aInnerFrame);
			LinkedList<Double> toAdd = myStorage.getHistory(t, Window.hours, true);
			// Add the hours.
			aInnerFrame.add(Window.hours.toString(),
			new GraphPanel(t.toString() + t.getUnits(), Window.hours.toString() + " Ago", toAdd));
			// Make a new list, add the days.
			toAdd = myStorage.getHistory(t, Window.days, true);
			aInnerFrame.add(Window.days.toString(),
					new GraphPanel(t.toString() + t.getUnits(), Window.days.toString() + " Ago", toAdd));
			// Make a new list, add the months.
			toAdd = myStorage.getHistory(t, Window.months, true);
			aInnerFrame.add(Window.months.toString(),
					new GraphPanel(t.toString() + t.getUnits(), Window.months.toString() + " Ago", toAdd));
		}
	}

	/**
	 * Called when the storage has been written to, sends the newly updated values
	 * for various data points.
	 * 
	 * @param someData The latest weather data.
	 */
	@Override
	public void updateData(Map<WeatherType, Double> someData) {
		// TODO Auto-generated method stub
		HashMap<WeatherType, Double> myCurrent = new HashMap<>();
		myCurrent.putAll(someData);
	}

}
