/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * Provides helpful information to users of the outdoor thermostat software when
 * displayed.
 * 
 * @author Rory Fisher
 *
 */
public class HelpWindow extends JFrame {

	/**
	 * A generated serial ID.
	 */
	private static final long serialVersionUID = 6811678411129701012L;

	/**
	 * Initializes a new help window.
	 */
	public HelpWindow() {
		super("Help/About/Manual");
		JTabbedPane helpTabs = new JTabbedPane();
		this.add(helpTabs);
		helpTabs.add(mainWindowHelp(), "About/What am I seeing?");
		helpTabs.add(snapshotHelp(), "About/SnapShots");
		helpTabs.add(simulatorInfo(), "About/The Simulator");
		this.pack();
	}

	/**
	 * Provides text field that gives a description of the simulator.
	 * 
	 * @return A text field that gives a description of the simulator.
	 */
	private Component simulatorInfo() {
		JTextArea simulatorAbout = new JTextArea();
		simulatorAbout.append("The simulator is largely a random force currently. \n"
				+ "However it it uses its previous outputs to inform its next inputs.\n" + "\n"
				+ "The exception to this is windchill, which is only different from the current\n"
				+ "temperature when the tempature is below 50 *f.\n" + "\n"
				+ "The rain is also an exception to certain rules, as the rain is not random.\n"
				+ "When the rain is calculated over an hour, or a current window, it is actually\n"
				+ "based on a 'rain rate' that is tracked, which in turn is based on outdoor humidity.\n" + "\n"
				+ "You don't necessarily need to know this, but it might be interesting to know\n"
				+ "why the interface is showing you specific patterns that seem unusual or structured.");
		return simulatorAbout;
	}

	/**
	 * Gets a panel that provides an adequate description of the snapshot feature.
	 * 
	 * @return A text area/panel that provides a description of the main windows
	 *         feature.
	 */
	private JTextArea snapshotHelp() {
		JTextArea snapshotTutorial = new JTextArea();
		snapshotTutorial.append("The snapshot feature is a feature that allows\n"
				+ "a user to get more detailed information on the weather\n"
				+ "data at points present on the persistant graphs.\n" + "\n"
				+ "When the snapshot button is clicked, a prompt will ask you\n"
				+ "about what timescale are you interested in seeing data on,\n"
				+ "if you exit out of this prompt without making a selection, \n"
				+ "it will cancel out of calling the snapshot feature.\n" + "\n"
				+ "The next prompt will ask you to input a number between\n"
				+ "0 and 24, invalid responses will close the current\n"
				+ "window and open a new one. We assume that if you reach\n"
				+ "this prompt, you want to open a new snapshot.\n" + "\n"
				+ "From here, a window resembling the upper panel of the main window\n"
				+ "will be generated. You may close these snapshot windows. without\n"
				+ "adversely affecting the functionality of this software.\n" + "\n"
				+ "A final note is that the Snapshots do not take into account variables\n"
				+ "such as cloud cover, sunrise, sunset, or the moon phase.");
		return snapshotTutorial;
	}

	/**
	 * Gets a panel that provides an adequate description of the main window.
	 * 
	 * @return A text area/panel that provides a description of the main windows
	 *         features.
	 */
	private JTextArea mainWindowHelp() {
		JTextArea myTutorial = new JTextArea();
		myTutorial.append("Welcome to the first version out OUTDOORSTHERMOSTAT, \n"
				+ "This is the help menu, where interactive features will be explained. \n"
				+ "Here we will be going over the default window, which serves as \n"
				+ "moderately interactive display that shows us both the live feed \n"
				+ "of our newest data which refreshes every 2.5 seconds.\n" + "\n"
				+ "The lower panel, which consists of a sequence of tabs is \n"
				+ "a long term data viewer. The lower panel currently does not\n"
				+ "support live updates. However, it is still functional in that we can see\n"
				+ "our 'long term' data displayed as it would be on the vantage vue.\n" + "\n"
				+ "Currently all data availible is simulated, as such, the long\n"
				+ "term data will be persistant across a single session but it will\n"
				+ "not be persistant across multiple sessions.");
		return myTutorial;
	}
}
