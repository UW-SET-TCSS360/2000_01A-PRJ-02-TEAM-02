/*
 * Project 1: Prepared for TCSS360 
 * By: Rory Fisher, Bree S. Dinish-Lomelli, Elias Hanna Salmo, Geoffrey Thomas Woulf, Kero Adib.
 */

//package storage;
//
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
///**
// * This class tests the storage class
// * @author eliassalmo
// *
// */
//
//@RunWith(JUnit4.class)
//class StorageTest {
//	
//	/**
//	 * The Storage used in all test cases.
//	 */
//	private Storage myStorage;
//	private Storage myStorage2;
//	
//	/**
//	 * set up method that initializes all of the values of the different temperatures
//	 * @throws Exception
//	 */
//	@BeforeEach
//	void setUp() throws Exception {
//		myStorage = new Storage();
//		myStorage.add(WeatherType.temp, 90.7);
//		myStorage.add(WeatherType.outtemp, 80);
//		myStorage.add(WeatherType.humidity, 35);
//		myStorage.add(WeatherType.rain, 50);
//		myStorage.add(WeatherType.rainRate, 15);
//		myStorage.add(WeatherType.wind, 10);
//		myStorage.add(WeatherType.barometric, 1);
//		myStorage.add(WeatherType.outhumidity, 30);
//		myStorage.add(WeatherType.windchill, 20);
//		myStorage.add(WeatherType.winddir, 10);
//		HashMap<WeatherType, Double[]> hours = new HashMap<WeatherType, Double[]>();
//		Double[] dTemp = {80.0,73.0};
//		Double[] dOut = {85.0,53.0};
//		Double[] dHum = {30.0,48.0};
//		Double[] dRain = {10.0,60.5};
//		Double[] dRRate = {35.0,12.0};
//		Double[] dWind = {10.0,17.0};
//		Double[] dBor = {1.0,1.5};
//		Double[] dOutH = {58.0,47.0};
//		Double[] dWChill = {56.0,15.0};
//		Double[] dWDir = {34.0,10.0};
//		hours.put(WeatherType.temp, dTemp);
//		hours.put(WeatherType.outtemp, dOut);
//		hours.put(WeatherType.humidity, dHum);
//		hours.put(WeatherType.rain, dRain);
//		hours.put(WeatherType.rainRate, dRRate);
//		hours.put(WeatherType.wind, dWind);
//		hours.put(WeatherType.barometric, dBor);
//		hours.put(WeatherType.outhumidity, dOutH);
//		hours.put(WeatherType.windchill, dWChill);
//		hours.put(WeatherType.winddir, dWDir);
//		HashMap<WeatherType, Double[]> days = new HashMap<WeatherType, Double[]>();
//		days.put(WeatherType.temp, dTemp);
//		days.put(WeatherType.outtemp, dOut);
//		days.put(WeatherType.humidity, dHum);
//		days.put(WeatherType.rain, dRain);
//		days.put(WeatherType.rainRate, dRRate);
//		days.put(WeatherType.wind, dWind);
//		days.put(WeatherType.barometric, dBor);
//		days.put(WeatherType.outhumidity, dOutH);
//		days.put(WeatherType.windchill, dWChill);
//		days.put(WeatherType.winddir, dWDir);
//		HashMap<WeatherType, Double[]> months = new HashMap<WeatherType, Double[]>();
//		months.put(WeatherType.temp, dTemp);
//		months.put(WeatherType.outtemp, dOut);
//		months.put(WeatherType.humidity, dHum);
//		months.put(WeatherType.rain, dRain);
//		months.put(WeatherType.rainRate, dRRate);
//		months.put(WeatherType.wind, dWind);
//		months.put(WeatherType.barometric, dBor);
//		months.put(WeatherType.outhumidity, dOutH);
//		months.put(WeatherType.windchill, dWChill);
//		months.put(WeatherType.winddir, dWDir);
//		
//		myStorage2 = new Storage(hours, days, months);
//		
//	}
//	
//	/**
//	 * this method tests that the temp is set correctly
//	 */
//	@Test
//	void testTemp() {
//		double[] test = myStorage.getHistory(WeatherType.temp, Window.current, true);
//		double[] test2 = myStorage2.getHistory(WeatherType.temp, Window.hours, true);
//		assertEquals(90.7, test[0]);
//		assertEquals(80.0, test2[test2.length - 2]);
//	}
//	
//	/**
//	 * this method tests that the outtemp is set correctly
//	 */
//	@Test
//	void testOutTemp() {
//		double[] test = myStorage.getHistory(WeatherType.outtemp, Window.current, true);
//		double[] test2 = myStorage2.getHistory(WeatherType.outtemp, Window.hours, true);
//		assertEquals(80, test[0]);
//		assertEquals(85.0, test2[test2.length - 2]);
//	}
//	
//	/**
//	 * This method tests if the humidity was setup correctly
//	 */
//	@Test
//	void testHumidity() {
//		double[] test = myStorage.getHistory(WeatherType.humidity, Window.current, true);
//		assertEquals(35, test[0]);
//	}
//	
//	/**
//	 * This method tests if the rain was setup correctly
//	 */
//	@Test
//	void testRain() {
//		double[] test = myStorage.getHistory(WeatherType.rain, Window.current, true);
//		assertEquals(50, test[0]);
//	}
//	
//	/**
//	 * This method tests if the rain rate was setup correctly
//	 */
//	@Test
//	void testRainRate() {
//		double[] test = myStorage.getHistory(WeatherType.rainRate, Window.current, true);
//		assertEquals(15, test[0]);
//	}
//	
//	/**
//	 * This method tests if the wind was setup correctly
//	 */
//	@Test
//	void testWind() {
//		double[] test = myStorage.getHistory(WeatherType.wind, Window.current, true);
//		assertEquals(10, test[0]);
//	}
//	
//	/**
//	 * This method tests if the barometric was setup correctly
//	 */
//	@Test
//	void testbarometric() {
//		double[] test = myStorage.getHistory(WeatherType.barometric, Window.current, true);
//		assertEquals(1, test[0]);
//	}
//	
//	/**
//	 * this method tests that the temp is set correctly
//	 */
//	@Test
//	void testOutHumidity() {
//		double[] test = myStorage.getHistory(WeatherType.outhumidity, Window.current, true);
//		assertEquals(30, test[0]);
//	}
//	
//	/**
//	 * this method tests that the temp is set correctly
//	 */
//	@Test
//	void testWindChill() {
//		double[] test = myStorage.getHistory(WeatherType.windchill, Window.current, true);
//		assertEquals(20, test[0]);
//	}
//	
//	/**
//	 * this method tests that the temp is set correctly
//	 */
//	@Test
//	void testWindDir() {
//		double[] test = myStorage.getHistory(WeatherType.winddir, Window.current, true);
//		assertEquals(10, test[0]);
//	}
//	
//	/**
//	 * This method test the getAllTheTime method and checks if the value I added to myStorage were correct
//	 */
//	@Test
//	void testgetAllTheTime() {
//		Map<WeatherType, Double> test = myStorage.getAllAtTime(Window.current, 0);
//		Map<WeatherType, Double> expected = new HashMap<WeatherType, Double>();
//		expected.put(WeatherType.temp, 90.7);
//		expected.put(WeatherType.outtemp, 80.0);
//		expected.put(WeatherType.humidity, 35.0);
//		expected.put(WeatherType.rain, 50.0);
//		expected.put(WeatherType.rainRate, 15.0);
//		expected.put(WeatherType.wind, 10.0);
//		expected.put(WeatherType.barometric, 1.0);
//		expected.put(WeatherType.outhumidity, 30.0);
//		expected.put(WeatherType.windchill, 20.0);
//		expected.put(WeatherType.winddir, 10.0);
//		assertEquals(test, expected);
//	}
//	
//	 @Test 
//	void testAllTheException()  {
//		Assertions.assertThrows(IllegalArgumentException.class, () -> myStorage.getAllAtTime(Window.current, 25));
//		
//	}
//
//}
