package demo.boot.web;


import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.boot.model.City;

@RestController
@RequestMapping("/cities")
public class CityController {

    private static final Map<Integer, City> cityData = new HashMap<>();

    static {
        cityData.put(1, new City(1, "Hyderabad", "Telangana"));
        cityData.put(2, new City(2, "Chennai", "Tamil Nadu"));
        cityData.put(3, new City(3, "Bengaluru", "Karnataka"));
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable int id) {
        System.out.println("🌐 [SERVER] Received request for city id: " + id);
        // Simulate delay to prove caching works on client
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        return cityData.getOrDefault(id, new City(id, "Unknown", "Unknown"));
    }
}
