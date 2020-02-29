/**
 * Class to create the GUI for the displaying of information from the Vantage Vue Weather Station.
 * 
 * @author Cynthia Pang
 *
 */

package VantageVueMenu;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import networking.OpenWeatherMap;
import storage.Storage;
import storage.WeatherType;
import visualizer.AlertsPanel;
import visualizer.GraphWrapperPanel;
import visualizer.WeatherDataItem;
import visualizer.WeatherPanel;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vantageVue {
    /** the main JFrame*/
	private JFrame jFrame;
	/** WeatherStation object where data will be generated from*/
//	private WeatherStation w = new WeatherStation();
    /** Radio button for the home tab*/
	private JRadioButton homeButton;
    /** Radio button for graphs tab*/
	private JRadioButton graphsButton;
	 /** Radio button for weather center tab*/
	private JRadioButton weatherButton;
	 /** Radio button for moon phase tab*/
	private JRadioButton alertButton;
	 /** Panel to act as the main console*/
	private JPanel panel;
	 /** Label for the weather center title*/
	private Container labelWeatherCenter;
	 /** clock label*/
	private JLabel clockLabel;
	 /** date label*/
	private JLabel dateLabel;
	 /** Davis logo label*/
	private JLabel DavisLogo;
	 /** Welcome label for home tab*/
	private JLabel labelWelcome;
	 /** Label for the moon phase image*/
	private JLabel moonPhaseImage;
	 /** Home page temperature label*/
	private JLabel labelTemp;
	 /** Humidity label for weather center tab*/
	private JLabel labelHumid;
	 /** Temperature label for weather center tab*/
	private JLabel labelTemperature;
	 /** Barometer label for weather center tab*/
	private JLabel labelBarometer;
	 /** Yesterday's temperature label for weather center tab*/
	private JLabel labelYesTemp;
	 /** Change in temperature label for weather center tab*/
	private JLabel labelChange;
	 /** Rain label for weather center tab*/
	private JLabel labelRain;
	 /** Wind label for weather center tab*/
	private JLabel labelWind;
	 /** moonPhase label for title at moon phase tab*/
	private JLabel labelMoonPhase;
	 /** Alarm label for weather center tab*/
	private JLabel labelAlarm;
	 /** Graph Image for the graphs tab*/
	private JLabel graph;
	 /** Graphs tab title*/
	private JLabel labelGraph;
	/**
	 * The weather panel.
	 */
	private WeatherPanel myWeatherPanel;
	/**
	 * The alert panel.
	 */
	private AlertsPanel myAlertPanel;
	/**
	 * The alert panel.
	 */
	private OpenWeatherMap myWeatherMap;
	/**
	 * A panel that wraps the graphing system, and updates the graphs on an hourly basis.
	 */
	private GraphWrapperPanel myGraphPanel;
	/**
	 * A timer that calls the weather map to update its data, and 
	 */
	private Timer myTimer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vantageVue window = new vantageVue();
					window.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Creating the application (Constructor).
	 */
	public vantageVue() {
		myWeatherMap = new OpenWeatherMap();
		HashMap<WeatherType, Double> theInitialWeather = myWeatherMap.getCurrent(); 
		myWeatherPanel = new WeatherPanel(theInitialWeather);
		myAlertPanel = new AlertsPanel();
		Storage aStorage = new Storage();
		myGraphPanel = new GraphWrapperPanel(aStorage, theInitialWeather);
		myTimer = new Timer(2500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				myWeatherMap.updateCurrent();
				HashMap<WeatherType, Double> theNewWeather = myWeatherMap.getCurrent();
				myWeatherPanel.updateData(theNewWeather);
				myAlertPanel.updateData(theNewWeather);
				myGraphPanel.updateData(theNewWeather); 
			} 
		});
		initialize();
		myTimer.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/** Create main JFrame which is not resizable, with background color grey */
		jFrame = new JFrame();
		jFrame.setResizable(false);
		jFrame.getContentPane().setBackground(Color.GRAY);
		jFrame.getContentPane().setLayout(null);
		DavisLogo = new JLabel("New label");
		DavisLogo.setBackground(Color.WHITE);
		DavisLogo.setIcon(new ImageIcon(getClass().getResource("/Davis.PNG")));
		DavisLogo.setBounds(22, 26, 238, 49);
		jFrame.getContentPane().add(DavisLogo);
		jFrame.setTitle("VantageVue Console");
		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Davis company logo.PNG")));
		jFrame.setBounds(500, 150, 700, 482);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** Create the home, graphs, weather Center, and Moon Phase buttons*/
		homeButton = new JRadioButton("Home");
		homeButton.setSelected(true);
		homeButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		homeButton.setBackground(Color.LIGHT_GRAY);
		homeButton.setForeground(new Color(0, 0, 0));
		homeButton.setToolTipText("Home");
		homeButton.setBounds(530, 85, 150, 40);
		jFrame.getContentPane().add(homeButton);
		
		graphsButton = new JRadioButton("Graphs");
		graphsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		graphsButton.setBackground(Color.LIGHT_GRAY);
		graphsButton.setBounds(530, 399, 150, 40);
		jFrame.getContentPane().add(graphsButton);

		weatherButton = new JRadioButton("Weather Center");
		weatherButton.setBackground(Color.LIGHT_GRAY);
		weatherButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		weatherButton.setBounds(530, 293, 150, 40);
		jFrame.getContentPane().add(weatherButton);

		alertButton = new JRadioButton("Alerts");
		alertButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		alertButton.setBackground(Color.LIGHT_GRAY);
		alertButton.setBounds(530, 188, 150, 40);
		jFrame.getContentPane().add(alertButton);

		/**Add action listeners to all of the buttons which will update their panels when clicked on */
		graphsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
				panel.add(myGraphPanel);
				panel.repaint();
			}
		});

		weatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
					panel.add(myWeatherPanel);
				panel.repaint();
			}
		});

		alertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
					panel.add(myAlertPanel);
				panel.repaint();
			}
		});

	
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
			        panel.add(labelWelcome);
				panel.repaint();
			}
		});

		/**Place all of the buttons into a button group so they are synchronized
		 * (you cannot click on 2 panels at once). Home button is by default selected
		 * first.
		 **/
		ButtonGroup G = new ButtonGroup();
		G.add(graphsButton);
		G.add(weatherButton);
		G.add(alertButton);
		G.add(homeButton);
		
		/** Display Options label placed above the buttons*/
		JLabel lblNewLabel = new JLabel("Display Options");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(530, 26, 150, 40);
		jFrame.getContentPane().add(lblNewLabel);

		/** Initializing the main Panel*/
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(22, 85, 483, 354);
		jFrame.getContentPane().add(panel);
		panel.setLayout(null);

		/** welcome label to be placed at home tab */
		labelWelcome = new JLabel("Welcome to Davis's Vantage Vue");
		labelWelcome.setBounds(10, 28, 399, 53);
		labelWelcome.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel.add(labelWelcome);

	
	}
}
