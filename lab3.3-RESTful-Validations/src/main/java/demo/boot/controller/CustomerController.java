package demo.boot.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import demo.boot.exception.CustomerNotFoundException;
import demo.boot.model.Customer;
import demo.boot.service.CustomerBeanService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerBeanService service;

    // ✅ Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.findAll();
    }

    // ✅ Get single customer with exception handling
    @GetMapping("/{name}")
    public Customer getCustomer(@PathVariable String name) {
        Customer customer = service.findCustomer(name);

        if (customer == null)
            throw new CustomerNotFoundException("Customer not found with name: " + name);

        return customer;
    }

    // ✅ Delete customer
    @DeleteMapping("/{name}")
    public void deleteCustomer( @PathVariable String name) {
        Customer customer = service.deleteByName(name);

        if (customer == null)
            throw new CustomerNotFoundException("Customer not found with name: " + name);
    }

    // ✅ Create new customer (tests all validations)
    @PostMapping
    public ResponseEntity<Object> createCustomerWithStatus(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = service.saveCustomer(customer);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(savedCustomer.getName())
                .toUri();

        return ResponseEntity.created(uri).body(savedCustomer);
    }

    // ✅ Custom Validation Error Handler — returns readable JSON response
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors; // example response: {"email":"Email should be valid","name":"Name should have at least 3 characters"}
    }


}
