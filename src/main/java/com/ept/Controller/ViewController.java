package com.ept.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	@GetMapping("/dashboard/manager")
	  public String forwardManager() {
	    return "forward:/manager.html";
	  }

	  @GetMapping("/dashboard/employee")
	  public String forwardEmployee() {
	    return "forward:/employee.html";
	  }
	  
	  @GetMapping("/login")
	    public String login() {
	        return "forward:/login.html"; 
	    }

	    @GetMapping("/signup")
	    public String signup() {
	        return "forward:/signup.html";
	    }
	    
	    @GetMapping("/emptasklist")
	    public String emptasklist() {
	    	return "forward:empTaskList.html";
	    }
	    @GetMapping("/taskListView")
	    public String showTaskListView() {
	        return "forward:/taskListView.html";
	    }
	    
	    @GetMapping("/profile")
	    public String profile() {
			return "forward:/profile.html";
	    	
	    }
	    
	    @GetMapping("/Pdashboard")
	    public String perormaceDashboard() {
	    	return "forward:/performanceDashboard.html";
	    }
	    
	    @GetMapping("/storypoints")
	    public String storyPoints() {
	    	
	    	return "forward:/storyPoints.html";
	    	
	    }
	    
	    @GetMapping("/emplsDetails")
	    public String EmployeesDetails() {
	    	return "forward:/empDetails.html";
	    	
	    }

}
