package demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class RestfulApiConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulApiConsumerApplication.class, args);
	}
	
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080/user")    
                .build();
    }

}
