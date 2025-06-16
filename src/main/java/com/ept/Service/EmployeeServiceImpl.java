package com.ept.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ept.Dao.GoogleSheetsRepository;
import com.ept.Entity.Task;
import com.ept.Entity.User;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	    @Autowired
	    private GoogleSheetsRepository googleSheetsRepository;

	    @Autowired
	    private EmailService emailService;

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
		        return "Task with ID: " + id + " not found";
		    }

		    boolean isUpdated = googleSheetsRepository.updateById(id, updatedTask);

		    if (isUpdated) {
		        String managerEmail = "ravikishoremac1234@gmail.com"; // hardcode or fetch dynamically
		       // String subject = "Task Updated by " + updatedTask.getPerson();
		        String personEmail = updatedTask.getPerson(); // This is the email stored in the task
		        User employee = googleSheetsRepository.userByemail(personEmail); // Fetch user by email
		        String employeeName = employee != null ? employee.getName() : personEmail; // fallback to email if not found

		        String subject = "Task Updated by " + employeeName;
		        String body = "Dear Manager,\n\n"
		                + "The following task was updated by " + updatedTask.getPerson() + ":\n\n"
		                + "Task ID       : " + updatedTask.getId() + "\n"
		                + "Task Name     : " + updatedTask.getDescription() + "\n"
		                + "Department    : " + updatedTask.getDepartment() + "\n"
		                + "Priority      : " + updatedTask.getPriority() + "\n"
		                + "Start Date    : " + updatedTask.getStart_date() + "\n"
		                + "End Date      : " + updatedTask.getEnd_date() + "\n"
		                + "Status        : **" + updatedTask.getStatus().toUpperCase() + "**\n\n"
		                + "Please review it in your dashboard.\n\n"
		                + "Note: This is a system-generated message. Please do not reply.\n\n"
		                + "Regards,\nTask Management System.";

		        // Notify manager
		        emailService.sendEmail(managerEmail, subject, body);
		    }

		    return isUpdated ? "Task updated successfully." : "Failed to update task.";
		}
	
}
