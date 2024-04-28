package ee.pitko.fx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FxApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxApplication.class, args);
	}

}
