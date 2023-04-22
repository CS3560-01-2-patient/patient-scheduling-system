package application;

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

	// All of the patient's getters 
	public int getPatientId() {
	      return patientId;
	   }

	   public void setPatientId(int patientId) {
	      this.patientId = patientId;
	   }

	   public String getName() {
	      return name;
	   }


	   public String getEmail() {
	      return email;
	   }


	   public String getUsername() {
	      return username;
	   }


	   public String getPassword() {
	      return password;
	   }

	   public String getPhoneNumber() {
		  return phoneNumber;
	   }


	   public String getDateOfBirth() {
		   return dateOfBirth;
	   }


	   public String getGender() {
		   return gender;
	   }

}
