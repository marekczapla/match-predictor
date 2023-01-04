package pl.markopolo.matchpredictor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MatchPredictorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchPredictorApplication.class, args);
	}

}
