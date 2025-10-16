package demo.boot.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Customer {

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name should have at least 3 characters")
    private String name;

    @NotNull(message = "Location cannot be null")
    @Size(min = 2, message = "Location should have at least 2 characters")
    private String location;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String phoneNumber;

    // Constructor
    public Customer(String name, String location, String email, String phoneNumber) {
        super();
        this.name = name;
        this.location = location;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Default constructor (important for frameworks like Spring Boot)
    public Customer() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", location=" + location + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
    }
}
