package demo.springboot.model;

import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "User",
    description = "Represents a user entity containing personal and salary details."
)
public class User {

    @Schema(
        description = "Unique identifier of the user",
        example = "101",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private long id;

    @Schema(
        description = "Name of the user. Minimum 3 characters required.",
        example = "John Doe"
    )
    @Size(min = 3, message = "name should have at least 3 characters")
    private String name;

    @Schema(
        description = "Age of the user in years",
        example = "30"
    )
    private int age;

    @Schema(
        description = "Salary of the user in INR",
        example = "75000.50"
    )
    private double salary;

    public User() {
        id = 0;
    }

    public User(long id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age
                + ", salary=" + salary + "]";
    }
}
