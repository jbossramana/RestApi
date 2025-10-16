package demo.jaxrs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import demo.jaxrs.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
