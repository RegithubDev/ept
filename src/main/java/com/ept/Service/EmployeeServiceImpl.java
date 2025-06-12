package com.ept.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ept.Dao.GoogleSheetsRepository;
import com.ept.Entity.Task;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	    @Autowired
	    private GoogleSheetsRepository googleSheetsRepository;

		@Override
		public List<Task> TasksByempName(String email) {
			List<Task> tasks = googleSheetsRepository.TasksByempName(email);
			return tasks;
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
