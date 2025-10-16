package demo.boot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.boot.model.City;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/httpclient/city")
public class CityControllerHttpClient {

    private final String BASE_URL = "http://localhost:8081/city";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<City> findCities() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), new TypeReference<List<City>>() {});
    }

    @GetMapping("/{cityId}")
    public City findCity(@PathVariable Long cityId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + cityId))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), City.class);
    }

    @PostMapping
    public void insertCity(@RequestBody City city) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(city);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    @PutMapping("/{cityId}")
    public void updateCity(@RequestBody City city, @PathVariable Long cityId) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(city);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + cityId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    @DeleteMapping("/{cityId}")
    public void deleteCity(@PathVariable int cityId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + cityId))
                .DELETE()
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }

    @GetMapping("/search")
    public List<City> findCitiesByPopulation(@RequestParam("people") int people) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/search?people=" + people))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), new TypeReference<List<City>>() {});
    }
}
