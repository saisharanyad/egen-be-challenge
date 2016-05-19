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

import developer.egen.collections.Metrics;

@Repository
public class MetricsDAOImpl extends BasicDAO<Metrics, ObjectId> implements MetricsDAO  {

	private static final Logger logger = Logger.getLogger(MetricsDAOImpl.class.getName());
	
	@Autowired
	protected MetricsDAOImpl(Datastore dataStore) {
		super(dataStore);
	}

	/** DAO implementation to fetch metrics from mongoDB based on timestamp
	 * **/
	@Override
	public List<Metrics> readMetricsByTimeRange(String timestamp1, String timestamp2) throws Exception{
		logger.info("In readMeticsByTimeRange DAO -"+timestamp1+"-"+timestamp2);
		Query<Metrics> query = createQuery();
		query.and(query.criteria("timeStamp").greaterThanOrEq(timestamp1),
				 query.criteria("timeStamp").lessThanOrEq(timestamp2));
		
		return query.asList();
	}

}
