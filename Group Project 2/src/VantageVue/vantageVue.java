/**
 * Class to create the GUI for the displaying of information from the Vantage Vue Weather Station.
 * @author Elham Jmaileh
 * @author Cynthia Pang 
 *
 */

package VantageVue;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JRadioButton;
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
	private WeatherStation w = new WeatherStation();
    /** Radio button for the home tab*/
	private JRadioButton homeButton;
    /** Radio button for graphs tab*/
	private JRadioButton graphsButton;
	 /** Radio button for weather center tab*/
	private JRadioButton weatherButton;
	 /** Radio button for moon phase tab*/
	private JRadioButton moonPhaseButton;
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
	/** Initializing the clock values
	 * 
	 */
	public void clock() {

		Thread clock = new Thread() {
			public void run() {
				try {
					for(;;) {
						Calendar cal = new GregorianCalendar();	
						int second = cal.get(Calendar.SECOND);
						int minute = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
						String curTime = String.format("%02d:%02d:%02d", hour, minute,second);

						clockLabel.setText("Time:  "+curTime);

						sleep(1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	/** Initializing the date values
	 * 
	 */
	public void date() {

		Thread date = new Thread() {
			public void run() {
				try {
					for(;;) {
						Calendar cal = new GregorianCalendar();
						int month = 01;
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int year = cal.get(Calendar.YEAR);
						String curTime = String.format("%02d/%02d/%02d", month, day, year);
						dateLabel.setText("Date:  "+curTime);

						sleep(1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		date.start();
	}

	/**
	 * Creating the application (Constructor).
	 */
	public vantageVue() {
		initialize();
		clock();
		date();
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

		moonPhaseButton = new JRadioButton("Moon Phase");
		moonPhaseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		moonPhaseButton.setBackground(Color.LIGHT_GRAY);
		moonPhaseButton.setBounds(530, 188, 150, 40);
		jFrame.getContentPane().add(moonPhaseButton);

		/**Add action listeners to all of the buttons which will update their panels when clicked on */
		graphsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
				panel.add(labelGraph);
				panel.add(graph);
				panel.repaint();
			}
		});

		weatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
				panel.add(labelWeatherCenter);
				panel.add(labelHumid);
				panel.add(labelTemperature);
				panel.add(labelYesTemp);
				panel.add(labelChange);
				panel.add(labelRain);
				panel.add(labelWind);
				panel.add(labelBarometer);
				panel.add(labelAlarm);
				panel.repaint();
			}
		});

		moonPhaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
				panel.add(labelMoonPhase);
				panel.add(moonPhaseImage);
				panel.repaint();
			}
		});

	
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.updateUI();
				panel.add(clockLabel);
				panel.add(dateLabel);
				panel.add(labelWelcome);
				panel.add(labelTemp);
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
		G.add(moonPhaseButton);
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

		/** clock label to be placed at home tab */
		clockLabel = new JLabel("clock");
		clockLabel.setBounds(20, 140, 138, 79);
		clockLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(clockLabel);

		/** date label to be placed at home tab */
		dateLabel = new JLabel("date");
		dateLabel.setBounds(20, 90, 217, 79);
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(dateLabel);

		/** welcome label to be placed at home tab */
		labelWelcome = new JLabel("Welcome to Davis's Vantage Vue");
		labelWelcome.setBounds(10, 28, 399, 53);
		labelWelcome.setFont(new Font("Tahoma", Font.PLAIN, 23));
		panel.add(labelWelcome);

		/** weather center title label to be placed at weather center tab */
		labelWeatherCenter = new JLabel("Weather Center");
		labelWeatherCenter.setFont(new Font("Tahoma", Font.PLAIN, 23));
		labelWeatherCenter.setBounds(10, 1,500,50);

		/**temperature label to be placed at home tab */
		w.updateTemp();
		int temp = w.getCurrentTemp();
		labelTemp = new JLabel("Current Temperature: "+ temp+"\u00B0"+"F");
		labelTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelTemp.setBounds(20, 170, 240, 150);
		panel.add(labelTemp);

		/** temperature label to be placed at weather center tab */
		labelTemperature = new JLabel("Current Temperature: "+ temp+ "\u00B0"+"F");
		labelTemperature.setBounds(30,5 , 209, 200);
		labelTemperature.setFont(new Font("Tahoma", Font.PLAIN, 16));


		/** humidity label to be placed at weather center tab */
		w.updateHumidity();
		int humidity = w.getHumidity();
		labelHumid = new JLabel("Current Humidity: "+ humidity + "%");
		labelHumid.setBounds(30,30,209,200);
		labelHumid.setFont(new Font("Tahoma", Font.PLAIN, 16));

		/** yesterday's temperature label to be placed at weather center tab */
		int yestTemp = 	w.getYesterdayTemp();
		labelYesTemp = new JLabel("Yesterday's Temperature: "+ yestTemp+"\u00B0"+"F");
		labelYesTemp.setBounds(30,55,400,200);
		labelYesTemp.setFont(new Font("Tahoma", Font.PLAIN, 16));

		/**change in temperature label to be placed at weather center tab */
		int change = 	w.getTempChange();
		labelChange = new JLabel("Change in Weather Since Yesterday: "+ change+"\u00B0"+"F");
		labelChange.setBounds(30,80,400,200);
		labelChange.setFont(new Font("Tahoma", Font.PLAIN, 16));

		/** rain label to be placed at weather center tab */
		w.updateRain();
		String rain = 	w.getRain();
		labelRain = new JLabel("Expectation of Rain: "+ rain);
		labelRain.setBounds(30,105,400,200);
		labelRain.setFont(new Font("Tahoma", Font.PLAIN, 16));

		/** wind speed label to be placed at weather center tab */
		w.updateWindSpeed();
		int wind = 	w.getWindSpeed();
		labelWind = new JLabel("Wind Speed: "+ wind +" mph");
		labelWind.setBounds(30,130,400,200);
		labelWind.setFont(new Font("Tahoma", Font.PLAIN, 16));

		/** Barometer label to be placed at weather center tab */
		w.updateBarometer();
		String barometric = w.getBarometer();
		labelBarometer = new JLabel("Status of Barometric Pressure: "+barometric);
		labelBarometer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelBarometer.setBounds(30, 155,500,200);

		/** Alarm label to be placed at weather center tab */
		labelAlarm = new JLabel("You will be alarmed in severe weather cases.");
		labelAlarm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelAlarm.setBounds(30, 180,500,200);

		/** MoonPhase title to be placed at moon phase tab */
		labelMoonPhase = new JLabel("Moon Phase");
		labelMoonPhase.setFont(new Font("Tahoma", Font.PLAIN, 23));
		labelMoonPhase.setBounds(10, 1,500,50);

		/** Moon Phase image to be placed at moon phase tab*/
		w.updateMoonPhase();
		String moonPhase = w.getMoonPhase();
		moonPhaseImage = new JLabel("");
		moonPhaseImage.setBackground(Color.WHITE);
		moonPhaseImage.setIcon(new ImageIcon(getClass().getResource("/"+moonPhase+".GIF")));
		moonPhaseImage.setBounds(160, 20,400,300);

		/** graphs title label to be placed at graphs tab */
		labelGraph = new JLabel("Graphs");
		labelGraph.setFont(new Font("Tahoma", Font.PLAIN, 23));
		labelGraph.setBounds(10, 1,500,50);

		/** graphs image to be placed at graphs tab */
		graph = new JLabel("");
		graph.setBackground(Color.WHITE);
		graph.setIcon(new ImageIcon(getClass().getResource("/temp.png")));
		graph.setBounds(50, 50,400,300);

	}
}
