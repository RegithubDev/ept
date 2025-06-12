package com.ept.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ept.Dao.GoogleSheetsRepository;
import com.ept.Entity.Task;
import com.ept.Entity.User;


@Service
public class ManagerServiceImpl implements ManagerService {
	
	     @Autowired
	    private GoogleSheetsRepository googleSheetsRepository;

	     @Override
	 	public String taskasign(Task task) {
	 		 boolean success = googleSheetsRepository.saveTask(task);
	 	        return success ? "task created and assigned successfully!" : "Failed to create and assign task.";
	 	}

	 	@Override
	 	public List<Task> getAllTasks() {
	 		List<Task> taksList = googleSheetsRepository.getAllTasks();
	 		return taksList;
	 	}

	 	@Override
	 	public List<User> getAllUsers() {
	 		List<User> usersList = googleSheetsRepository.getAllUsers();
	 		return usersList;
	 	}

	 	@Override
	 	public Task getTaskById(Long id) {
	 	    Optional<Task> task = googleSheetsRepository.findById(id);
	 	    return task.orElse(null); 
	 	}

	 	@Override
	 	public String update(Long id, Task updatedTask) {
	 	    Optional<Task> existingTask = googleSheetsRepository.findById(id);
	 	    if (existingTask.isEmpty()) {	        
	 	    	return "Task with ID: " + id + "not found";
	 	    }

	 	    boolean isUpdated = googleSheetsRepository.updateById(id, updatedTask);
	 	        return isUpdated ? "Task updated successfully." : "Failed to update task.";
	 	}

	 	
	

	
}
