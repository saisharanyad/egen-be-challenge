package developer.egen.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import developer.egen.collections.Alerts;
import developer.egen.collections.Metrics;

public interface AlertsDAO {

	public List<Alerts> readAlertsByTimeRange(String timestamp1,String timestamp2) throws Exception;
}
