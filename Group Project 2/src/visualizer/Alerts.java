/*

 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

package visualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import storage.WeatherType;

/**
 * A UI that is used to create an alert for 7 different measurements within the
 * WeatherTypes
 * 
 * @author Kero Adib
 */
public class Alerts extends JFrame
{
	/**
	 * A Generated Serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Map Holding the current alarms
	 */
	private Map<WeatherType, Double> currentAlerts;

	/**
	 * Map Holding inequalities
	 */
	private Map<WeatherType, String> comparators;

	/**
	 * JButton used for openning this frame
	 */
	private JButton alarmButton;

	/**
	 * A list that carries the contents for a jlist
	 */
	private DefaultListModel<String> list;

	/**
	 * A constructor that makes the frame and other components within the frame and
	 * does call setInterface()
	 * 
	 */
	public Alerts()
	{
		list = new DefaultListModel<>();
		currentAlerts = new HashMap<>();
		comparators = new HashMap<>();
		alarmButton = new JButton("Alerts");
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				alarmButton.setEnabled(false);
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				alarmButton.setEnabled(true);

			}
		});
		this.setSize(new Dimension(450, 500));
		this.setTitle("Alerts");
		alarmButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Alerts.this.setVisible(true);
			}

		});

		setInterface();
	}

	/**
	 * This Method sets everything up within the frame, with multiple panels, radio
	 * buttons, etc
	 */
	private void setInterface()
	{
		//Initializing everything needed for the UI
		String[] combos = { "≤", "≥", "<", ">" };
		JPanel existingAlerts = new JPanel();
		JButton submit = new JButton("Submit");
		JTextField textField = new JTextField();
		ButtonGroup myGroup = new ButtonGroup();
		JRadioButton myTempIn = new JRadioButton("Inside Temperature"),
				myTempOut = new JRadioButton("Outside Temperature"), myHumIn = new JRadioButton("Indoor Humidity"),
				myHumOut = new JRadioButton("Outdoor Humidity"), myWindSpeed = new JRadioButton("Wind Speed"),
				myRainInches = new JRadioButton("Ammont of Rain(In)"), myBar = new JRadioButton("BAR");
		JComboBox<String> comboBox = new JComboBox<String>(combos);
		JPanel myUpperPanel = new JPanel();
		JPanel myLowerPanel = new JPanel();
		JList<String> jlist = new JList<String>(list);
		JScrollPane listScrollPane = new JScrollPane(jlist);
		
		//Setting up the panels, textField, and jlist to fit with the UI
		textField.setPreferredSize(new Dimension(50, 25));
		textField.setVisible(true);
		textField.setEnabled(true);
		myUpperPanel.setLayout(new GridLayout(4, 2));
		jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jlist.setLayoutOrientation(JList.VERTICAL);
		jlist.setVisibleRowCount(-1);
		existingAlerts.add(listScrollPane);
		listScrollPane.setPreferredSize(new Dimension(200, 160));
		listScrollPane.setVisible(true);
		
		//Adding everything to the panels and the frame
		myGroup.add(myTempIn);
		myGroup.add(myTempOut);
		myGroup.add(myHumIn);
		myGroup.add(myHumOut);
		myGroup.add(myWindSpeed);
		myGroup.add(myRainInches);
		myGroup.add(myBar);
		myTempIn.setSelected(true);
		myUpperPanel.add(myTempIn);
		myUpperPanel.add(myTempOut);
		myUpperPanel.add(myWindSpeed);
		myUpperPanel.add(myHumIn);
		myUpperPanel.add(myHumOut);
		myUpperPanel.add(myRainInches);
		myUpperPanel.add(myBar);
		myLowerPanel.add(textField);
		myLowerPanel.add(comboBox);
		myLowerPanel.add(submit);
		this.add(myUpperPanel, BorderLayout.NORTH);
		this.add(myLowerPanel, BorderLayout.SOUTH);
		this.add(existingAlerts, BorderLayout.CENTER);
		
		//Setting up a button for setting the alerts
		submit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					double cast = Double.parseDouble(textField.getText());
					if (myTempIn.isSelected() && !textField.getText().isEmpty())
					{

						list.addElement(cast + " " + comboBox.getSelectedItem() + " " + WeatherType.temp.toString());
						currentAlerts.put(WeatherType.temp, cast);
						comparators.put(WeatherType.temp, (String) comboBox.getSelectedItem());
					}
					else if (myTempOut.isSelected() && !textField.getText().isEmpty())
					{
						list.addElement(cast + " " + comboBox.getSelectedItem() + " " + WeatherType.outtemp.toString());
						currentAlerts.put(WeatherType.outtemp, cast);
						comparators.put(WeatherType.outtemp, (String) comboBox.getSelectedItem());
					}
					else if (myHumIn.isSelected() && !textField.getText().isEmpty())
					{
						if (cast < 0 || cast > 100)
						{
							throw new NumberFormatException();
						}
						list.addElement(
								cast + " " + comboBox.getSelectedItem() + " " + WeatherType.humidity.toString());
						currentAlerts.put(WeatherType.humidity, cast);
						comparators.put(WeatherType.humidity, (String) comboBox.getSelectedItem());
					}
					else if (myHumOut.isSelected() && !textField.getText().isEmpty())
					{
						if (cast < 0 || cast > 100)
						{
							throw new NumberFormatException();
						}
						list.addElement(
								cast + " " + comboBox.getSelectedItem() + " " + WeatherType.outhumidity.toString());
						currentAlerts.put(WeatherType.outhumidity, cast);
						comparators.put(WeatherType.outhumidity, (String) comboBox.getSelectedItem());
					}
					else if (myWindSpeed.isSelected() && !textField.getText().isEmpty())
					{
						if (cast < 0)
						{
							throw new NumberFormatException();
						}
						list.addElement(cast + " " + comboBox.getSelectedItem() + " " + WeatherType.wind.toString());
						currentAlerts.put(WeatherType.wind, cast);
						comparators.put(WeatherType.wind, (String) comboBox.getSelectedItem());
					}
					else if (myRainInches.isSelected() && !textField.getText().isEmpty())
					{
						if (cast < 0)
						{
							throw new NumberFormatException();
						}
						list.addElement(cast + " " + comboBox.getSelectedItem() + " " + WeatherType.rain.toString());
						currentAlerts.put(WeatherType.rain, cast);
						comparators.put(WeatherType.rain, (String) comboBox.getSelectedItem());
					}
					else if (myBar.isSelected() && !textField.getText().isEmpty())
					{
						if (cast < 0)
						{
							throw new NumberFormatException();
						}
						list.addElement(
								cast + " " + comboBox.getSelectedItem() + " " + WeatherType.barometric.toString());
						currentAlerts.put(WeatherType.barometric, cast);
						comparators.put(WeatherType.barometric, (String) comboBox.getSelectedItem());
					}

				}
				catch (NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid number");
				}
			}

		});
	}

	/**
	 * the check method checks whether if any of the data from info will set off one
	 * of the alarms
	 * 
	 * @param info
	 */
	public void check(Map<WeatherType, Double> info)
	{
		for (int i = 0; i < WeatherType.values().length; i++)
		{
			WeatherType j = WeatherType.values()[i];
			if (currentAlerts.containsKey(j))
			{
				if (comparators.get(WeatherType.values()[i]).equals("<"))
				{
					if (currentAlerts.get(j) < info.get(j))
					{
						JOptionPane.showMessageDialog(new JFrame(), j.toString() + " > " + currentAlerts.get(j));
						list.removeElement(currentAlerts.get(j) + " < " + j.toString());
						currentAlerts.remove(j);
						comparators.remove(j);
					}
				}
				else if (comparators.get(WeatherType.values()[i]).equals(">"))
				{
					if (currentAlerts.get(j) > info.get(j))
					{
						JOptionPane.showMessageDialog(new JFrame(), j.toString() + " < " + currentAlerts.get(j));
						list.removeElement(currentAlerts.get(j) + " > " + j.toString());
						currentAlerts.remove(j);
						comparators.remove(j);
					}
				}
				else if (comparators.get(WeatherType.values()[i]).equals("≤"))
				{
					if (currentAlerts.get(j) <= info.get(j))
					{
						JOptionPane.showMessageDialog(new JFrame(), j.toString() + " ≥ " + currentAlerts.get(j));
						list.removeElement(currentAlerts.get(j) + " ≤ " + j.toString());
						currentAlerts.remove(j);
						comparators.remove(j);
					}
				}
				else if (comparators.get(WeatherType.values()[i]).equals("≥"))
				{
					if (currentAlerts.get(j) >= info.get(j))
					{
						JOptionPane.showMessageDialog(new JFrame(), j.toString() + " ≤ " + currentAlerts.get(j));
						list.removeElement(currentAlerts.get(j) + " ≥ " + j.toString());
						currentAlerts.remove(j);
						comparators.remove(j);
					}
				}
			}
		}
	}

	/**
	 * Returns alarmButton
	 * @return alarmButton
	 */
	public JButton getAlarmButton()
	{
		return alarmButton;
	}

}
