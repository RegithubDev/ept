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
	     @Autowired
	     private EmailService emailService;

	      @Override
	     public String taskasign(Task task) {
	         boolean success = googleSheetsRepository.saveTask(task);

	         if (success) {
	             String subject = "New Task Assigned to You";
	             String body = "Dear " + task.getPerson() + ",\n\nA new task has been assigned to you:\n\n"
	                         + "Description: " + task.getDescription() + "\n"
	                         + "Priority: " + task.getPriority() + "\n"
	                         + "Department: " + task.getDepartment() + "\n"
	                         + "Start Date: " + task.getStart_date() + "\n"
	                         + "End Date: " + task.getEnd_date() + "\n\n"
	                         + "Please login to your dashboard to view more details\n."
	                         + "Note: This is a system-generated message. Please do not reply.\n\n"
	 		                + "Regards,\nTask Management System.";

	             // Send email to employee
	             emailService.sendEmail(task.getPerson(), subject, body);
	         }

	         return success ? "Task created and assigned successfully!" : "Failed to create and assign task.";
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
	 	        return "Task with ID: " + id + " not found";
	 	    }

	 	    boolean isUpdated = googleSheetsRepository.updateById(id, updatedTask);

	 	    if (isUpdated) {
	 	        String subject = "Task Updated";

	 	        String body = "Dear " + updatedTask.getPerson() + ",\n\n"
	 	                    + "Your assigned task has been updated by the manager. Please check the details below:\n\n"
	 	                    + "Description : " + updatedTask.getDescription() + "\n"
	 	                    + "Status      : " + updatedTask.getStatus() + "\n\n"
	 	                    + "Note: This is a system-generated message. Please do not reply.\n\n"
	 	                    + "Regards,\nTask Management System.";

	 	        // Notify employee of task update
	 	        emailService.sendEmail(updatedTask.getPerson(), subject, body);

	 	        return "Task updated successfully.";
	 	    }

	 	    return "Failed to update task.";
	 	}
	
}
