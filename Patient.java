public class Patient {
    
    private String name;
    private int idNumber;
    private String phoneNumber;
    private String emailAddress;
    private String socialSecurityNumber;
    private String healthInsuranceNumber;
    
    /**
	 * getPatientName method
	 * @return The patient's name
	 */
    public String getPatientName() {
        return name;
    }
    
    /**
	 * setPatientName method
	 * @param patientName The name of the patient
	 */
    public void setPatientName(String patientName) {
        name = patientName;
    } 
    
    /**
	 * getIdNumber method
	 * @return The patient's id
	 */
    public int getIdNumber() {
        return idNumber;
    }
    
    /**
	 * setIdNumber method
	 * @param patientID The id of the patient
	 */
    public void setIdNumber(int patientID) {
        idNumber = patientID;
    }   
    
    /**
	 * getPhoneNumber method
	 * @return The patient's phone number
	 */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
	 * setPhoneNumber method
	 * @param patientPhoneNum 
	 */
    public void setPhoneNumber(String patientPhoneNum) {
        phoneNumber = patientPhoneNum;
    }    
    
    /**
	 * getEmailAddress method
	 * @return the patient's email address
	 */
    public String getEmailAddress() {
        return emailAddress;
    }
    
    /**
	 * setEmailAddress method
	 * @param patientEmail the email of the patient
	 */
    public void setEmailAddress(String patientEmail) {
        emailAddress = patientEmail;
    }    
    
    /**
	 * getSocialSecurityNumber method
	 * @return the patient's social security number
	 */
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    
    /**
	 * setSocialSecurityNumber method
	 * @param ssn the social security number of the patient
	 */
    public void setSocialSecurityNumber(String ssn) {
        socialSecurityNumber = ssn;
    }    
    
    /**
	 * getHealthInsuranceNumber method
	 * @return the patient's healthInsuranceNumber 
	 */
    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }
    
        /**
	 * setHealthInsuranceNumber method
	 * @param insuranceID the health insurance number of patient
	 */
    public void setHealthInsuranceNumber(String insuranceID) {
        healthInsuranceNumber = insuranceID;
    }
    
}
