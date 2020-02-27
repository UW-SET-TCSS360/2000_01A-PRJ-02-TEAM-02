package storage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTests {

	/**
	 * Used to test basic functionality and asserts of the storage and log class.
	 */
	Storage myDefaultStorage;
	/**
	 * Used to test simple functionality of the storage and log class.
	 */
	Storage myCustomStorage;
	/**
	 * Used to test advanced functionality of the storage and log class.
	 */
	Storage myHighLowStorage;
	Double[] someHours = { 65.3, 64.2, 63.1, 61.3, 60.8 };
	Double[] someDays = { 52.0, 49.6, 60.3, 60.5, 60.8 };
	Double[] someMonths = { 70.6, 1.0, 30.2, 26.6, 102.2 };

	@BeforeEach
	public void setup() {
		// Generate the default storage with dummy values.
		myDefaultStorage = new Storage();
		// Generate basic weather data and make a storage that utilizes them.
		HashMap<WeatherType, Double[]> theHours = new HashMap<>();
		HashMap<WeatherType, Double[]> theDays = new HashMap<>();
		HashMap<WeatherType, Double[]> theMonths = new HashMap<>();
		for (WeatherType t : WeatherType.values()) {
			theHours.put(t, someHours);
			theDays.put(t, someDays);
			theMonths.put(t, someMonths);
		}
		// Generate the basic validation storage.
		myCustomStorage = new Storage(theHours, theDays, theMonths);
		// Generate Low values.
		Double[] someHourLows = new Double[5];
		for (int i = 0; i < someHours.length; i++) {
			someHourLows[i] = (someHours[i] - 4.0);
		}
		Double[] someDayLows = new Double[5];
		for (int i = 0; i < someDays.length; i++) {
			someDayLows[i] = (someDays[i] - 4.0);
		}
		Double[] someMonthLows = new Double[5];
		for (int i = 0; i < someMonths.length; i++) {
			someMonthLows[i] = (someMonths[i] - 4.0);
		}
		// Store the low values.
		HashMap<WeatherType, Double[]> theHourLows = new HashMap<>();
		HashMap<WeatherType, Double[]> theDayLows = new HashMap<>();
		HashMap<WeatherType, Double[]> theMonthLows = new HashMap<>();
		for (WeatherType t : WeatherType.values()) {
			theHourLows.put(t, someHourLows);
			theDayLows.put(t, someDayLows);
			theMonthLows.put(t, someMonthLows);
		}
		// Generate the high/low value storage.
		myHighLowStorage = new Storage(theHours, theHourLows, theDays, theDayLows, theMonths, theMonthLows);
	}

	/**
	 * Ensures that the default constructor is generating a proper list of "Dummy"
	 * values.
	 */
	@Test
	public void testDefaultConstructor() {
		HashMap<WeatherType, Double> someHighs = myDefaultStorage.getAllAtTime(Window.hours, 0, true);
		HashMap<WeatherType, Double> someLows = myDefaultStorage.getAllAtTime(Window.hours, 0, false);
		for (WeatherType t : WeatherType.values()) {
			assertEquals(-9999.0, someHighs.get(t), 0.1);
			assertEquals(-9999.0, someLows.get(t), 0.1);
		}
	}

	/**
	 * Verifies that the second constructor accurately holds a list of the input
	 * values.
	 */
	@Test
	public void testSecondConstructor() {
		List<Double> myHourTemps = myCustomStorage.getHistory(WeatherType.temp, Window.hours, true);
		List<Double> myDayTemps = myCustomStorage.getHistory(WeatherType.temp, Window.days, true);
		List<Double> myMonthTemps = myCustomStorage.getHistory(WeatherType.temp, Window.months, true);
		for (int i = 0; i < someHours.length; i++) {
			assertEquals(someHours[i], myHourTemps.get(i), 0.1);
			assertEquals(someDays[i], myDayTemps.get(i), 0.1);
			assertEquals(someMonths[i], myMonthTemps.get(i), 0.1);
		}

	}

	/**
	 * Verifies that the second constructors low values are not equal to the default
	 * high values.
	 */
	@Test
	public void testThirdConstructor() {
		List<Double> myHourTemps = myHighLowStorage.getHistory(WeatherType.temp, Window.hours, false);
		List<Double> myDayTemps = myHighLowStorage.getHistory(WeatherType.temp, Window.days, false);
		List<Double> myMonthTemps = myHighLowStorage.getHistory(WeatherType.temp, Window.months, false);
		for (int i = 0; i < someHours.length; i++) {
			assertNotEquals(someHours[i], myHourTemps.get(i), 0.1);
			assertNotEquals(someDays[i], myDayTemps.get(i), 0.1);
			assertNotEquals(someMonths[i], myMonthTemps.get(i), 0.1);
		}

	}

	/**
	 * Verifies the accurate length of logs can be retrieved.
	 */
	@Test
	public void testLogLengths() {
		for (Window t : Window.values()) {
			HashMap<WeatherType, Integer> myDefaultLengths = myDefaultStorage.getLogLengths(t);
			HashMap<WeatherType, Integer> myCustomLenghts = myCustomStorage.getLogLengths(t);
			for (WeatherType w : WeatherType.values()) {
				assertEquals(25, (int) myDefaultLengths.get(w));
				assertEquals(5, (int) myCustomLenghts.get(w));
			}
		}
	}

	/**
	 * Verifies that adding to a log works properly.
	 */
	@Test
	public void testAddNormal() {
		for (Window t : Window.values()) {
			myDefaultStorage.add(WeatherType.temp, t, 25.3);
			assertEquals(25.3, myDefaultStorage.getAllAtTime(t, 0, true).get(WeatherType.temp), 0.1);
			assertEquals(25, myDefaultStorage.getLogLengths(t).get(WeatherType.temp));
		}
	}

	/**
	 * Verifies that adding seperate values to the high and low segments of a log works properly.
	 */
	@Test
	public void testAddHighLow() {
		for (Window t : Window.values()) {
			myDefaultStorage.add(WeatherType.temp, t, 25.3, 20.1);
			assertEquals(25.3, myDefaultStorage.getAllAtTime(t, 0, true).get(WeatherType.temp), 0.1);
			assertEquals(20.1, myDefaultStorage.getAllAtTime(t, 0, false).get(WeatherType.temp), 0.1);
			assertEquals(25, myDefaultStorage.getLogLengths(t).get(WeatherType.temp));
		}
	}
	
	/**
	 * Tests that adding elements to a storage module in bulk updates the correct logs.
	 */
	@Test
	public void testAddBulk() {
		HashMap<WeatherType, Double> toAdd = new HashMap<>();
		toAdd.put(WeatherType.temp, 20.3);
		toAdd.put(WeatherType.outtemp, -60.3);
		myDefaultStorage.addBulk(Window.hours, toAdd);
		//Verify the indoor and outdoor temps were saved.
		assertEquals(20.3, myDefaultStorage.getHistory(WeatherType.temp, Window.hours, true).get(24), 0.1);
		assertEquals(-60.3, myDefaultStorage.getHistory(WeatherType.outtemp, Window.hours, true).get(24), 0.1);
		//Verify the barometic pressure dummy value was unchanged.
		assertEquals(-9999.0, myDefaultStorage.getHistory(WeatherType.barometric, Window.hours, true).get(24), 0.1);
	}
	
	/**
	 * Tests the bulk high/low functionality to ensure that all values are properly being stored.
	 */
	@Test
	public void testAddBulkHighLow() {
		HashMap<WeatherType, Double> toAdd = new HashMap<>();
		toAdd.put(WeatherType.temp, 20.3);
		toAdd.put(WeatherType.outtemp, -60.3);
		HashMap<WeatherType, Double> toAddLow = new HashMap<>();
		toAddLow.put(WeatherType.temp, 19.7);
		toAddLow.put(WeatherType.outtemp, -80.9);
		myDefaultStorage.addBulk(Window.hours, toAdd, toAddLow);
		//Verify the indoor and outdoor temps were saved.
		assertEquals(20.3, myDefaultStorage.getHistory(WeatherType.temp, Window.hours, true).get(24), 0.1);
		assertEquals(-60.3, myDefaultStorage.getHistory(WeatherType.outtemp, Window.hours, true).get(24), 0.1);
		assertEquals(19.7, myDefaultStorage.getHistory(WeatherType.temp, Window.hours, false).get(24), 0.1);
		assertEquals(-80.9, myDefaultStorage.getHistory(WeatherType.outtemp, Window.hours, false).get(24), 0.1);

	}
	
	/**
	 * Ensures that all out of bounds exceptions are properly thrown when retrieving from get all at time.
	 */
	@Test
	public void testBoundsExceptions() {
		assertThrows(IndexOutOfBoundsException.class,  ()-> {myDefaultStorage.getAllAtTime(Window.hours, -1, true);} );
		assertThrows(IndexOutOfBoundsException.class,  ()-> {myDefaultStorage.getAllAtTime(Window.hours, 25, true);} );
		assertThrows(IndexOutOfBoundsException.class,  ()-> {myCustomStorage.getAllAtTime(Window.hours, myCustomStorage.getLogLengths(Window.hours).get(WeatherType.temp), true);} );

	}
	
	/**
	 * Tests the one measurements array constructor for illegal arguments.
	 */
	@Test
	public void testBasicConstructorExceptions() {
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[26], new Double[25], new Double[25]);});
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[26], new Double[25]);});
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[25], new Double[26]);});
	}
	
	/**
	 * Tests the high low constructor for illegal arguments.
	 */
	@Test
	public void testAdvancedConstructorExceptions() {
		//Test the oversized array illegal argument exceptions.
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[26], new Double[25], new Double[25], new Double[25], new Double[25], new Double[25]);});
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[25], new Double[26], new Double[25], new Double[25], new Double[25]);});
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[25], new Double[25], new Double[25], new Double[26], new Double[25]);});
		//Test the size mismatch argument exceptions.
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[24], new Double[25], new Double[25], new Double[25], new Double[25]);});
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[25], new Double[24], new Double[25], new Double[25], new Double[25]);});
		assertThrows(IllegalArgumentException.class, ()-> {new LinkedLog(new Double[25], new Double[25], new Double[25], new Double[25], new Double[24], new Double[25]);});
	}
}
