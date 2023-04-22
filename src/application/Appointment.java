package application;
import java.sql.*;

public class Appointment {
    private int appointment_id;
    private Patient patient;
    private Physician physician;
    private String appointment_Date;
    private String appointment_Time;
    private String treatment;

    public Appointment(int appointmentId, Patient patient, Physician physician, String appointmentDate, String appointmentTime, String status, String treatment) {
        this.appointment_id = appointmentId;
        this.patient = patient;
        this.physician = physician;
        this.appointment_Date = appointmentDate;
        this.appointment_Time = appointmentTime;
        this.treatment = treatment;
    }

    // All of the appointment's getters and setters

    public int getAppointmentId() {
        return appointment_id;
    }

    public Patient getPatient() {
        return patient;
    }


    public Physician getPhysician() {
        return physician;
    }

    public String getAppointmentDate() {
        return appointment_Date;
    }


    public String getAppointmentTime() {
        return appointment_Time;
    }


    public String getTreatment() {
        return treatment;
    }

 


   	 
}