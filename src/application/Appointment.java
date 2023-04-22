package application;
import java.sql.*;

public class Appointment {
    private int appointment_id;
    private int patient_id;
    private int physician_id;
    private String appointment_date;
    private String appointment_time;
    private String treatment;

    public Appointment(int appointmentId, int patient_id, int physician_id, String appointment_date, String appointment_time, String treatment) {
        this.appointment_id = appointmentId;
        this.patient_id = patient_id;
        this.physician_id = physician_id;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.treatment = treatment;
    }
    
    // All of the appointment's getters and setters
    
    public int getAppointmentId() {
        return appointment_id;
    }


    public int getPatientId() {
        return patient_id;
    }


    public int getPhysicianId() {
        return physician_id;
    }
    

    public String getAppointmentDate() {
        return appointment_date;
    }
    
    
    public String getAppointmentTime() {
        return appointment_time;
    }
    
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    // Inserts created appointment into the database
    public void insertIntoDatabase() {
		   try {
			   Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pss_schema", "root", "mypassword");
			   PreparedStatement stmt = connection.prepareStatement("INSERT INTO appointment (appointment_id, patient_id, physician_id, appointment_date, appointment_time, treatment) VALUES (?, ?, ?, ?, ?, ?, ?)"); {
			         stmt.setInt(1, appointment_id);
			         stmt.setInt(2, patient.getPatientId());
			         stmt.setInt(3, physician.getPhysicianId());
			         stmt.setString(4, appointment_Date);
			         stmt.setString(5, appointment_Time);
			         stmt.setString(6, treatment);
			   }
			   		 stmt.executeUpdate(); // makes sure that the database gets updated
			   }
			   catch(SQLException e) 
			   {
			   e.printStackTrace();
			   }   
	   }
    
    // For an already existing Appointment in the database based off of appointmen_id get all of its information
    public void getAppointmentInfo(int appointment_Id)
   	{
   		try {
   			// Establishes connection to the database and retrieves the information for the specific appointment
   			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pss_schema", "root", "mypassword");
	        String query = "SELECT appointment_id, patient_id, physician_id, appointment_date, appointment_time, status, treatment FROM appointment WHERE appointment_id = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setInt(1, appointment_Id);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	        	this.appointment_id = rs.getInt("appointment_id");
	        	Patient pat = new Patient(rs.getInt("patient_id")); // Generates an instance of the patient class based off of the data in the database
	            this.patient = pat;
	            Physician phys = new Physician(rs.getInt("physician_id")); // Generates an instance of the physician class based off the data in the database
	            this.physician = phys;
	            this.appointment_Date = rs.getString("appointment_date");
	            this.appointment_Time = rs.getString("appointment_time");
	            this.treatment = rs.getString("treatment");
	        }
   			
   		}catch(SQLException e) {
   			e.printStackTrace();
   		}
   		
   	} 
}