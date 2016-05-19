package developer.egen.config;

import java.net.UnknownHostException;

import org.easyrules.spring.RulesEngineFactoryBean;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.MongoClient;

@Configuration
@EnableAutoConfiguration
@PropertySource(value = "classpath:application.properties")
public class ApplicationConfiguration {

	@Autowired
	private Environment environment;
	
	@Bean
	public Morphia getMorphia(){
		return new Morphia();
	}
	
	@Bean
	public MongoClient getClient(){
		MongoClient mongoClient = null;
		try {
			mongoClient =  new MongoClient(environment.getRequiredProperty("mongo.host.port"));
		} catch (UnknownHostException e) {
		
			e.getMessage();
		}
		
		return mongoClient;
	}
	
	@Bean
	public Datastore dataStore(){
		Morphia morphia = getMorphia();
		morphia.mapPackage(environment.getRequiredProperty("mongo.package"), true);
		return morphia.createDatastore(getClient(),environment.getRequiredProperty("mongo.DB"));
	}
	
	@Bean
	public RulesEngineFactoryBean getRulesFactory(){
		return new RulesEngineFactoryBean();
		
	}
	
}
