package developer.egen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("developer.egen.*")
public class MicroservicesEgenChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesEgenChallengeApplication.class, args);
	}
}
