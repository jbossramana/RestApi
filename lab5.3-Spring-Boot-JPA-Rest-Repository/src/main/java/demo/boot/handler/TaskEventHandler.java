package demo.boot.handler;



import demo.boot.model.Task;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Task.class)
public class TaskEventHandler {

    @HandleBeforeCreate
    public void beforeCreate(Task task) {
        System.out.println("📌 Before creating Task: " + task.getDescription());

        // ✅ Example business logic... for POST operation
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new RuntimeException("Task description must not be empty!");
        }

        // Modify description before saving
        task.setDescription(task.getDescription().trim().toUpperCase());
    }

    @HandleBeforeSave  // for PUT operation
    public void beforeSave(Task task) {
        System.out.println("✏️ Before updating Task ID: " + task.getId());

        // Another business rule example
        if (task.getDescription().length() < 3) {
            throw new RuntimeException("Description must have at least 3 characters!");
        }
    }
}
