package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;


import org.junit.jupiter.api.*;

import storage.WeatherType;
import visualizer.AlertsPanel;
class AlertsPanelTests
{
	/**
	 * Panel For Testing
	 */
	private AlertsPanel alertsPanel;
	
	/**
	 * Sets the Panel up before each test
	 */
	@BeforeEach
	public void setUp()
	{
		alertsPanel = new AlertsPanel();
	}
	
	/**
	 * sets the initialization of the panel
	 */
	@Test
	public void testInitialize()
	{
		assertTrue(alertsPanel.getList().isEmpty());
		assertTrue(alertsPanel.getCurrentAlerts().isEmpty());
		assertTrue(alertsPanel.getComparators().isEmpty());
	}
	
	/**
	 * tests the method of calling alerts using a similar method to the actuals UpdataData method
	 */
	@Test
	public void testAlerts()
	{
		HashMap<WeatherType, Double> info = new HashMap<>();
		info.put(WeatherType.temp, (double) 91);
		info.put(WeatherType.outtemp, (double) 91);
		alertsPanel.registerAlerts(WeatherType.temp, 90);
		alertsPanel.setComparators(WeatherType.temp, "<");
		assertTrue(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 91);
		alertsPanel.setComparators(WeatherType.temp, "<");
		assertFalse(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 92);
		alertsPanel.setComparators(WeatherType.temp, ">");
		assertTrue(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 91);
		alertsPanel.setComparators(WeatherType.temp, ">");
		assertFalse(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		//The following tests are system dependent, and functionality regarding them likely should be trimmed.
		/*alertsPanel.registerAlerts(WeatherType.temp, 91);
		alertsPanel.setComparators(WeatherType.temp, "≤");
		assertTrue(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 92);
		alertsPanel.setComparators(WeatherType.temp, "≤");
		assertFalse(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 91);
		alertsPanel.setComparators(WeatherType.temp, "≥");
		assertTrue(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 90);
		alertsPanel.setComparators(WeatherType.temp, "≥");
		assertFalse(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		alertsPanel.registerAlerts(WeatherType.temp, 90);
		alertsPanel.registerAlerts(WeatherType.outtemp, 90);
		alertsPanel.setComparators(WeatherType.temp, "≥");
		alertsPanel.setComparators(WeatherType.outtemp, "≥");
		assertFalse(alertsPanel.updateDataTest(info).get(WeatherType.temp));
		assertFalse(alertsPanel.updateDataTest(info).get(WeatherType.outtemp));*/
	}
	
	/**
	 * Tests the get method for the alertsPanel
	 */
	@Test
	public void testGetAlertsPanel()
	{
		assertEquals(alertsPanel, alertsPanel.getAlertsPanel());
	}
	
	/**
	 * Tests the get method for the currentAlerts
	 */
	@Test
	public void testGetCurrentAlerts()
	{
		alertsPanel.registerAlerts(WeatherType.temp, 90);
		HashMap<WeatherType, Double> test = new HashMap<WeatherType, Double> ();
		test.put(WeatherType.temp, (double) 90);
		assertEquals(alertsPanel.getCurrentAlerts(), test);
	}
	
	/**
	 * Tests both the Set and Get method for the comparators
	 */
	@Test
	public void testSetAndGetComparators()
	{
		alertsPanel.setComparators(WeatherType.temp, ">");
		HashMap<WeatherType, String> test = new HashMap<>();
		test.put(WeatherType.temp, ">");
		assertEquals(alertsPanel.getComparators(), test);
	}
	
	
}
