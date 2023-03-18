public class Appointment {

    private String date;
    private String time;
    private String treatment;
    private String status;

     /**
	 * getDate method
	 * @return The appointment date
	 */
    public String getDate()
    {
        return date;
    }

    /**
	 * setDate method
	 * @param date The date of the appointment 
	 */
    public String setDate(String appointmentDate)
    {
        date = appointmentDate;
    }


    /**
	 * getTime method
	 * @return The appointment time
	 */
    public String getTime()
    {
        return time;
    }

    /**
	 * setTime method
	 * @param time The time of the appointment 
	 */
    public String setTime(String appointmentTime)
    {
        time = appointmentTime;
    }

    /**
	 * getTreatment method
	 * @return The treatment diagnosis from the appointment
	 */
    public String getTreatment()
    {
        return treatment;
    }

    /**
	 * setTreatment method
	 * @param treat The treatment given from appointment
	 */
    public String setTreatment(String treat)
    {
        treatment = treat;
    }

    /**
	 * getStatus method
	 * @return The appointment status
	 */
    public String getStatus()
    {
        return status;
    }

    /**
	 * setStatus method
	 * @param appointmentStatus the status of the appointment
	 */
    public String setTime(String appointmentStatus)
    {
        status = appointmentStatus;
    }

}
