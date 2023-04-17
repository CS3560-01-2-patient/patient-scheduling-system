public class Physician {
    
    private String name;
    private int idNumber;
    private String emailAddress;
    private String specialty;
    
    /**
	 * getPhysicianName method
	 * @return The physician's name
	 */
    public String getPhysicianName() {
        return name;
    }
    
    /**
	 * setPhysicianName method
	 * @param physicianName The name of the physician
	 */
    public void setPhysicianName(String physicianName) {
        name = physicianName;
    } 
    /**
	 * getPhysicianNumber method
	 * @return The physician's ID number
	 */
    public String getPhysicianNumber() {
        return idNumber;
    }
    
    /**
	 * setPhysicianNumber method
	 * @param idNumber The ID of the physician
	 */
    public void setPhysicianNumber(String physicianID) {
        idNumber = physicianID;
    } 
    /**
	 * getPhysicianEmail method
	 * @return The physician's email
	 */
    public String getPhysicianEmail() {
        return emailAddress;
    }
    
    /**
	 * setPhysicianEmail method
	 * @param physicianEmail The email of the physician
	 */
    public void setPhysicianEmail(String physicianEmail) {
        emailAddress = physicianEmail;
    } 
    /**
	 * getPhysicianSpecialty method
	 * @return The physician's specialty
	 */
    public String getPhysicianSpecialty() {
        return specialty;
    }
    
    /**
	 * setPhysicianSpecialty method
	 * @param physicianSpecialty The specialty of the physician
	 */
    public void setPhysicianSpecialty(String physicianSpecialty) {
        specialty = physicianSpecialty;
    } 
    
    
    
}
