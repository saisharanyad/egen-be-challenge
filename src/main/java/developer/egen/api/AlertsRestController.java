package developer.egen.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import developer.egen.collections.Alerts;
import developer.egen.collections.Metrics;
import developer.egen.dao.AlertsDAOImpl;


/** 
 * RestController exposing api to read all alerts and 
 * specific alerts by timestamp.
 * 
 * **/
@RestController
@RequestMapping(value = "/alerts")
public class AlertsRestController {

	private static final Logger logger = Logger.getLogger(AlertsRestController.class.getName());

	@Autowired
	private AlertsDAOImpl alertsDAOImpl;

	/*GET method to read all alerts
	 * */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ResponseEntity<List<Alerts>> readAllAlerts() {
		logger.info("In readAlerts  method ");
		QueryResults<Alerts> alertsList = null;
		try {
			alertsList = alertsDAOImpl.find();
		} catch (Exception e) {
			logger.error("Error reading alerts -" + e.getMessage());
		}
		return new ResponseEntity<List<Alerts>>(alertsList.asList(),HttpStatus.OK);
	}

	/*GET method to read alerts based on timestamp
	 * @param1 timestamp
	 * @param2 timestamp
	 * */
	@RequestMapping(value = "/read/{timestamp1}/{timestamp2}", method = RequestMethod.GET)
	public ResponseEntity<List<Alerts>> readAlertsbyTimeRange(@PathVariable("timestamp1") String timestamp1,
			@PathVariable("timestamp2") String timestamp2) {
		logger.info("In readalerts  method with timerange -" + timestamp1 + "-" + timestamp2);
		List<Alerts> alertsList = null;
		try {
			alertsList =  alertsDAOImpl.readAlertsByTimeRange(timestamp1, timestamp2);
		} catch (Exception e) {
			logger.error("Error reading alerts by time range -" + e.getMessage());
		}
		return new ResponseEntity<List<Alerts>>(alertsList,HttpStatus.OK);

	}

}
