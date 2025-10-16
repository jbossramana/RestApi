package demo.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.boot.dao.TaskDao;
import demo.boot.model.Task;

@Service
public class TaskService implements ITaskService {

	@Autowired
	TaskDao  taskDao;
	
	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return (List<Task>) taskDao.findAll();
	}

	@Override
	public Optional<Task> getTaskById(Long id) {
		// TODO Auto-generated method stub
		return taskDao.findById(id);
	}

	@Override
	public Task createTask(Task task) {
		// TODO Auto-generated method stub
		return taskDao.save(task);
	}

	@Override
	public Task updateTask(Long id, Task updatedTask) {
		// TODO Auto-generated method stub
		return taskDao.save(updatedTask);
	}

	@Override
	public void deleteTask(Long id) {
		// TODO Auto-generated method stub
		 taskDao.deleteById(id);
	}

}
