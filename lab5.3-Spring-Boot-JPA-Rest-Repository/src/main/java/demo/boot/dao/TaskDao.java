package demo.boot.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import demo.boot.model.Task;



@RepositoryRestResource(itemResourceRel = "taskEndpoint", path = "task")
public interface TaskDao  extends CrudRepository<Task, Long>{

    @RestResource(
            path = "by-description", 
            rel = "findByDescription",
            exported = true
        )
	   List<Task> findTaskByDescription(@Param("desc") String description);

}

