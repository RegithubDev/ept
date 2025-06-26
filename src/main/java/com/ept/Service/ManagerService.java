package com.ept.Service;

import java.util.List;
import com.ept.Entity.Task;
import com.ept.Entity.User;

public interface ManagerService {
	
	String taskasign(Task task);
	
	List<Task> getAllTasks();
	
	List<User> getAllUsers();
	
	Task getTaskById(Long id);
	
	String update(Long id ,Task task);
	
	String delById(Long id);

	String delEmpByMail(String email);
}
