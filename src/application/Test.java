package application;

public class Test {
	public static void go() {
		Patient pat = new Patient(12);
		System.out.println(pat.getPatientId());
		System.out.println(pat.getName());
		System.out.println(pat.getEmail());
		System.out.println(pat.getUsername());
		System.out.println(pat.getGender());
		System.out.println(pat.getAppointmentIDs());
		System.out.println("");
		Appointment app = new Appointment(1001);
		System.out.println(app.getPatient().getPatientId());
		
		Physician phys = new Physician(11);
		System.out.println(phys.getPhysicianId());
		
	}
	
}
