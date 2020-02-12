/**
 * WeatherStation junit test
 * 
 * @author Bob Schmitz
 * @version 1-25-2020
 */

package tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import VantageVue.WeatherStation;

public class WeatherStationTest {
	
	/** Setup WeatherStation object **/
	private WeatherStation myWeatherStation;

    /**
     * A method to initialize the test fixture before each test.
     */
	@BeforeEach
	public void setup() {
		myWeatherStation = new WeatherStation();
	}
	
	/**
	 * Test initialization variables
	 */
	@Test
	public void testInitialize() {
		assertEquals(0, myWeatherStation.getHumidity());
		assertEquals("No rain", myWeatherStation.getRain());
		assertEquals("Rising", myWeatherStation.getBarometer());
		assertEquals(0, myWeatherStation.getWindSpeed());
		assertEquals(0, myWeatherStation.getCurrentTemp());
		assertEquals(0, myWeatherStation.getYesterdayTemp());
		assertEquals(0, myWeatherStation.getTempChange());
	}
	
	/**
	 * Test updateMoonPhase and getMoonPhase cases
	 */
	@Test
	public void testMoonPhase() {
		for (int i=0; i < 10; i++) {
			assertEquals("New Moon", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("Waxing Crecent", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("First Quarter", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("Waxing Gibbous", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("Full Moon", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("Wanning Gibbous", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("Third Quarter", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
			assertEquals("Wanning Crecent", myWeatherStation.getMoonPhase());
			myWeatherStation.updateMoonPhase();
		}
	}

	/**
	 * Test updateHumidity and getHumidity, must be between 0 to 99
	 */
	@Test
	public void testHumidity() {
		for (int i=0; i < 1000; i++) {
			myWeatherStation.updateHumidity();
			assertEquals(49.5, myWeatherStation.getHumidity(), 49.5);
		}
	}
	
	/**
	 * Test updateRain and getRain cases
	 */
	@Test
	public void testRain() {
		String rainStatus;
		for (int i=0; i < 50; i++) {
			myWeatherStation.updateRain();
			rainStatus=myWeatherStation.getRain();
			if (rainStatus != "No rain" &&
				rainStatus != "Some rain" &&
				rainStatus != "Heavy rain") {
				fail("Detected an invalid rain status! (" + rainStatus +")");
			}
		}
	}
	
	/**
	 * Test updateBarometer and getBarometer cases
	 */
	@Test
	public void testBarometer() {
		String barometerStatus;
		for (int i=0; i < 50; i++) {
			myWeatherStation.updateBarometer();
			barometerStatus=myWeatherStation.getBarometer();
			if (barometerStatus != "Rising" &&
				barometerStatus != "Falling" &&
				barometerStatus != "Stable") {
				fail("Detected an invalid barometer status! (" + barometerStatus +")");
			}
		}
	}
	
	/**
	 * Test updateWindSpeed and getWindSpeed, must be between 0 to 35
	 */
	@Test
	public void testWindSpeed() {
		for (int i=0; i < 1000; i++) {
			myWeatherStation.updateWindSpeed();
			assertEquals(17.5, myWeatherStation.getWindSpeed(), 17.5);
		}
	}
	
	/**
	 * Test updateTemp and getTemp, must be between -20 to 110
	 */
	@Test
	public void testGetTemp() {
		int[] tempArray;
		for (int i=0; i < 100; i++) {
			myWeatherStation.updateTemp();
			tempArray = myWeatherStation.getTemp();
			for (int theTemp : tempArray) {
				assertEquals(45, theTemp, 65);	
			}
		}
	}
	
	/**
	 * Test updateTemp and getCurrentTemp, must be between -20 to 110
	 */
	@Test
	public void testCurrentTemp() {
		for (int i=0; i < 1000; i++) {
			myWeatherStation.updateTemp();
			assertEquals(45, myWeatherStation.getCurrentTemp(), 65);
		}
	}
	
	/**
	 * Test updateTemp and getYesterdayTemp, must be between -20 to 110
	 */
	@Test
	public void testYesterdayTemp() {
		for (int i=0; i < 1000; i++) {
			myWeatherStation.updateTemp();
			assertEquals(45, myWeatherStation.getYesterdayTemp(), 65);
		}
	}
	
	/**
	 * Test updateTemp and getTempChange, must be between -130 to 130
	 */
	@Test
	public void testTempChange() {
		for (int i=0; i < 1000; i++) {
			myWeatherStation.updateTemp();
			assertEquals(0, myWeatherStation.getTempChange(), 130);
		}
	}
}

