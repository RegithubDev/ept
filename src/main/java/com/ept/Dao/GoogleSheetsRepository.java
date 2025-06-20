package com.ept.Dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.ept.Entity.Employees;
import com.ept.Entity.Task;
import com.ept.Entity.User;
import com.ept.Util.GoogleSheetProperties;
import com.ept.Util.GoogleSheetServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;



@Repository
public class GoogleSheetsRepository {
		
	 private final GoogleSheetProperties sheetProperties;
	 
	 
	 public GoogleSheetsRepository(GoogleSheetProperties sheetProperties) {
	        this.sheetProperties = sheetProperties;			
	    }
	 
	

	 /*---------- User SignUp-----------------*/
	    public boolean saveUser(User user) {
			
	    	try {
	            Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsFirst());

	            // Read existing data to determine row count
	            ValueRange readResult = sheetsService.spreadsheets().values()
	                    .get(sheetProperties.getUserid(), "Users.Resustainability")
	                    .execute();

	            List<List<Object>> existingRows = readResult.getValues();
	            int nextId = (existingRows == null || existingRows.size() <= 1)
	                    ? 1
	                    : existingRows.size(); // Assume header is at index 0

	            user.setId((long) nextId);

	            List<Object> rowData = new ArrayList<>();
	            rowData.add(user.getId());
	            rowData.add(user.getName());
	            rowData.add(user.getEmail());
	            rowData.add(user.getMobileNumber());
	            rowData.add(user.getRole());
	            rowData.add(user.getDepartment());
	            rowData.add(user.getStatus());
	            rowData.add(user.getPassword());
	            rowData.add(user.getReportingto());

	            List<List<Object>> rows = new ArrayList<>();
	            rows.add(rowData);

	            ValueRange appendBody = new ValueRange()
                    .setValues(rows);

	            sheetsService.spreadsheets().values()
	                    .append(sheetProperties.getUserid(), "Users.Resustainability", appendBody)
	                    .setValueInputOption("RAW")
	                    .execute();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
	    	return true;
	    	
    }
	    
	
	
	    /*---------- Getting All Users for Login Purpose-----------------*/
	    
