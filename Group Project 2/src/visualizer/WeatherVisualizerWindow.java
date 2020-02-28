/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import networking.Network;
import networking.OpenWeatherMap;
import storage.Storage;
import storage.WeatherType;
import storage.Window;

/**
 * Initializes and manages the live data feed of the main user interface of the
 * Weather Visualizer Window.
 * 
 * @author Rory Fisher.
 */
public class WeatherVisualizerWindow {

	/**
	 * The window managed by the class.
	 */
	private JFrame myWindow;

	/**
	 * The live data feed that is the upper frame.
	 */
	private UpperWeatherPanel myUpperFrame;

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
	 * The alert window that processes alert features.
	 */
	private Alerts myAlertWindow;
	
	/**
	 * The help window that contains various user manuals.
	 */
	private HelpWindow myHelper;

	/**
	 * Creates a new window which is able to display weather data from the vue
	 * weather system.
	 */
	public WeatherVisualizerWindow(Storage theStorage, OpenWeatherMap theOWM) {
		// Will later accept inputs for whatever event generators and data systems we
		// use in the project, but currently it has no inputs.
		myWindow = new JFrame();
		myWindow.setSize(new Dimension(1300, 600));
		myWindow.setTitle("Thermostat for Outside.");
		myAlertWindow = new Alerts();
		myStorage = theStorage;
		myHelper = new HelpWindow();
		myTimer = new Timer(2500, new NetworkTimerActionListener(theOWM));
		try { // Find if java supports a native software look and field, and if it does, open
				// this window with the selected look and feel.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// No need to do anything special here! it just means we will use the default
			// look and feel.
		}
	}

	/**
	 * Opens the window and configures it with its default layout..
	 */
	public void display() {
		// Setup the main elements.
		JMenuBar myBar = new JMenuBar();
		JTabbedPane myLowerFrame = new JTabbedPane();
		// Call the menu bar setup method, get the buttons/static menu elements.
		initializeMenuBar(myBar);
		// Generate the graphics.
		addGraphicsTabs(myLowerFrame);
		// Set up the main UI elements.
		myWindow.setJMenuBar(myBar);
		// Initialize the live feed window, with the last hourly data (the most recent
		// prior to the live feed being initialized.)
		myUpperFrame = new UpperWeatherPanel(myStorage.getAllAtTime(Window.hours, 0));
		myWindow.add(myUpperFrame, BorderLayout.CENTER);
		myWindow.add(myLowerFrame, BorderLayout.SOUTH);
		// Start the live updates, set up the window to disable the timer on close.
		myTimer.start();
		myWindow.addWindowListener(new TimerShutoff());
		// Set the window to close this application when it is closed.
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Make the window visible.
		myWindow.setVisible(true);
	}

	/**
	 * Sets the window to have its various graphical interfaces for displaying long
	 * term weather data.
	 * 
	 * @param myLowerFrame The tabbed pane to be configured.
	 */
	private void addGraphicsTabs(JTabbedPane myLowerFrame) {
		WeatherType[] supported = { WeatherType.temp, WeatherType.outtemp, WeatherType.humidity,
				WeatherType.outhumidity, WeatherType.rain, WeatherType.wind, WeatherType.barometric };

		// Create the many graphics frames, in nested tabs.
		for (WeatherType t : supported) {
			JTabbedPane aInnerFrame = new JTabbedPane();
			myLowerFrame.add(t.toString(), aInnerFrame);
			ArrayList<Double> toAdd = new ArrayList<>();
			// Add the hours.
			for (Double d : myStorage.getHistory(t, Window.hours, true))
				toAdd.add(d);
			aInnerFrame.add(Window.hours.toString(),
					new GraphPanel(t.toString() + t.getUnits(), Window.hours.toString() + " Ago", toAdd));
			// Make a new list, add the days.
			toAdd = new ArrayList<>();
			for (Double d : myStorage.getHistory(t, Window.days, true))
				toAdd.add(d);
			aInnerFrame.add(Window.days.toString(),
					new GraphPanel(t.toString() + t.getUnits(), Window.days.toString() + " Ago", toAdd));
			// Make a new list, add the months.
			toAdd = new ArrayList<>();
			for (Double d : myStorage.getHistory(t, Window.months, true))
				toAdd.add(d);
			aInnerFrame.add(Window.months.toString(),
					new GraphPanel(t.toString() + t.getUnits(), Window.months.toString() + " Ago", toAdd));

		}

	}

	/**
	 * Called when the storage has been written to, sends the newly updated values
	 * for various data points.
	 */
	private void updateData() {
		Map<WeatherType, Double> someData = myStorage.getAllAtTime(Window.current, 0);
		myUpperFrame.updateData(someData);
		myAlertWindow.check(someData);
	}

