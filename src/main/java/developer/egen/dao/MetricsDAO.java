package developer.egen.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;
import developer.egen.collections.Metrics;

public interface MetricsDAO {

	public List<Metrics> readMetricsByTimeRange(String timestamp1, String timestamp2) throws Exception;
}
