package demo.boot.service;



import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CityClientService {

 private final RestClient restClient;

 public CityClientService(RestClient restClient) {
     this.restClient = restClient;
 }

 @Cacheable("cities")
 public String getCityById(int id) {
     System.out.println("Client: Making API call for ID " + id);
     return restClient.get()
             .uri("/cities/{id}", id)
             .retrieve()
             .body(String.class);
 }
}
