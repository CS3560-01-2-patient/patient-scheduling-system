package application;
import java.sql.*;

public class Physician {
    private int physicianId;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String specialty;
    
    // All of the constructors
    public Physician()
    {
    	
    }
    
    public Physician(int physicianId)
    {
    	this.getPhysicianInfo(physicianId);
    }

    public Physician(int physicianId, String name, String email, String phoneNumber, String gender, String specialty) {
        this.physicianId = physicianId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.specialty = specialty;
    }
    
        
    // All of the physician's getters and setters
    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    // Since the physicians are going to be mock data, only need methods to gather information from the database
    
    public void getPhysicianInfo(int physicianID)
   	{
   		try {
   			// Establishes connection to the database and retrieves the information for the specific physician
   			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pss_schema", "root", "mypassword");
	        String query = "SELECT name, email, phoneNumber, dateOfBirth, gender, specialty FROM physician WHERE physician_id = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setInt(1, physicianID);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            this.name = rs.getString("name");
	            this.email = rs.getString("email");
	            this.phoneNumber = rs.getString("phoneNumber");
	            this.gender = rs.getString("gender");
	            this.specialty = rs.getString("specialty");
	        }
   			
   		}catch(SQLException e) {
   			e.printStackTrace();
   		}
   		
   	}
}