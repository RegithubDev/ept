//package com.ept.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.ept.Entity.Employees;
//import com.ept.Entity.LoginResponse;
//import com.ept.Entity.User;
//
//
//public interface AuthUserService {
//	
//	String signup(User user);	
//	Optional<LoginResponse> login(String email, String role,String password);	
//	User getUserByEmail(String emali);
//	List<Employees> getAllemployees();
//	
//
//}

package com.ept.Service;

import java.util.List;
import java.util.Optional;

import com.ept.Entity.Employees;
import com.ept.Entity.LoginResponse;
import com.ept.Entity.User;

public interface AuthUserService {
    String signup(User user);    
    
    Optional<LoginResponse> login(String email, String role, String password); 
    User getUserByEmail(String email);
    List<Employees> getAllemployees();
    String requestOtp(String email);
    boolean verifyOtp(String email, String otp);
    boolean resetPassword(String email, String newPassword);
}
