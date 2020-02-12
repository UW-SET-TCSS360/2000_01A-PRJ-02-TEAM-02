package visualizer;

import java.util.Map;

import storage.WeatherType;

/**
 * An item that accepts data from the standardized weather storage module.
 */
public interface WeatherDataItem {
	
	/**Updates the data and display labels.
	 * @param A map of "Weather data types" to the newest data values. 
	 * May be partially filled, in which case only the correct display elements will be updated.
	 */
	public void updateData(Map<WeatherType, Double> someData);
		
	
}
