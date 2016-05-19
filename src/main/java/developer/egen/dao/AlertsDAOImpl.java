package developer.egen.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import developer.egen.api.AlertsRestController;
import developer.egen.collections.Alerts;
import developer.egen.collections.Metrics;

@Repository
public class AlertsDAOImpl extends BasicDAO<Alerts, ObjectId> implements AlertsDAO {
	
	private static final Logger logger = Logger.getLogger(AlertsDAOImpl.class.getName());
	
	@Autowired
	protected AlertsDAOImpl(Datastore dataStore) {
		super(dataStore);
	}

	@Override
	public List<Alerts> readAlertsByTimeRange(String timestamp1, String timestamp2) throws Exception{
		
		logger.info("In readAlertsByTimeRange DAO -"+timestamp1+"-"+timestamp2);
		
		Query<Alerts> query = createQuery();
		query.and(query.criteria("timeStamp").greaterThanOrEq(timestamp1),
				 query.criteria("timeStamp").lessThanOrEq(timestamp2));
		
		return query.asList();
		
	}

}
