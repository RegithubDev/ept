package com.ept.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ept.Entity.Task;
import com.ept.Entity.User;
import com.ept.Service.ManagerService;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {


	@Autowired
	private ManagerService taskService;

	@PostMapping("/taskasign")
	public String taskasign(@RequestBody Task task) {
		return taskService.taskasign(task);
	}

	@GetMapping("/allTasks")
	public List<Task> getAllTasks() {
		return taskService.getAllTasks();
	}

	@GetMapping("/allUsers")
	public List<User> getAllUsers() {
		return taskService.getAllUsers();
	}

       @GetMapping("/getTask/{id}")
	public Object getTaskById(@PathVariable Long id) {
	  Task task = taskService.getTaskById(id);	  
	  if (task == null) { 		  
		  return "task not found with id: "+id; 		  
		  }	  
	  return task; 	  
	  }
	 
	@PutMapping("/updateTask/{id}")
	public String updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
		return taskService.update(id, updatedTask);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteTaskById(@PathVariable Long id) {
	    return taskService.delById(id);
	}
	
	
}
