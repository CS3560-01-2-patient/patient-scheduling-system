public class Patient {
    
    private String name;
    private int idNumber;
    private int phoneNumber;
    private String emailAddress;
    private int socialSecurityNumber;
    private int healthInsuranceNumber;
    
    public String getPatientName() {
        return name;
    }
    
    public void setPatientName(String patientName) {
        name = patientName;
    } 
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(int patientID) {
        idNumber = patientID;
    }   
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(int patientPhoneNum) {
        phoneNumber = patientPhoneNum;
    }    
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String patientEmail) {
        emailAddress = patientEmail;
    }    
    
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    
    public void setSocialSecurityNumber(int ssn) {
        socialSecurityNumber = ssn;
    }    
    
    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }
    
    public void setHealthInsuranceNumber(int insuranceID) {
        healthInsuranceNumber = insuranceID;
    }
    
}
