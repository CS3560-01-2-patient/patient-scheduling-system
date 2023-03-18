public class Patient {
    
    private string name;
    private int idNumber;
    private int phoneNumber;
    private string emailAddress;
    private int socialSecurityNumber;
    private int healthInsuranceNumber
    
    public string getPatientName() {
        return name;
    }
    
    public void setPatientName(string newValue) {
        name = newValue;
    } 
    
    public string getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(int newValue) {
        idNumber = newValue;
    }   
    
    public string getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(int newValue) {
        phoneNumber = newValue;
    }    
    
    public string getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(string newValue) {
        emailAddress = newValue;
    }    
    
    public string getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    
    public void setSocialSecurityNumber(int newValue) {
        socialSecurityNumber = newValue;
    }    
    
    public string getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }
    
    public void setHealthInsuranceNumber(int newValue) {
        healthInsuranceNumber = newValue;
    }
    
}
