package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage implements Initializable{
   
    @FXML
    private Label patientIdLabel;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    private Label passwordLabel;

    @FXML
    private Label phoneNumLabel;
    
    @FXML
    private Label dobLabel;

    @FXML
    private Label genderLabel;
    
    @FXML
    private Button editProfileBtn;
    
    @FXML
    private Button appointmentBtn;
    
    @FXML
    private Button viewProfileBtn;
      
    @FXML
    private Button appointment_createBtn;

    @FXML
    private DatePicker appointment_date;

    @FXML
    private Button appointment_deleteBtn;

    @FXML
    private AnchorPane appointment_form;
    
    @FXML
    private AnchorPane updateAppt_form;

    @FXML
    private DatePicker appointment_newDate;

    @FXML
    private ChoiceBox<?> appointment_newPhys;

    @FXML
    private ChoiceBox<?> appointment_newTime;

    @FXML
    private TextArea appointment_newTreatment;

    @FXML
    private ChoiceBox<?> appointment_time;
    
    @FXML
    private ChoiceBox<?> appointment_physician;

    @FXML
    private TextArea appointment_treatment;

    @FXML
    private Button appointment_updateBtn;
    
    @FXML
    private Button appointment_updateBtn1;

    @FXML
    private Button logoutBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button profile_deleteBtn;

    @FXML
    private DatePicker profile_dob;

    @FXML
    private TextField profile_email;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private ChoiceBox<?> profile_gender;
    
    @FXML
    private TextField profile_name;

    @FXML
    private TextField profile_password;

    @FXML
    private TextField profile_phoneNum;

    @FXML
    private Button profile_updateBtn;

    @FXML
    private TextField profile_username;
	
    @FXML
    private AnchorPane userInfo_form;
    
    @FXML
    private TableColumn<Appointment, String> appt_col_date;

    @FXML
    private TableColumn<Appointment, String> appt_col_patient;

    @FXML
    private TableColumn<Appointment, String> appt_col_physician;

    @FXML
    private TableColumn<Appointment, String> appt_col_time;

    @FXML
    private TableColumn<Appointment, String> appt_col_treatment;

    @FXML
    private TableView<Appointment> appt_tableView;
    
    private Main mainController;      
    
    private String gender[] = {"Male", "Female", "Other"};
    private String appointmentTime[]  = {"9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM", "5:00PM"};
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    //sets the reference to the Main class to access its methods
	public  void setMainController(Main mainController) {
		this.mainController = mainController;	
	}
	
	//adds all the rows in appointment database to appointmentListData
	public ObservableList<Appointment> appointmentDataList(){
	    ObservableList<Appointment> listData = FXCollections.observableArrayList();
	    
	    String sql = "SELECT a.*, p.name AS physician_name FROM appointment a JOIN physician p ON a.physician_id = p.physician_id";
	    
	    connect = Database.connectDB();
	    try {
	        prepare = connect.prepareStatement(sql);
	        result = prepare.executeQuery();
	        
	        Appointment appointments;
			
	        //Appointment object created for each row 
	        while(result.next()) {
	        	Patient patient = new Patient(result.getInt("patient_id"));
	            Physician physician = new Physician(result.getInt("physician_id"));
	            appointments = new Appointment(result.getInt("appointment_id"), patient,
	                    physician, result.getString("appointment_date"), result.getString("appointment_time"), 
	                            result.getString("treatment"));
	            listData.add(appointments);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return listData;
	}
	
	private ObservableList<Appointment> appointmentListData;
	
	//retrieves list of Appointment objects and set ups table columns
	public void showAppointments() {
		appointmentListData = appointmentDataList();
		
		appt_col_patient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getName()));
		appt_col_physician.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhysician().getName()));
		appt_col_date.setCellValueFactory(new PropertyValueFactory<>("AppointmentDate"));
		appt_col_time.setCellValueFactory(new PropertyValueFactory<>("AppointmentTime"));
		appt_col_treatment.setCellValueFactory(new PropertyValueFactory<>("Treatment"));
		
		appt_tableView.setItems(appointmentListData);		
	}
	
	//gets the selected Appointment object from the table
	public void selectAppointment() {
		Appointment appointments = appt_tableView.getSelectionModel().getSelectedItem();
		int num = appt_tableView.getSelectionModel().getSelectedIndex();
		
		//checks if no appointment is selected
		if((num - 1) < -1) {
			return;
		}
		appointment_treatment.setText(appointments.getTreatment());
		appointment_date.setValue(LocalDate.parse(String.valueOf(appointments.getAppointmentDate())));
		
	}
    
	//patient clicks create appointment button
    public void createAppointment() {
    	String createAppointmentSql = "INSERT INTO appointment (patient_id, physician_id, appointment_date, appointment_time, treatment) VALUES (?, ?, ?, ?, ?)";
    	connect = Database.connectDB();
    	
    	try {
    		if(appointment_physician.getSelectionModel().getSelectedItem() == null || appointment_date.getValue() == null 
    				|| appointment_time.getSelectionModel().getSelectedItem() == null || appointment_treatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {
    			Patient patient = mainController.getPatient();
    			int patientId = patient.getPatientId();    			
    	    	int physicianID = getPhysicianID();
    	    	
    	    	String checkAppointmentSql = "SELECT * FROM appointment WHERE appointment_date = ? AND appointment_time = ? AND physician_id = ?";
    	    	prepare = connect.prepareStatement(checkAppointmentSql);
    	    	prepare.setString(1, String.valueOf(appointment_date.getValue()));
    	    	prepare.setString(2, (String)appointment_time.getSelectionModel().getSelectedItem());
    	    	prepare.setInt(3, physicianID);
    	    	result = prepare.executeQuery();
    	    	
    	    	if(result.next()) { //checks if an appointment already exists with that date, time, and physician
    	    		Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Failed to create Appointment");
            		alert.setContentText("Sorry! This appointment date and time is already taken with that physician!");
            		alert.showAndWait();
            			
    	    	}
    	    	else {		
             		prepare = connect.prepareStatement(createAppointmentSql);
        	    	prepare.setInt(1,  patientId);
        	    	prepare.setInt(2, physicianID);
        	    	prepare.setString(3, String.valueOf(appointment_date.getValue()));
        	    	prepare.setString(4, (String)appointment_time.getSelectionModel().getSelectedItem());
        	    	prepare.setString(5, appointment_treatment.getText());
        				
        	    	Alert alert = new Alert(AlertType.INFORMATION);
        	    	alert.setTitle("Appointment Created");
            		alert.setContentText("You have created an appointment!");
            		alert.showAndWait();
            		prepare.executeUpdate();
            		showAppointments();
            		clearAppointment();  	    			
    	    	}
    		}
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
	//patient clicks update appointment button
    public void showUpdateApptForm() {
    	Patient patient = mainController.getPatient();
		int patientId = patient.getPatientId();
    	int physicianID = getPhysicianID();
    	
    	String checkAppointmentSql = "SELECT * FROM appointment WHERE patient_id = ? AND appointment_date = ? AND appointment_time = ? AND physician_id = ? AND treatment = ?";
    	connect = Database.connectDB();
  	
    	try {
    		if(appointment_physician.getSelectionModel().getSelectedItem() == null || appointment_date.getValue() == null 
    				|| appointment_time.getSelectionModel().getSelectedItem() == null || appointment_treatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {				
    	    	prepare = connect.prepareStatement(checkAppointmentSql);
    	    	prepare.setInt(1, patientId);
				prepare.setString(2, String.valueOf(appointment_date.getValue()));
    	    	prepare.setString(3, (String)appointment_time.getSelectionModel().getSelectedItem());
    	    	prepare.setInt(4, physicianID);
    	    	prepare.setString(5, appointment_treatment.getText());
    	    	result = prepare.executeQuery();
    	    	
    			if(result.next()) { //checks if an appointment exists for that patient with that date, time, and physician
    				clearUpdatedAppointment();
    		    	updateAppt_form.setVisible(true);
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Appointment does not exist");
        			alert.setContentText("Please enter or select your appointment listed on the table to update");
        			alert.showAndWait();
    			} 			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    	
    
    //patient clicks update appointment button from update appointment screen
    public void updateAppointment() {
    	int newPhysicianID = getUpdatedPhysicianID();
    	int appointmentId = getAppointmentID();
    	
    	String updateAppointmentSql = "UPDATE appointment SET physician_id = ?, appointment_date = ?, appointment_time = ?, treatment = ? "
    			+ "WHERE appointment_id = ?";
    	connect = Database.connectDB();
  	
    	try {
    		if(appointment_newPhys.getSelectionModel().getSelectedItem() == null || appointment_newDate.getValue() == null 
    				|| appointment_newTime.getSelectionModel().getSelectedItem() == null || appointment_newTreatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {	  			
    	    	String checkAppointmentSql = "SELECT * FROM appointment WHERE appointment_date = ? AND appointment_time = ? AND physician_id = ?";
    	    	prepare = connect.prepareStatement(checkAppointmentSql);
				prepare.setString(1, String.valueOf(appointment_newDate.getValue()));
    	    	prepare.setString(2, (String)appointment_newTime.getSelectionModel().getSelectedItem());
    	    	prepare.setInt(3, newPhysicianID);
    	    	result = prepare.executeQuery();
    	    	
    			if(result.next()) { //checks if an appointment already exists with that date, time, and physician
    				Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Failed to update Appointment");
            		alert.setContentText("Sorry! This appointment date and time is already taken with that physician!");
            		alert.showAndWait();
    			}
    			else {           		            
        			prepare = connect.prepareStatement(updateAppointmentSql);
        	    	prepare.setInt(1, newPhysicianID);
    				prepare.setString(2, String.valueOf(appointment_newDate.getValue()));
        	    	prepare.setString(3, (String)appointment_newTime.getSelectionModel().getSelectedItem());
        	    	prepare.setString(4, appointment_newTreatment.getText());
        	    	prepare.setInt(5, appointmentId);

        	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Appointment Updated");
        			alert.setContentText("You have updated your appointment!");
        			alert.showAndWait();
        			prepare.executeUpdate();
        			showAppointments();
        			clearAppointment();
    		    	updateAppt_form.setVisible(false);

    			} 			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //patient clicks delete appointment button 
    public void deleteAppointment() {
    	Patient patient = mainController.getPatient();
		int patientId = patient.getPatientId();
    	int physicianID = getPhysicianID();

    	String deleteAppointmentSql = "DELETE FROM appointment WHERE patient_id = ? AND appointment_date = ? AND appointment_time = ? AND physician_id = ?";
       	connect = Database.connectDB();
       
    	try {
    		if(appointment_physician.getSelectionModel().getSelectedItem() == null || appointment_date.getValue() == null 
    				|| appointment_time.getSelectionModel().getSelectedItem() == null || appointment_treatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {  			
    	    	String checkAppointmentSql = "SELECT * FROM appointment WHERE patient_id = ? AND appointment_date = ? AND appointment_time = ? AND physician_id = ? AND treatment = ?";
    	    	prepare = connect.prepareStatement(checkAppointmentSql);
    	    	prepare.setInt(1, patientId);
				prepare.setString(2, String.valueOf(appointment_date.getValue()));
    	    	prepare.setString(3, (String)appointment_time.getSelectionModel().getSelectedItem());
    	    	prepare.setInt(4, physicianID);
    	    	prepare.setString(5, appointment_treatment.getText());
    	    	result = prepare.executeQuery();

    			if(result.next()) { //checks if an appointment exists for that patient with that date, time, and physician   				
    				prepare = connect.prepareStatement(deleteAppointmentSql);
    				prepare.setInt(1, patientId);
    				prepare.setString(2, String.valueOf(appointment_date.getValue()));
        	    	prepare.setString(3, (String)appointment_time.getSelectionModel().getSelectedItem());
        	    	prepare.setInt(4, physicianID);
        	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Appointment deleted");
        			alert.setContentText("You have deleted your appointment!");
        			alert.showAndWait();
        			
        			prepare.executeUpdate();
        			showAppointments();
        			clearAppointment();
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Appointment does not exist");
        			alert.setContentText("Please enter or select your appointment listed on the table to delete");
        			alert.showAndWait();
    			} 	
    			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //clears appointment input fields
    public void clearAppointment() {	
    	appointment_date.setValue(null);
    	appointment_time.getSelectionModel().clearSelection();
    	appointment_physician.getSelectionModel().clearSelection();
    	appointment_treatment.setText("");;
    }
    
    public void clearUpdatedAppointment() {
    	appointment_newDate.setValue(null);
    	appointment_newTime.getSelectionModel().clearSelection();
    	appointment_newPhys.getSelectionModel().clearSelection();
    	appointment_newTreatment.setText("");;
    }
    
    public void cancelUpdateAppt() {
		clearAppointment();
    	updateAppt_form.setVisible(false);
    }
    
    //patient clicks update patient button
    public void updatePatientInfo() {
    	Patient patient = mainController.getPatient();
		int patientId = patient.getPatientId();
		
    	String updatePatientSql = "UPDATE patient SET name = ?, email = ?, username = ?, password = ?, phoneNumber = ?, dateOfBirth = ?, gender = ? WHERE patient_id = ?";
       	connect = Database.connectDB();

    	try {
    		if(profile_name.getText().isEmpty() || profile_email.getText().isEmpty() 
    				|| profile_username.getText().isEmpty() || profile_password.getText().isEmpty()  
    				|| profile_phoneNum.getText().isEmpty() || profile_dob.getValue() == null  
    				|| profile_gender.getSelectionModel().getSelectedItem() == null) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {	
    			String checkUsernameExistsSql = ("SELECT * FROM patient WHERE username = ?");
    			prepare = connect.prepareStatement(checkUsernameExistsSql);
    			prepare.setString(1, profile_username.getText());
    			result = prepare.executeQuery();
    			
    			if(result.next()) { //check if username already exists in database
    				Alert alert = new Alert(Alert.AlertType.ERROR);
        			alert.setTitle("Failed to update account information!");
    				alert.setContentText("Sorry! This username already exists!");
    				alert.showAndWait();
    			}
    			else {
    				prepare = connect.prepareStatement(updatePatientSql);
    				prepare.setString(1, profile_name.getText());
    				prepare.setString(2, profile_email.getText());
        	    	prepare.setString(3, profile_username.getText());
        	    	prepare.setString(4, profile_password.getText());
        	    	prepare.setString(5, profile_phoneNum.getText());
    				prepare.setString(6, String.valueOf(profile_dob.getValue()));
    				prepare.setString(7, (String)profile_gender.getSelectionModel().getSelectedItem());
    				prepare.setInt(8, patientId);
    				prepare.executeUpdate();
        	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Account Updated");
        			alert.setContentText("You have updated your account!");
        			alert.showAndWait();
        			
        			setUserInfo(patientId, profile_name.getText(), profile_email.getText(), profile_username.getText(), 
        					profile_password.getText(), profile_phoneNum.getText(), String.valueOf(profile_dob.getValue()), 
        					(String)profile_gender.getSelectionModel().getSelectedItem());
        			
        			patient.updatePatientInfo(profile_name.getText(), profile_email.getText(), profile_username.getText(), 
        					profile_password.getText(), profile_phoneNum.getText(), String.valueOf(profile_dob.getValue()), 
        					(String)profile_gender.getSelectionModel().getSelectedItem()); 
        			
        			clearPatientInfo();    				
    			}
  				
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    //patient clicks delete patient button
    public void deletePatient() {
    	Patient patient = mainController.getPatient();
		int patientId = patient.getPatientId();
       	String deletePatientSql = "DELETE FROM patient where patient_id = ?";
       	connect = Database.connectDB();
    	try {
    		Alert confirmDeletion = new Alert(AlertType.CONFIRMATION);
    		confirmDeletion.setTitle("Awaiting confirmation");
    		confirmDeletion.setContentText("This will permanently delete your account. Would you like to proceed?");
    		Optional<ButtonType> deleteOption = confirmDeletion.showAndWait();
    		
    		if(deleteOption.get().equals(ButtonType.OK)) {
    			String checkAppointmentSql = "SELECT * FROM appointment where patient_id = ?";
    			prepare = connect.prepareStatement(checkAppointmentSql);
        	    prepare.setString(1, String.valueOf(patientId));
        	    result = prepare.executeQuery();
        	    
        	    //will delete all the appointments the patient has, before deleting the patient
        	    if(result.next()) { 
        	    	String deleteAppointmentSql = "DELETE FROM appointment where patient_id = ?";
        	    	prepare = connect.prepareStatement(deleteAppointmentSql);
        	    	prepare.setInt(1, patientId);
                	prepare.executeUpdate();
        	    }
        	    		
    			prepare = connect.prepareStatement(deletePatientSql);
    			prepare.setString(1, String.valueOf(patientId));
            	prepare.executeUpdate();
            	Alert alert = new Alert(AlertType.INFORMATION);
        	    alert.setTitle("Account deleted");
            	alert.setContentText("You have deleted your account!");
            	alert.showAndWait();

            	profile_deleteBtn.getScene().getWindow().hide();
            			
            	Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        		Stage stage = new Stage();
        		Scene scene = new Scene(root);
        		stage.setScene(scene);
        		stage.show();     			
    		}		
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //clears patient info fields
    public void clearPatientInfo() {
    	profile_name.setText("");
    	profile_email.setText("");
    	profile_username.setText("");
    	profile_password.setText("");
    	profile_phoneNum.setText("");
    	profile_dob.setValue(null);
    	profile_gender.getSelectionModel().clearSelection();  	
    }
    
    //switches screen when patient clicks "View Profile", "Edit Profile", and "Appointments" button
    public void switchForm(ActionEvent event) {
    	if(event.getSource() == appointmentBtn) {
    		appointment_form.setVisible(true);
    		profile_form.setVisible(false);
    		userInfo_form.setVisible(false);    		
    		appointmentTimeList();
    		appointmentPhysicianList();
    	}
    	else if(event.getSource() == editProfileBtn) {
    		profile_form.setVisible(true);
    		appointment_form.setVisible(false);
    		userInfo_form.setVisible(false);
    		genderList();
    	}
    	else if(event.getSource() == viewProfileBtn) {
    		userInfo_form.setVisible(true);
    		profile_form.setVisible(false);
    		appointment_form.setVisible(false);   		
    	}
    	
    }
    
    //takes patient back to login screen when click log out button
    public void logout() throws IOException {
    	logoutBtn.getScene().getWindow().hide();
    	
    	Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
    	
    	Stage stage = new Stage();
    	Scene scene = new Scene(root);
    	
    	stage.setScene(scene);
    	stage.show();
    }
    
    //gets the all the physicians names in the physician table
    public List<String> getPhysicians() {
    	String physicianNamesSql = "SELECT name FROM physician";
       	connect = Database.connectDB();
       	List<String> physicianList = new ArrayList<>();
		try {
			statement = connect.createStatement();
			result = statement.executeQuery(physicianNamesSql);
			while(result.next()) {
				String name = result.getString("name");
				physicianList.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return physicianList;
    }
    
    //gets the physician id from selected physician choice box
    public int getPhysicianID() {
    	String physicianIdSql = "SELECT physician_id FROM physician WHERE name = ?";
		try {
			prepare = connect.prepareStatement(physicianIdSql);
    		prepare.setString(1, (String)appointment_physician.getSelectionModel().getSelectedItem());
    		result = prepare.executeQuery();
    		if(result.next())
    		{
    			int physicianID = result.getInt("physician_id");
        		return physicianID;
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    //gets the updated physician id from selected physician choice box
    public int getUpdatedPhysicianID() {
    	String physicianIdSql = "SELECT physician_id FROM physician WHERE name = ?";
		try {
			prepare = connect.prepareStatement(physicianIdSql);
    		prepare.setString(1, (String)appointment_newPhys.getSelectionModel().getSelectedItem());
    		result = prepare.executeQuery();
    		if(result.next())
    		{
    			int physicianID = result.getInt("physician_id");
        		return physicianID;
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    //gets the appointment id 
    public int getAppointmentID() {
    	Patient patient = mainController.getPatient();
		int patientId = patient.getPatientId();
    	int physicianID = getPhysicianID();
    	String appointmentIdSql = "SELECT appointment_id FROM appointment WHERE patient_id = ? AND appointment_date = ? AND appointment_time = ? AND physician_id = ?";
		try {
			prepare = connect.prepareStatement(appointmentIdSql);
 	    	prepare.setInt(1, patientId);
			prepare.setString(2, String.valueOf(appointment_date.getValue()));
    	    prepare.setString(3, (String)appointment_time.getSelectionModel().getSelectedItem());
    	    prepare.setInt(4, physicianID);    		result = prepare.executeQuery();
    		if(result.next())
    		{
    			int appointmetnId = result.getInt("appointment_id");
        		return appointmetnId;
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    // lists for choice box
    public void appointmentTimeList() {
    	List<String> timeList = new ArrayList<>();
    	
    	for(int i = 0; i < appointmentTime.length; i++) {
    		timeList.add(appointmentTime[i]);
    	}
    	ObservableList listData = FXCollections.observableArrayList(timeList);
    	appointment_time.setItems(listData);
    	appointment_newTime.setItems(listData);
    }
    
    public void genderList() {
    	List<String> genderList = new ArrayList<>();
    	
    	for(int i = 0; i < gender.length; i++) {
    		genderList.add(gender[i]);
    	}	
    	ObservableList listData = FXCollections.observableArrayList(genderList);
    	profile_gender.setItems(listData);
    }
    
    public void appointmentPhysicianList() {
    	List<String> physicianList = getPhysicians();
    	ObservableList listData = FXCollections.observableArrayList(physicianList);
    	appointment_physician.setItems(listData);
    	appointment_newPhys.setItems(listData);
    }
    
    //sets user info in the view profile screen
	public void setUserInfo(int patientID, String name, String email, String username, String password, String phone, String dateOfBirth, String gender) {
		patientIdLabel.setText("ID: " + String.valueOf(patientID) );
		nameLabel.setText("Name: " +  name);
		emailLabel.setText("Email: " +  email);
		usernameLabel.setText("Username: " +  username);
		passwordLabel.setText("Password: " +  password);
		phoneNumLabel.setText("Phone Number: " +  phone);
		dobLabel.setText("Date of Birth: " +  dateOfBirth);
		genderLabel.setText("Gender: " +  gender);			
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		appointmentTimeList();
		appointmentPhysicianList();
		genderList();
		
		showAppointments();
	}


}
