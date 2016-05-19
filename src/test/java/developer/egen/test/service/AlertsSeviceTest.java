package developer.egen.test.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import developer.egen.MicroservicesEgenChallengeApplicationTests;
import developer.egen.collections.Alerts;
import developer.egen.dao.AlertsDAOImpl;

public class AlertsSeviceTest extends MicroservicesEgenChallengeApplicationTests {

	private static final Logger logger = Logger.getLogger(AlertsSeviceTest.class.getClass());

	@Autowired
	private AlertsDAOImpl alertsDAOImpl;
	
	@Before
	public void beforeTest(){
		logger.info("Executing test cases for Alert Services");
	}
	
	@After
	public void afterTest(){
		logger.info("Completed  test cases for Alert Services");
	}
	
	@Test
	public void testfindAll(){
		List<Alerts> alertList = alertsDAOImpl.find().asList();
		
		Assert.assertNotNull("failure - expected not null", alertList);
		Assert.assertNotEquals("failure - expected size greater than 0",0,alertList.size());
		
	}
	
	@Test
	public void testtimerange(){
		String timestamp1 = "1463641141076";
		String timestamp2 = "1463641151318";
		
		try {
			List<Alerts> alertList = alertsDAOImpl.readAlertsByTimeRange(timestamp1, timestamp2);
			
			Assert.assertNotNull("failure - expected not null", alertList);
			Assert.assertNotEquals("failure - expected size greater than 0",0,alertList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreate(){
		Alerts alert = new Alerts();
		alert.setAlertMessage("underweightalert");
		alert.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		alert.setValue(String.valueOf(20.0));
		
		Key<Alerts> createdalert = alertsDAOImpl.save(alert);
		Assert.assertNotNull("failure - expected not null", createdalert);
		Assert.assertNotNull("failure - expected not null", createdalert.getId());
	}
	
}
