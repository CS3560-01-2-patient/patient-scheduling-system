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
    
}