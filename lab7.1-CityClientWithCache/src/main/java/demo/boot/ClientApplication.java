package demo.boot;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableCaching
public class ClientApplication {

 @Bean
 public RestClient restClient() {
     return RestClient.create("http://localhost:8080");
 }

 public static void main(String[] args) {
     SpringApplication.run(ClientApplication.class, args);
 }
}
