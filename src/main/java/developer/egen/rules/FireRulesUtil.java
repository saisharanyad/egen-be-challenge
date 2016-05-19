package developer.egen.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.easyrules.api.RulesEngine;
import org.easyrules.spring.RulesEngineFactoryBean;

import developer.egen.dao.AlertsDAOImpl;

public class FireRulesUtil {
	
	private static final Logger logger = Logger.getLogger(AlertsDAOImpl.class.getName());
	
	public static double BASE_WIEGHT = 0;
	
	/**
	 * firesrules to create alerts
	 * @param value
	 * @param rulesEngineFactoryBean
	 * @param alertsDAOImpl
	 * @throws Exception
	 */
	public static void fireRules(double value,RulesEngineFactoryBean rulesEngineFactoryBean,AlertsDAOImpl alertsDAOImpl) throws Exception{
		logger.info("In firerules method "+value);
		MetricsOverWeightRule overweightrule = new MetricsOverWeightRule(alertsDAOImpl);
		MetricsUnderWeightRule underweightrule = new MetricsUnderWeightRule(alertsDAOImpl);
		List<Object> rulesList = new ArrayList<>();
		overweightrule.setValue(value);
		underweightrule.setValue(value);
		rulesList.add(overweightrule);
		rulesList.add(underweightrule);
		rulesEngineFactoryBean.setRules(rulesList);
		RulesEngine rulesEngine = (RulesEngine)rulesEngineFactoryBean.getObject();
		rulesEngine.fireRules();
	}
}
