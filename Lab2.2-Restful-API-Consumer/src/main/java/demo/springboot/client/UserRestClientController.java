package demo.springboot.client;

import demo.springboot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client/user")
public class UserRestClientController {

    private final RestClient restClient;

    public UserRestClientController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        User[] users = restClient.get()
                .retrieve()
                .body(User[].class);

        if (users == null || users.length == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Arrays.asList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = restClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .body(User.class);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // e.g. 404 or connection issue
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
       
            ResponseEntity<Void> response = restClient.post()
                    .uri("http://localhost:8080/user")  
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(user)
                    .retrieve()
                    .toEntity(Void.class);

            // Read Location header
            String location = response.getHeaders().getFirst("Location");

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("✅ User created successfully. Resource URL: " + location);

        } catch (org.springframework.web.client.HttpClientErrorException.Conflict e) {
            // Handle 409 Conflict
            String errorMsg = e.getResponseBodyAsString();
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("❌ Conflict: " + errorMsg);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error creating user: " + e.getMessage());
        }
    }


    

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            restClient.put()
                    .uri("/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(user)
                    .retrieve()
                    .toBodilessEntity();

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            restClient.delete()
                    .uri("/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {
        restClient.delete()
                .retrieve()
                .toBodilessEntity();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<User>> getUsersWithPagination(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) Double minSalary
    ) {
        User[] users = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/page")
                        .queryParam("pageNo", pageNo)
                        .queryParam("pageSize", pageSize)
                        .queryParam("sortBy", sortBy)
                        .queryParamIfPresent("minSalary", Optional.ofNullable(minSalary))
                        .build())
                .retrieve()
                .body(User[].class);

        if (users == null || users.length == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(Arrays.asList(users));
    }
}
