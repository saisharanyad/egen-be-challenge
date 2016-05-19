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

@SpringRule
public class MetricsUnderWeightRule {

	private static final Logger logger = Logger.getLogger(MetricsUnderWeightRule.class.getName());

	public static final double WEIGHT_PERCENT = 0.1;

	private double value;

	private static final String UnderWeightMessage = "underweightalert";

	private AlertsDAOImpl alertsDAOImpl;

	public MetricsUnderWeightRule(AlertsDAOImpl alertsDAOImpl) {
		this.alertsDAOImpl = alertsDAOImpl;
	}

	@Condition
	public boolean checkForUnderWeight() throws Exception{
		
		logger.info("In under weight rule condition");

		double minUnderWeight = FireRulesUtil.BASE_WIEGHT - (0.1 * FireRulesUtil.BASE_WIEGHT);

		if (Double.compare(minUnderWeight, getValue()) > 0) {
			return true;
		}
		return false;

	}

	@Action
	public void createAlert() throws Exception {
		
		logger.info("In under weight rule action");
		
		Alerts alert = new Alerts();
		alert.setValue(String.valueOf(getValue()));
		alert.setAlertMessage(UnderWeightMessage);
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
