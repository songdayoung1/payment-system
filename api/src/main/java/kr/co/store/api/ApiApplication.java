package kr.co.store.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		String envVar = System.getenv("API_BASE_URL");
		System.out.println("Environment variable: " + envVar);

		SpringApplication.run(ApiApplication.class, args);

	}

}
