package com.ept.Service;

import java.util.List;
import com.ept.Entity.Task;

public interface EmployeeService {
	
	List<Task> TasksByempName(String email);
	
	Task getTaskById(Long id);
	
	String update(Long id ,Task task);

}
