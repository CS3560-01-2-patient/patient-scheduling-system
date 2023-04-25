package application;
import java.sql.*;
import java.util.*;

public class Patient{
	// All of the patients attributes
	private int patientId;
	private String name;
	private String email;
	private String username;
	private String password;
	private String phoneNumber;
	private String dateOfBirth;
	private String gender;
	
	// Default Constructor for a patient
	public Patient()
	{

	}
	
	public Patient(int patientId)
	{
		this.getPatientInfo(patientId);
	}
	
	public Patient(int patientId, String name, String email, String username, String password, String phoneNumber, String dateOfBirth, String gender) {
	      this.patientId = patientId;
	      this.name = name;
	      this.email = email;
	      this.username = username;
	      this.password = password;
	      this.phoneNumber = phoneNumber;
	      this.dateOfBirth = dateOfBirth;
	      this.gender = gender;
	   }
	
	// All of the patient's getters and setters
	public int getPatientId() {
	      return patientId;
	   }

	   public void setPatientId(int patientId) {
	      this.patientId = patientId;
	   }

	   public String getName() {
	      return name;
	   }

	   public void setName(String name) {
	      this.name = name;
	   }

	   public String getEmail() {
	      return email;
	   }

	   public void setEmail(String email) {
	      this.email = email;
	   }

	   public String getUsername() {
	      return username;
	   }

	   public void setUsername(String username) {
	      this.username = username;
	   }

	   public String getPassword() {
	      return password;
	   }

	   public void setPassword(String password) {
	      this.password = password;
	   }
	   
	   public String getPhoneNumber() {
		  return phoneNumber;
	   }
	   
	   public void setPhoneNumber(String phoneNumber) {
		   this.phoneNumber = phoneNumber;
	   }
	   
	   public String getDateOfBirth() {
		   return dateOfBirth;
	   }
	   
	   public void setDateOfBirth(String dateOfBirth) {
		  this.dateOfBirth = dateOfBirth;
	   }
	   
	   public String getGender() {
		   return gender;
	   }
	   
	   public void setGender(String gender) {
		   this.gender = gender;
	   }
	   
	   // Method that inserts patient data into the database
	   
	   public void insertIntoDatabase() {
		   try {
			   Connection connection = Database.connectDB();
			   PreparedStatement stmt = connection.prepareStatement("INSERT INTO patient (patient_id, name, email, username, password, phone_number, date_of_birth, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); {
		             stmt.setInt(1, patientId);
		             stmt.setString(2, name);
		             stmt.setString(3, email);
		             stmt.setString(4, username);
		             stmt.setString(5, password);
		             stmt.setString(6, phoneNumber);
		             stmt.setString(7, dateOfBirth);
		             stmt.setString(8, gender);
			   }
			   		 stmt.executeUpdate(); // makes sure that the database gets updated
			   }
			   catch(SQLException e) 
			   {
			   e.printStackTrace();
			   }   
	   }
	   // Method to update the elements of the database
	   
	   public void updatePatientDatabase(){
				try {
					Connection connection = Database.connectDB();
					PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM patient WHERE patient_id = ?");
				    selectStmt.setInt(1, this.patientId);
				    ResultSet rs = selectStmt.executeQuery();
				    // Updates each element inside the database
					if(rs.next()) {
						 if (name != null) {
					            rs.updateString("name", name);
					        }
					        
					        if (email != null) {
					            rs.updateString("email", email);
					        }
					        
					        if (username != null) {
					            rs.updateString("username", username);
					        }
					        
					        if (password != null) {
					            rs.updateString("password", password);
					        }
					        
					        if (phoneNumber != null) {
					            rs.updateString("phoneNumber", this.phoneNumber);
					        }

					        if (dateOfBirth != null) {
					            rs.updateString("dateOfBirth", this.dateOfBirth);
					        }

					        if (gender != null) {
					            rs.updateString("gender", this.gender);
					        }
					}

					rs.updateRow();	//Confirms updates to the database
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			} 
	   	public void getPatientInfo(int patientID)
	   	{
	   		try {
	   			// Establishes connection to the database and retrieves the information for the specific patient
	   			Connection connection = Database.connectDB();
		        String query = "SELECT name, email, username, password, phoneNumber, dateOfBirth, gender FROM patient WHERE patient_id = ?";
		        PreparedStatement stmt = connection.prepareStatement(query);
		        stmt.setInt(1, patientID);
		        ResultSet rs = stmt.executeQuery();
		        this.patientId = patientID;
		        if (rs.next()) {
		            this.name = rs.getString("name");
		            this.email = rs.getString("email");
		            this.username = rs.getString("username");
		            this.password = rs.getString("password");
		            this.phoneNumber = rs.getString("phoneNumber");
		            this.dateOfBirth = rs.getString("dateOfBirth");
		            this.gender = rs.getString("gender");
		        }
	   			
	   		}catch(SQLException e) {
	   			e.printStackTrace();
	   		}
	   		
	   	}
	   	
	   	// Method to create a list of all appointments that the current patient is a part of
	   	// Requires patient_id to be set
	   	public ArrayList<Integer> getAppointmentIDs()
	   	{
	   		Connection connection = Database.connectDB();
	   		ArrayList<Integer> appointmentIdList = new ArrayList<>();
	   		try {
	   			PreparedStatement statement = connection.prepareStatement("SELECT appointment_id FROM appointment WHERE patient_id = ?");
	   			statement.setInt(1, this.patientId);
	   			ResultSet resultSet = statement.executeQuery();
	   			while (resultSet.next()) {
	   				appointmentIdList.add(resultSet.getInt("appointment_id"));
	   			}
	   			resultSet.close();
	   			statement.close();
	   			connection.close();
	   		}catch(SQLException e)
	   		{
	   			e.printStackTrace();
	   		}
	   		return appointmentIdList;
	   		
	   	}
		   
		   
		   
		   
}