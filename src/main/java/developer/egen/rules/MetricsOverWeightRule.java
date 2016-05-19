package developer.egen.rules;

import org.apache.log4j.Logger;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.spring.SpringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import developer.egen.api.MetricsRestController;
import developer.egen.collections.Alerts;
import developer.egen.dao.AlertsDAOImpl;

/**
 * Rule to check for Overweight based on the basevalue given as input
 * and save overweight alert to MongoDB
 * 
 */

@SpringRule
public class MetricsOverWeightRule {

	private static final Logger logger = Logger.getLogger(MetricsOverWeightRule.class.getName());

	public static final double WEIGHT_PERCENT = 0.1;

	private double value;

	private static final String OverWeightMessage = "overweightalert";

	private AlertsDAOImpl alertsDAOImpl;

	public MetricsOverWeightRule(AlertsDAOImpl alertsDAOImpl) {
		this.alertsDAOImpl = alertsDAOImpl;
	}

	/**
	 * 10% over base weight returns true
	 * @return true/false
	 * @throws Exception
	 */
	@Condition
	public boolean checkForOverWeight() throws Exception{

		logger.info("In over weight rule condition");

		double minOverWeight = FireRulesUtil.BASE_WIEGHT + (WEIGHT_PERCENT * FireRulesUtil.BASE_WIEGHT);

		if (Double.compare(getValue(), minOverWeight) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * save overweightalert to mongoDB with a message
	 * @throws Exception
	 */

	@Action
	public void createAlert() throws Exception{
		
		logger.info("In over weight rule action");
		
		Alerts alert = new Alerts();
		alert.setValue(String.valueOf(getValue()));
		alert.setAlertMessage(OverWeightMessage);
		alert.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		alertsDAOImpl.save(alert);

	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
