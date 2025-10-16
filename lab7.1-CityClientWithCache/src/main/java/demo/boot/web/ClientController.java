package demo.boot.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.boot.service.CityClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

 private final CityClientService cityClientService;

 public ClientController(CityClientService cityClientService) {
     this.cityClientService = cityClientService;
 }

 @GetMapping("/city/{id}")
 public String getCity(@PathVariable int id) {
     return cityClientService.getCityById(id);
 }
}
