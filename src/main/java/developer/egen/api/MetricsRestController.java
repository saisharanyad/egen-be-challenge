package developer.egen.api;

import java.util.List;

import org.apache.log4j.Logger;
import org.easyrules.spring.RulesEngineFactoryBean;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import developer.egen.collections.Alerts;
import developer.egen.collections.Metrics;
import developer.egen.dao.AlertsDAOImpl;
import developer.egen.dao.MetricsDAOImpl;
import developer.egen.rules.FireRulesUtil;



/** 
 * RestController exposing api to read all metrics and 
 * specific metrics by timestamp.
 * 
 * **/
@RestController
@RequestMapping(value = "/metrics")
public class MetricsRestController {

	private static final Logger logger = Logger.getLogger(MetricsRestController.class.getName());

	@Autowired
	private RulesEngineFactoryBean rulesEngineFactoryBean;

	@Autowired
	private MetricsDAOImpl metricsDAOImpl;

	@Autowired
	private AlertsDAOImpl alertsDAOImpl;

	private int count = 0;
	
	
	/*POST method to create metrics and alerts based on rules
	 * */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void createMetrics(@RequestBody Metrics metrics) {

		logger.info("In createMetrics  method with parameters " + metrics.getValue() + "-" + metrics.getTimeStamp());
		count++;
		try {
			double value = Double.parseDouble(metrics.getValue());
			if (count == 1) {
				FireRulesUtil.BASE_WIEGHT = value;
			}
			//fire rules to create alerts
			FireRulesUtil.fireRules(value, rulesEngineFactoryBean, alertsDAOImpl);
			metricsDAOImpl.save(metrics);

		} catch (Exception e) {
			logger.error("Error creating metrics -" + e.getMessage());

		}

	}

	/*GET method to read all metrics
	 * */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ResponseEntity<List<Metrics>> readAllMetrics() {

		logger.info("In readMetrics  method ");
		QueryResults<Metrics> metricsList = null;
		try {
			  metricsList = metricsDAOImpl.find();
		} catch (Exception e) {
			logger.error("Error reading metrics -" + e.getMessage());
		}

		return new ResponseEntity<List<Metrics>>(metricsList.asList(),HttpStatus.OK);
	}

	/*GET method to read metrics based on timestamp
	 * @param1 timestamp
	 * @param2 timestamp
	 * */
	@RequestMapping(value = "/read/{timestamp1}/{timestamp2}", method = RequestMethod.GET)
	public ResponseEntity<List<Metrics>> readMetricsbyTimeRange(@PathVariable("timestamp1") String timestamp1,
			@PathVariable("timestamp2") String timestamp2) {
		logger.info("In readMetrics  method with timerange -" + timestamp1 + "-" + timestamp2);
		List<Metrics> metricsList = null;
		try {
			metricsList = metricsDAOImpl.readMetricsByTimeRange(timestamp1, timestamp2);
		} catch (Exception e) {
			logger.error("Error reading metrics by time range -" + e.getMessage());
		}

		return new ResponseEntity<List<Metrics>>(metricsList,HttpStatus.OK);
	}

}
