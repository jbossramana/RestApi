package demo.boot.service;

import java.util.List;
import java.util.Optional;


import demo.boot.model.Task;

public interface ITaskService {

	List<Task> getAllTasks();
	Optional<Task>  getTaskById( Long id);
	Task createTask( Task task);
	Task updateTask( Long id,  Task updatedTask);
	void deleteTask( Long id);
	
	
	
}
