package application;

public class Physician {
    private int physicianId;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String specialty;


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

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getSpecialty() {
        return specialty;
    }

}