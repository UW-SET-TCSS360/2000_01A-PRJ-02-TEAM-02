package visualizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import storage.WeatherType;

public class WeatherPanel extends JPanel implements WeatherDataItem {

	private static final long serialVersionUID = 1L;

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
	
	private String assetDirectory = "./src/visualizer/";
	private String windDirectionFile = assetDirectory + "wind_north.png";
	
	
	/***********************************************/
	
	public WeatherPanel(Map<WeatherType, Double> theData) {
		myData = new HashMap<>();
		myData.putAll(theData);
		this.populatePanel();
		//setupDisplayBits();
	}

	
	@Override
	public void updateData(Map<WeatherType, Double> newData) {
		myData.put(WeatherType.windchill, newData.get(WeatherType.windchill));
		//First, update the data on windchill, as it is not supported by its own UI panel, and is instead a sub-element of another panel.
		for (int i = 0; i < supportedTypes.length; i++) {
			WeatherType type = supportedTypes[i]; // Get the Datatype.
			if (newData.containsKey(type)) { // See if it was included in the list of updated data.
				myData.put(type, newData.get(type)); // Update our local copy of the data.
				String toSet = getTypeString(i);
				myDisplayElements.get(type).setText(toSet); // Update the text of our display elements.
				// Only if we have new data.
			}
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
	
	 /* Takes an angle that is in degrees, and returns a direction starting from straight north, and rotating degrees clockwise.
	 * AKA, the "Degrees from north" method.
	 * @param theDegree A double representing a value that is between from 0 to 360.
	 * @return A string describing the direction the given angle points in.
	 */
	private String getDirection(Double theDegree) {
		if (theDegree<0 || theDegree>360)
			throw new IllegalArgumentException();
		String toReturn;
		if (theDegree<30 && theDegree>=0 || theDegree>=330 && theDegree <=360) {
			toReturn="North";
			windDirectionFile = assetDirectory + "wind_north.png";
		} else if (theDegree>=30 && theDegree<60) {
			windDirectionFile = assetDirectory + "wind_northeast.png";
			toReturn = "North-East";
		} else if (theDegree>=60 && theDegree<120) {
			windDirectionFile = assetDirectory + "wind_east.png";
			toReturn = "East";
		} else if (theDegree>=120 && theDegree<150) {
			windDirectionFile = assetDirectory + "wind_southeast.png";
			toReturn = "South-East";
		}	else if (theDegree>=150 && theDegree<210) {
			windDirectionFile = assetDirectory + "wind_south.png";
			toReturn = "South";
		} else if (theDegree>=210 && theDegree<240) {
			windDirectionFile = assetDirectory + "wind_southwest.png";
			toReturn = "South-West";
		}	else if (theDegree>=240 && theDegree<300) {
			windDirectionFile = assetDirectory + "wind_west.png";
			toReturn = "West";
		} else {
			windDirectionFile = assetDirectory + "wind_northwest.png";
			toReturn = "NorthWest";
		}
		return toReturn;
	}
	

	
	public String toString() {
		StringBuilder result = new StringBuilder();
		Set<WeatherType> weatherSet = myData.keySet();
		for (WeatherType key : weatherSet) {
			result.append("Key: " + key + " ||||  Value is " + myData.get(key) + "\n");
		}
		return result.toString();
	}
	

	
	private void populatePanel() {
    	this.setForeground(SystemColor.textHighlight);
        //this.setBorder(new LineBorder(Color.BLACK, 4, true));
        this.setBackground(Color.WHITE);
        this.setBounds(22, 85, 483, 354); //same dimensions used in Cynthia's display panel
        this.setLayout(null);

        //Inside temperature static text
        JLabel temperatureLabel = new JLabel("TEMPERATURE");
        temperatureLabel.setForeground(Color.BLACK);

        temperatureLabel.setBounds(365, 0, 100, 20);
        this.add(temperatureLabel);
        
        //Inside temperature static text
        JLabel inside = new JLabel("---INSIDE---");
        inside.setForeground(Color.BLACK);
        inside.setFont(new Font("Tahoma", Font.PLAIN, 12));
        inside.setBounds(250, 48, 64, 14);
        this.add(inside);
        
        //Outside temperature static text
        JLabel lbloutside = new JLabel("---OUTSIDE---");
        lbloutside.setForeground(Color.BLACK);
        lbloutside.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbloutside.setBounds(360, 48, 82, 14);
        this.add(lbloutside);
        
        //Indoor temp static text
        JLabel insideTempLabel = new JLabel(Double.toString(myData.get(WeatherType.temp)) + "°F");
        insideTempLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        insideTempLabel.setForeground(Color.GREEN);
        insideTempLabel.setBounds(250, 20, 100, 35);
        this.add(insideTempLabel); 
        
        //Outside temp dynamic
        JLabel outsideTempLabel = new JLabel(Double.toString(myData.get(WeatherType.outtemp)) + "°F");
        outsideTempLabel.setForeground(Color.GREEN);
        outsideTempLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        outsideTempLabel.setBounds(360, 20, 100, 35);
        this.add(outsideTempLabel); 
        
        //INDOOR HUMIDITY static
        JLabel humidityPercent = new JLabel("25%");
        humidityPercent.setFont(new Font("Tahoma", Font.PLAIN, 20));
        humidityPercent.setForeground(Color.GREEN);
        humidityPercent.setBounds(480, 48, 46, 25);
        this.add(humidityPercent);
        
        //OUTDOOR HUMIDITY dynamic
        JLabel label = new JLabel(myData.get(WeatherType.outhumidity) + "%");
        label.setForeground(Color.GREEN);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setBounds(565, 50, 46, 25);
        this.add(label);
        
        //Pressure static label
        JLabel pressureLabel = new JLabel("PRESSURE");
        pressureLabel.setForeground(Color.BLACK);
        pressureLabel.setBounds(380, 80, 82, 14);
        this.add(pressureLabel);
        
        //Pressure dynamic
        JLabel barometricPressureLabel = new JLabel(myData.get(WeatherType.barometric) + " inHg");
        barometricPressureLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        barometricPressureLabel.setForeground(Color.GREEN);
        barometricPressureLabel.setBounds(335, 100, 125, 25);
        this.add(barometricPressureLabel);
        
        //HUMIDITY static text
        JLabel humidityLabel = new JLabel("HUMIDITY");
        humidityLabel.setForeground(Color.BLACK);
        humidityLabel.setBounds(390, 135, 82, 14);
        this.add(humidityLabel);
        
        //HUMIDITY INSIDE dynamic
        JLabel indoorHumidityLabel = new JLabel(myData.get(WeatherType.humidity) + "%");
        indoorHumidityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        indoorHumidityLabel.setForeground(Color.GREEN);
        indoorHumidityLabel.setBounds(250, 155, 125, 25);
        this.add(indoorHumidityLabel);
        
        JLabel insideHum = new JLabel("---INSIDE---");
        insideHum.setForeground(Color.BLACK);
        insideHum.setFont(new Font("Tahoma", Font.PLAIN, 12));
        insideHum.setBounds(250, 180, 100, 14);
        this.add(insideHum);
        
        JLabel outdoorHumidityLabel = new JLabel(myData.get(WeatherType.outhumidity) + "%");
        outdoorHumidityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        outdoorHumidityLabel.setForeground(Color.GREEN);
        outdoorHumidityLabel.setBounds(360, 155, 125, 25);
        this.add(outdoorHumidityLabel);
        
        JLabel outsideHum = new JLabel("---OUTSIDE---");
        outsideHum.setForeground(Color.BLACK);
        outsideHum.setFont(new Font("Tahoma", Font.PLAIN, 12));
        outsideHum.setBounds(360, 180, 100, 14);
        this.add(outsideHum);
        
        
        //Rainfall dynamic
        JLabel rainfallLabel = new JLabel(myData.get(WeatherType.rain) + " in");
        rainfallLabel.setForeground(Color.GREEN);
        rainfallLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rainfallLabel.setBounds(520, 157, 76, 25);
        this.add(rainfallLabel);
        
      
        
        //static text for rain info
        JLabel rainTextLabel = new JLabel("RAINFALL");
        rainTextLabel.setForeground(Color.BLACK);
        rainTextLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        rainTextLabel.setBounds(380, 140, 160, 160);
        this.add(rainTextLabel);
        
        JLabel rainAccumulated = new JLabel("ACCUMULATED");
        rainAccumulated.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rainAccumulated.setBounds(250, 205, 160, 120);
        this.add(rainAccumulated);
        
        JLabel rainRate = new JLabel("--OVER 24Hr--");
        rainRate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rainRate.setBounds(368, 205, 160, 120);
        this.add(rainRate);
        
        //dynamic text for rainfall
       // JLabel rainFall = new JLabel(myData
        JLabel rainFallLabel = new JLabel(myData.get(WeatherType.rain) + " in");
        rainFallLabel.setForeground(Color.GREEN);
        rainFallLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rainFallLabel.setBounds(250, 185, 160, 120);
        this.add(rainFallLabel);
        
        JLabel rainFallRateLabel = new JLabel(myData.get(WeatherType.rainRate) + " in");
        rainFallRateLabel.setForeground(Color.GREEN);
        rainFallRateLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        rainFallRateLabel.setBounds(370, 185, 160, 120);
        this.add(rainFallRateLabel);
        
      //Static text for wind info below
        JLabel windTextLabel = new JLabel("WIND SPEED & DIRECTION");
        windTextLabel.setForeground(Color.BLACK);
        windTextLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        windTextLabel.setBounds(20, 0, 180, 20);
        this.add(windTextLabel); 
        
        //Text displaying wind speed
        JLabel windSpeedTextLabel = new JLabel(myData.get(WeatherType.wind) + " MPH");
        windSpeedTextLabel.setForeground(Color.GREEN);
        windSpeedTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        windSpeedTextLabel.setBounds(50, 20, 160, 20);
        this.add(windSpeedTextLabel); 
        
        //Text for Wind Direction
        JLabel windDirTextLabel = new JLabel("From " + getDirection(myData.get(WeatherType.winddir)));
        windDirTextLabel.setForeground(Color.GREEN);
        windDirTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        windDirTextLabel.setBounds(50, 170, 160, 20);
        this.add(windDirTextLabel); 
        
        
        //wind chill static
        JLabel windChillText = new JLabel("WIND CHILL");
        windChillText.setForeground(Color.BLACK);
        windChillText.setFont(new Font("Tahoma", Font.BOLD, 12));
        windChillText.setBounds(20, 200, 180, 20);
        this.add(windChillText); 
        
        //wind chill dynamic
        JLabel windChillLabel = new JLabel(myData.get(WeatherType.windchill) + "°F");
        windChillLabel.setForeground(Color.GREEN);
        windChillLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        windChillLabel.setBounds(20, 230, 160, 20);
        this.add(windChillLabel); 
         
        //WIND IMAGE
        ImageIcon windImage = new ImageIcon(windDirectionFile);
        JLabel windLabel = new JLabel();
        windLabel.setLayout(null);
        windLabel.setSize(40, 40);
        windLabel.setBackground(Color.RED);
       
        windLabel.setBounds(10, 30, 150, 150);
        windLabel.setIcon(windImage);
        windLabel.setVisible(true);
        this.add(windLabel);
        
        /*
        //Forecast dynamic text
        JLabel tempOutLabel = new JLabel(forecast.toString().toUpperCase());
        tempOutLabel.setForeground(Color.GREEN);
        tempOutLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tempOutLabel.setBounds(520, 193, 250, 35);
        this.add(tempOutLabel); 
        
       
        //24hr temp change static text
        JLabel changelabel = new JLabel("24hr CHANGE:");
        changelabel.setForeground(Color.GREEN);
        changelabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        changelabel.setBounds(520, 250, 157, 35);
        this.add(changelabel);
        
        //24hr temp change dynamic text
        JLabel changeInWeatherLabel = new JLabel(Double.toString(this.temperature - this.yesterdayTemp) + "°F");
        changeInWeatherLabel.setForeground(Color.GREEN);
        changeInWeatherLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        changeInWeatherLabel.setBounds(520, 274, 124, 35);
        this.add(changeInWeatherLabel); */
	}	
}
