/**
 * Class to simulate data from Davis vantage-vue weather station.
 * 
 * @author Trevor 
 * @version 1.0 1-22-2020
 *
 */

package VantageVue;

public class WeatherStation {
	/** int to keep track of the moon phase [0,7] */
	private int moonPhase = 0;
	/** int array to keep track of the temperature. [-20,110] */
	private int[] tempArr = new int[25];
	/** int to keep track of the humidity [0,2] */
	private int humid = 0;
	/** int to keep track of the windSpeed */
	private int windSpeed = 0;
	/** int to keep track of barometric presure */
	private int barometer = 0;
	/** int to keep track of rain amount */
	private int rain = 0;
	
	/**
	 * increases moon phase by 1, and modded by 8
	 */
	public void updateMoonPhase() {
		moonPhase = (moonPhase + 1) % 8;
	}
	
	/**
	 * returns a string of the current moon phase
	 * @return a string of the current moon phase
	 */
	public String getMoonPhase() {
		switch(moonPhase) {
			case 0:
				return "New Moon";
			case 1:
				return "Waxing Crecent";
			case 2:
				return "First Quarter";
			case 3:
				return "Waxing Gibbous";
			case 4:
				return "Full Moon";
			case 5:
				return "Wanning Gibbous";
			case 6:
				return "Third Quarter";
			case 7:
				return "Wanning Crecent";
		}
		throw new RuntimeException("Moon Phase (" + moonPhase + ") out of bounds");
	}
	
	/**
	 * updates the Humidity
	 */
	public void updateHumidity() {
		humid = (int) (Math.random() * 100);
	}
	
	/**
	 * returns an int of the humidity
	 * @return humidity
	 */
	public int getHumidity() {
		return humid;
	}
	
	/**
	 * changes the amount a random number [0,2]
	 */
	public void updateRain() {
		rain = (int) (Math.random() * 3);
	}
	
	/**
	 * returns a String describing how much it is raining
	 * @return a String describing how much it is raining
	 */
	public String getRain() {
		switch(rain) {
		case 0:
			return "No rain";
		case 1:
			return "Some rain";
		case 2:
			return "Heavy rain";
		}
		throw new RuntimeException("reached end of switch case with no return");
	}
	
	/**
	 * changes barometer to a random value [0,2]
	 */
	public void updateBarometer() {
		barometer = (int) (Math.random() * 3);
	}
	
	/**
	 * returns a string describing if the barometric pressure is rising, falling, or stable
	 * @return
	 */
	public String getBarometer() {
		switch(barometer) {
		case 0:
			return "Rising";
		case 1:
			return "Falling";
		case 2:
			return "Stable";
		}
		throw new RuntimeException("reached end of switch case with no return");
	}
	
	/**
	 * updates the windspeed to a random value [0,35]
	 */
	public void updateWindSpeed() {
		windSpeed = (int) (Math.random() * 35);
	}
	
	/**
	 * returns the wind speed
	 * @return the wind speed
	 */
	public int getWindSpeed() {
		return windSpeed;
	}
	
	/**
	 * updates the temperature array by adding another random temperature to the temp array. 
	 * 
	 * array is treated as a queue of size 24, the 25th element
	 * of the array keeps track of where the head of the queue is.
	 */
	public void updateTemp() {
		if (tempArr[23] == 0 && tempArr[24] == 0) {
			tempArr[0] = (int) ((Math.random() * 130) - 20); //max temp = 110
			tempArr[24] = 1;
		}
		else {
			// Force temperature to stay in the range of -20 to 110
			do {
				tempArr[tempArr[24]] = tempArr[(tempArr[24] + 23) % 24] + (int) ((Math.random() * 15) - 7.5);
			} while (tempArr[tempArr[24]] < -20 || tempArr[tempArr[24]] > 110);
			tempArr[24] = (tempArr[24] + 1) % 24;
		}		
	}
	
	/**
	 * returns the temperature array with the most recent value at index 0, and
	 * the least recent temperature at index 24 
	 * @return array of ints
	 */
	public int[] getTemp() {
		int[] temp = new int[24];
		for (int i = 0 ; i <= 23; i++) {
			temp[i] = tempArr[(i + tempArr[24] + 23) % 24];
		}
		return temp;
	}
	/**
	 * returns the current temp
	 * @return the current temp
	 */
	public int getCurrentTemp() {
		return tempArr[(tempArr[24] + 23)% 24];
	}
	
	/**
	 * returns the temp at this time yesterday
	 * @return the temp at this time yesterday
	 */
	public int getYesterdayTemp() {
		return tempArr[tempArr[24]];
	}
	
	/**
	 * returns the change in temp 24hrs ago
	 * @return the change in temp 24hrs ago
	 */
	public int getTempChange() {
		return getCurrentTemp() - getYesterdayTemp();
	}
	
	
	
}