	    public List<User> getAllUsers() {
	        try {
	            Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsFirst());
	            ValueRange response = sheetsService.spreadsheets().values()
	                    .get(sheetProperties.getUserid(), "Users.Resustainability") // Make sure the range includes your data
	                    .execute();

	            List<List<Object>> values = response.getValues();
	            List<User> users = new ArrayList<>();

	            if (values == null || values.size() <= 1) {
	                return users; // empty or only header
	            }

	            for (int i = 1; i < values.size(); i++) { // skip header
	                List<Object> row = values.get(i);

	                // Skip rows that are completely empty
	                if (row == null || row.isEmpty() || row.get(0).toString().trim().isEmpty()) {
	                    continue;
	                }

	                User user = new User();
	                user.setId(row.size() > 0 ? Long.parseLong(row.get(0).toString()) : null);
	                user.setName(row.size() > 1 ? row.get(1).toString() : "");
	                user.setEmail(row.size() > 2 ? row.get(2).toString() : "");
	                user.setMobileNumber(row.size() > 3 ? row.get(3).toString() : "");
	                user.setRole(row.size() > 4 ? row.get(4).toString() : "");	                
	                user.setDepartment(row.size() > 5 ? row.get(5).toString() : "");
	                user.setStatus(row.size() > 6 ? row.get(6).toString() : "");
	                user.setPassword(row.size() > 7 ? row.get(7).toString() : "");
	                user.setReportingto(row.size() > 8 ? row.get(8).toString() : "");
	                users.add(user);
	            }

	            return users;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return Collections.emptyList();
	        }
	    }


	    /*---------- Assigning Task(Used In Manager)-----------------*/
	    
		public boolean saveTask(Task task) {
			// TODO Auto-generated method stub
			try {
	            Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsSecond());

	            // Read existing data to determine row count
	            ValueRange readResult = sheetsService.spreadsheets().values()
	                    .get(sheetProperties.getTasksid(), "Tasks.Resustainability")
	                    .execute();

	            List<List<Object>> existingRows = readResult.getValues();
	            int nextId = (existingRows == null || existingRows.size() <= 1)
	                    ? 1
	                    : existingRows.size(); // Assume header is at index 0

	            task.setId((long) nextId);

	            
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	            List<Object> rowData = new ArrayList<>();
	            rowData.add(task.getId() != null ? task.getId().toString() : "");
	            rowData.add(task.getDescription() != null ? task.getDescription() : "");
	            rowData.add(task.getDepartment() != null ? task.getDepartment() : "");
	            rowData.add(task.getArea() != null ? task.getArea() : "");
	            rowData.add(task.getSub_area() != null ? task.getSub_area() : "");
	            rowData.add(task.getPriority() != null ? task.getPriority() : "");
	            rowData.add(task.getPerson() != null ? task.getPerson() : "");
	            rowData.add(task.getStart_date() != null ? task.getStart_date().format(formatter) : "");
	            rowData.add(task.getEnd_date() != null ? task.getEnd_date().format(formatter) : "");
	            rowData.add(task.getBottlenecks() != null ? task.getBottlenecks() : "");
	            rowData.add(task.getStorypoints() != null ? task.getStorypoints().toString() : "");
	            rowData.add(task.getStatus() != null ? task.getStatus() : "");
		    rowData.add(task.getCompletion_date() != null ? task.getCompletion_date().format(formatter) : "");
	            rowData.add(task.getRemarks() != null ? task.getRemarks() : "");



	            List<List<Object>> rows = new ArrayList<>();
	            rows.add(rowData);

	            ValueRange appendBody = new ValueRange()
	                    .setValues(rows);

	            sheetsService.spreadsheets().values()
	                    .append(sheetProperties.getTasksid(), "Tasks.Resustainability", appendBody)
	                    .setValueInputOption("RAW")
	                    .execute();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
	    	return true;
	    	
	    }

		/*---------- Getting All Tasks(Used In Manager)-----------------*/
		
		public List<Task> getAllTasks() {
		    List<Task> tasks = new ArrayList<>();

		    try {
		        Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsSecond());

		        ValueRange response = sheetsService.spreadsheets().values()
		                .get(sheetProperties.getTasksid(), "Tasks.Resustainability")
		                .execute();

		        List<List<Object>> values = response.getValues();

		        // Return an empty task if the sheet is empty or has only the header
		        if (values == null || values.size() < 2) {
		            Task emptyTask = new Task();
		            emptyTask.setDescription("");
		            emptyTask.setDepartment("");
		            emptyTask.setArea("");
		            emptyTask.setSub_area("");
		            emptyTask.setPriority("");
		            emptyTask.setPerson("");
		            emptyTask.setStorypoints(0L);
		            emptyTask.setStart_date(null);
		            emptyTask.setEnd_date(null);
		            emptyTask.setBottlenecks("");
		            emptyTask.setStatus("");
			    emptyTask.setCompletion_date(null);
		            emptyTask.setRemarks("");
		            return Collections.singletonList(emptyTask);
		        }

		        for (int i = 1; i < values.size(); i++) {
		            List<Object> row = values.get(i);
		            Task task = new Task();

		            try {
		                task.setId(getLongSafe(row, 0));
		                task.setDescription(getStringSafe(row, 1));
		                task.setDepartment(getStringSafe(row, 2));
		                task.setArea(getStringSafe(row, 3));
		                task.setSub_area(getStringSafe(row, 4));
		                task.setPriority(getStringSafe(row, 5));
		                task.setPerson(getStringSafe(row, 6));
		                task.setStart_date(parseLocalDateSafe(row, 7));
		                task.setEnd_date(parseLocalDateSafe(row, 8));
		                task.setBottlenecks(getStringSafe(row, 9));
		                task.setStorypoints(getLongSafe(row, 10));
		                task.setStatus(getStringSafe(row, 11));
				task.setCompletion_date(parseLocalDateSafe(row, 12));
		                task.setRemarks(getStringSafe(row, 13));

		                tasks.add(task);
		            } catch (Exception rowEx) {
		                System.err.println("Skipping row " + (i + 1) + " due to error: " + rowEx.getMessage());
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return tasks;
		}
		
		/*---------- Find Task Using Id-----------------*/
		
		public Optional<Task> findById(Long id) {
		    try {
		        Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsSecond());

		        ValueRange response = sheetsService.spreadsheets().values()
		                .get(sheetProperties.getTasksid(), "Tasks.Resustainability")
		                .execute();

		        List<List<Object>> values = response.getValues();

		        if (values == null || values.size() < 2) {
		            return Optional.empty(); // No data or only header
		        }

		        for (int i = 1; i < values.size(); i++) {
		            List<Object> row = values.get(i);

		            try {
		                Long rowId = getLongSafe(row, 0); // use safe parse
		                if (rowId != null && rowId.equals(id)) {
		                    Task task = new Task();
		                    task.setId(rowId);
		                    task.setDescription(getStringSafe(row, 1));
		                    task.setDepartment(getStringSafe(row, 2));
		                    task.setArea(getStringSafe(row, 3));
		                    task.setSub_area(getStringSafe(row, 4));
		                    task.setPriority(getStringSafe(row, 5));
		                    task.setPerson(getStringSafe(row, 6));
		                    task.setStart_date(parseLocalDateSafe(row, 7));
		                    task.setEnd_date(parseLocalDateSafe(row, 8));
		                    task.setBottlenecks(getStringSafe(row, 9));
		                    task.setStorypoints(getLongSafe(row, 10));
		                    task.setStatus(getStringSafe(row, 11));
				    task.setCompletion_date(parseLocalDateSafe(row, 12));
		                    task.setRemarks(getStringSafe(row, 13));

		                    return Optional.of(task);
		                }
		            } catch (Exception ex) {
		                System.err.println("Skipping row " + (i + 1) + ": " + ex.getMessage());
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return Optional.empty();
		}

		
		/*---------- Update Task Using Id-----------------*/

		public boolean updateById(Long id, Task updatedTask) {
		    try {
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsSecond());

		        ValueRange response = sheetsService.spreadsheets().values()
		                .get(sheetProperties.getTasksid(), "Tasks.Resustainability")
		                .execute();

		        List<List<Object>> values = response.getValues();

		        if (values == null || values.size() < 2) {
		            return false;
		        }

		        for (int i = 1; i < values.size(); i++) {
		            List<Object> row = values.get(i);
		            if (row.size() > 0 && row.get(0) != null) {
		                Long rowId = Long.parseLong(row.get(0).toString());
		                if (rowId.equals(id)) {
		                    List<Object> updatedRow = Arrays.asList(
		                            updatedTask.getId(),
		                            updatedTask.getDescription(),
		                            updatedTask.getDepartment(),
		                            updatedTask.getArea(),
		                            updatedTask.getSub_area(),
		                            updatedTask.getPriority(),
		                            updatedTask.getPerson(),
		                            updatedTask.getStart_date() != null ? updatedTask.getStart_date().format(formatter) : "",
		                            updatedTask.getEnd_date() != null ? updatedTask.getEnd_date().format(formatter) : "",
		                            updatedTask.getBottlenecks(),
		                            updatedTask.getStorypoints() == null ? "":updatedTask.getStorypoints(),
		                            updatedTask.getStatus(),
					    updatedTask.getCompletion_date() != null ? updatedTask.getCompletion_date().format(formatter) : "",
		                            updatedTask.getRemarks()
		                    );

		                    ValueRange body = new ValueRange().setValues(Collections.singletonList(updatedRow));
		                    String range = "Tasks.Resustainability!A" + (i + 1) + ":N" + (i + 1); // Adjust to include column L (remarks)

		                    sheetsService.spreadsheets().values()
		                            .update(sheetProperties.getTasksid(), range, body)
		                            .setValueInputOption("RAW")
		                            .execute();

		                    return true;
		                }
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}

		
		/*---------- List of Tasks using Email(Used In Employee)-----------------*/
		
		public List<Task> TasksByempName(String email) {
		    List<Task> employeeTasks = new ArrayList<>();

		    try {
		        Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsSecond());

		        ValueRange response = sheetsService.spreadsheets().values()
		                .get(sheetProperties.getTasksid(), "Tasks.Resustainability")
		                .execute();

		        List<List<Object>> values = response.getValues();

		        if (values == null || values.size() < 2) {
		            return Collections.emptyList(); // Only header or no data
		        }

		        for (int i = 1; i < values.size(); i++) {
		            List<Object> row = values.get(i);

		            try {
		                String personInRow = getStringSafe(row, 6); // Person column
		                if (!personInRow.equalsIgnoreCase(email)) continue;

		                Task task = new Task();
		                task.setId(getLongSafe(row, 0));
		                task.setDescription(getStringSafe(row, 1));
		                task.setDepartment(getStringSafe(row, 2));
		                task.setArea(getStringSafe(row, 3));
		                task.setSub_area(getStringSafe(row, 4));
		                task.setPriority(getStringSafe(row, 5));
		                task.setPerson(personInRow);		                
		                task.setStart_date(parseLocalDateSafe(row, 7));
		                task.setEnd_date(parseLocalDateSafe(row, 8));
		                task.setBottlenecks(getStringSafe(row, 9));
		               // task.setStorypoints(getLongSafe(row, 10));
		                task.setStatus(getStringSafe(row, 11));
				task.setCompletion_date(parseLocalDateSafe(row, 12));
		                task.setRemarks(getStringSafe(row, 13));

		                employeeTasks.add(task);
		            } catch (Exception rowEx) {
		                System.err.println("Skipping row " + (i + 1) + " due to error: " + rowEx.getMessage());
		            }
		        }

		    } catch (Exception e) {
		        e.printStackTrace(); // Preferably log this
		    }

		    return employeeTasks;
		}
		
		 public User userByemail(String email) {
		        try {
		            Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsFirst());

		            ValueRange response = sheetsService.spreadsheets().values()
		                    .get(sheetProperties.getUserid(), "Users.Resustainability") // assuming "Users" is the sheet name
		                    .execute();

		            List<List<Object>> values = response.getValues();

		            if (values == null || values.size() < 2) {
		                return null; // No data or only header
		            }

		            for (int i = 1; i < values.size(); i++) {
		                List<Object> row = values.get(i);
		                String rowEmail = getStringSafe(row, 2); // Email is at index 2

		                if (rowEmail != null && rowEmail.equalsIgnoreCase(email)) {
		                    User user = new User();
		                    user.setId(getLongSafe(row, 0));               // ID
		                    user.setName(getStringSafe(row, 1));           // Name
		                    user.setEmail(rowEmail);                       // Email
		                    user.setMobileNumber(getObjectSafe(row, 3));   // MobileNumber
		                    user.setRole(getStringSafe(row, 4));           // Role
		                    user.setDepartment(getStringSafe(row, 5));        // Department
		                    user.setStatus(getStringSafe(row, 6));         // Status
		                    user.setPassword(getStringSafe(row, 7)); // password is at index 7
		                    user.setReportingto(getStringSafe(row, 8));
		                    return user;
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace(); // Preferably log this
		        }

		        return null;
		    }

		    

		    private Object getObjectSafe(List<Object> row, int index) {
		        if (row.size() > index) {
		            return row.get(index);
		        }
		        return null;
		    }
		    
		    
		    public List<Employees> allEmployees() {
		        try {
		            Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsThird());

		            ValueRange response = sheetsService.spreadsheets().values()
		                    .get(sheetProperties.getEmpid(), "Employees.Resustainability") // Sheet name or range
		                    .execute();

		            List<List<Object>> values = response.getValues();
		            List<Employees> employees = new ArrayList<>();

		            if (values == null || values.size() <= 1) {
		                return employees; // Return empty if no data or only header
		            }

		            for (int i = 1; i < values.size(); i++) { // Skip header row
		                List<Object> row = values.get(i);

		                // Ensure both ID and email are present
		                if (row.size() < 2 || row.get(1).toString().trim().isEmpty()) {
		                    continue; // Skip incomplete row
		                }

		                Employees emp = new Employees();
		                emp.setId(row.size() > 0 ? Long.parseLong(row.get(0).toString()) : null); // First column = ID
		                emp.setEmail(row.get(1).toString()); // Second column = Email
                        emp.setRole(row.get(2).toString()); // Third column = Role
		                employees.add(emp);
		            }

		            return employees;

		        } catch (Exception e) {
		            e.printStackTrace();
		            return Collections.emptyList();
		        }
		    }
		    
		    public boolean updateUserPassword(String email, String newPassword) {
		        try {
		            Sheets sheetsService = GoogleSheetServiceUtil.getSheetsService(sheetProperties.getCredentialsFirst());
		            if (sheetsService == null) {
		                System.err.println("Sheets service is null. Check credentials.");
		                return false;
		            }

		            ValueRange response = sheetsService.spreadsheets().values()
		                    .get(sheetProperties.getUserid(), "Users.Resustainability")
		                    .execute();

		            List<List<Object>> values = response.getValues();
		            if (values == null || values.size() <= 1) {
		                System.err.println("No data found in Users sheet.");
		                return false;
		            }

		            int rowIndex = -1;
		            for (int i = 1; i < values.size(); i++) {
		                List<Object> row = values.get(i);
		                if (row.size() > 2 && row.get(2).toString().equalsIgnoreCase(email.trim())) {
		                    rowIndex = i + 1; // Google Sheets rows are 1-based
		                    break;
		                }
		            }

		            if (rowIndex == -1) {
		                System.err.println("User not found for email: " + email);
		                return false; // User not found
		            }

		            // Update the password column (index 7)
		            List<List<Object>> updateData = new ArrayList<>();
		            List<Object> rowData = new ArrayList<>();
		            rowData.add(newPassword);
		            updateData.add(rowData);

		            ValueRange updateBody = new ValueRange().setValues(updateData);
		            sheetsService.spreadsheets().values()
		                    .update(sheetProperties.getUserid(), "Users.Resustainability!H" + rowIndex, updateBody)
		                    .setValueInputOption("RAW")
		                    .execute();

		            System.out.println("Password updated successfully for email: " + email);
		            return true;

		        } catch (Exception e) {
		            System.err.println("Error updating password for email " + email + ": " + e.getMessage());
		            e.printStackTrace();
		            return false;
		        }
		    }
		

		    
	
	
		/*---------- Utility Methods-----------------*/
		
		private String getStringSafe(List<Object> row, int index) {
		    return row.size() > index ? row.get(index).toString().trim() : "";
		}

		private Long getLongSafe(List<Object> row, int index) {
		    try {
		        return row.size() > index ? Long.parseLong(row.get(index).toString().trim()) : null;
		    } catch (NumberFormatException e) {
		        return null;
		    }
		}


		private LocalDate parseLocalDateSafe(List<Object> row, int index) {
		    try {
		        if (row.size() > index && row.get(index) != null && !row.get(index).toString().isEmpty()) {
		            return LocalDate.parse(row.get(index).toString());
		        }
		    } catch (Exception e) {
		        System.err.println("Date parse error at column " + index + ": " + e.getMessage());
		    }
		    return null;
		}
		
		
		
}	



