package demo.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // (scanBasePackages = "demo.boot.*")  @ComponentScan, @Configuration , @EnableAutoConfiguration
public class CityServiceProvider {

	public static void main(String[] args) {
		SpringApplication.run(CityServiceProvider.class, args);
	}

}