	/** Sets up the menu bar with the options menus and buttons. */
	private void initializeMenuBar(JMenuBar myBar) {
		//Generate these header "Weather State" bits at random, we don't have a meaningful way
		//of measuring or simulating them currently.
		String[] moonPhases = {"   Moon Phase: New Moon", "   Moon Phase: Quarter Moon", 
				"   Moon Phase: Full Moon", 
				"   Moon Phase: Waxing Moon", 
				"   Moon Phase: Waining Moon"};
		String[] cloudCoverStates = {"   Cloud Cover: Mostly Cloudy", "   Cloud Cover: Partially Cloudy", "   Cloud Cover: Mostly Clear", "   Cloud Cover: Clear", "   Cloud Cover: Overcast"};
		JLabel moonPhase = new JLabel(moonPhases[(int)(Math.random()*moonPhases.length)]);// Placeholder.
		JLabel cloudCover = new JLabel(cloudCoverStates[(int)(Math.random()*cloudCoverStates.length)]);// Placeholder.
		JLabel sunRise = new JLabel("   Sunrise Time: " + (6 + (int)(Math.random()*2)) +":"+ 
									(10+(int)(Math.random()*50)) + " AM");// Placeholder.
		JLabel sunSet = new JLabel(("   Sunset Time: " + (6 + (int)(Math.random()*4)) +":"+ 
				(10+(int)(Math.random()*50)) + " pm"));// Placeholder.


		// Initialize the buttons.
		JButton snapshotButton = new JButton("Get Snapshot");
		snapshotButton.addActionListener(new SnapshotWindowListner());
		JButton manualButton = new JButton("About/Manual");
		// This simple action listener only executes a single line of code, to make the
		// help window visible.
		manualButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				myHelper.setVisible(true);
			}
		});

		myBar.add(snapshotButton);
		myBar.add(myAlertWindow.getAlarmButton());
		myBar.add(manualButton);// Placeholder.
		myBar.add(moonPhase); // Display static for a given day data on the menu bar.
		myBar.add(cloudCover);
		myBar.add(sunRise);
		myBar.add(sunSet);
	}

	/**
	 * Initializes and manages the simulated network used in managing the storage.
	 * Due to this network being extremely reliable.
	 * 
	 * @author Rory Fisher
	 *
	 */
	private class NetworkTimerActionListener implements ActionListener {

		/**
		 * The simulated network used by our timer.
		 */
		Network myNetwork;

		/**
		 * Initializes a new timer listener, and its own simulated network.
		 */
		private NetworkTimerActionListener(OpenWeatherMap theOWM) {
			myNetwork = new Network(myStorage, theOWM);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean newValues = myNetwork.checkSimulator();
			// Check if the simulator is "Ready" to send out new values.
			if (newValues) {
				myNetwork.sendToStorage();
				// Send the simulators data to the storage system.
				updateData();
				// Call the front end to recieve the storage data.
			}
		}

	}

	/**
	 * Makes sure the timer properly shuts down when the window is closed.
	 * 
	 * @author Rory Fisher
	 *
	 */
	private class TimerShutoff extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			myTimer.stop();
		}
	}

	/**
	 * Prompts the user for an input on how long ago they want to get data from.
	 * Then it opens a variation of the live info feed panel that shows the exact
	 * data recorded at the specific time the user inputed.
	 * 
	 * @author Rory Fisher
	 *
	 */
	private class SnapshotWindowListner implements ActionListener {

		/**
		 * The windows we can take a snapshot at.
		 */
		private Window[] supportedWindows = { Window.hours, Window.days, Window.months };

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String[] Options = { "Hours", "Days", "Months" };
			int selected = -1;
			// Prompt the user for the timescale they want to
			selected = JOptionPane.showOptionDialog(myWindow,
					"On what timescale would you like to take a snapshot? (Close to cancel)", "Create Snapshot",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
			System.out.println(selected);
			if (selected != -1) {
				int tMinus = -1;
				String input = JOptionPane.showInputDialog(myWindow,
						"How long ago would you like to view? (From 0-24 " + Options[selected] + " ago)", "0");
				// Assume if the user gets here, they want to finish opening the file.
				// Repeat this dialogue until
				while (tMinus < 0 || tMinus > 24) {
					// Scanner lets me check if the string has an int before it crashes the
					// software.
					// I will use it to parse for an int.
					Scanner getInt = new Scanner(input);

					if (getInt.hasNextInt()) {
						tMinus = getInt.nextInt();
					}
					// Close a the scanner, its done it job.
					getInt.close();
				}

				// Open the data at that time as a "Snapshot."
				JFrame snapshotFrame = new JFrame(
						"[SnapShot] Weather data from " + tMinus + " " + Options[selected] + " ago.");
				//
				Map<WeatherType, Double> snapshotData = myStorage.getAllAtTime(supportedWindows[selected], tMinus);
				UpperWeatherPanel snapshotPanel = new UpperWeatherPanel(snapshotData);
				snapshotFrame.add(snapshotPanel);
				snapshotFrame.pack();
				Dimension current = snapshotFrame.getSize();
				snapshotFrame.setSize((int) current.getWidth(), (int) (current.getHeight() * 3));
				snapshotFrame.setVisible(true);
			}
		}

	}

}
