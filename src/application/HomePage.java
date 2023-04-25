package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField appointment_patientID;

    @FXML
    private ChoiceBox<?> appointment_time;
    
    @FXML
    private ChoiceBox<?> appointment_physician;

    @FXML
    private TextArea appointment_treatment;

    @FXML
    private Button appointment_updateBtn;

    @FXML
    private Button closeBtn;

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
    private TextField profile_patientID;

    @FXML
    private TextField profile_phoneNum;

    @FXML
    private Button profile_updateBtn;

    @FXML
    private TextField profile_username;
	
    @FXML
    private AnchorPane userInfo_form;
     
    
    private String gender[] = {"Male", "Female", "Other"};
    private String appointmentTime[]  = {"9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM", "5:00PM"};
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    
    public void createAppointment() {
    	String sql = "INSERT INTO appointment (patient_id, physician_id, appointment_date, appointment_time, treatment) VALUES (?, ?, ?, ?, ?)";
    	connect = Database.connectDB();
    	
    	try {
    		if(appointment_patientID.getText().isEmpty() || appointment_physician.getSelectionModel().getSelectedItem() == null 
    				|| appointment_physician.getSelectionModel().getSelectedItem() == null || appointment_date.getValue() == null 
    				|| appointment_time.getSelectionModel().getSelectedItem() == null || appointment_treatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {
    			boolean patientIdExists = false;
    			String patientIdSql = "SELECT patient_id FROM patient";
    			statement = connect.createStatement();
    			result = statement.executeQuery(patientIdSql);
    			while(result.next()) {
    				int patientIdDatabase = result.getInt("patient_id");
    				if(Integer.parseInt(appointment_patientID.getText()) == patientIdDatabase){
    					patientIdExists = true;
    					break;
    				}
    			}
    			
    			if(patientIdExists) {
    	    		int physicianID = getPhysicianID();
    	    		String checkAppointmentSql = "SELECT * FROM appointment where appointment_date = ? AND appointment_time = ? AND physician_id = ?";
    	    		prepare = connect.prepareStatement(checkAppointmentSql);
    	    		prepare.setString(1, String.valueOf(appointment_date.getValue()));
    	    		prepare.setString(2, (String)appointment_time.getSelectionModel().getSelectedItem());
    	    		prepare.setInt(3, physicianID);
    	    		result = prepare.executeQuery();
    	    		if(result.next()) {
    	    			Alert alert = new Alert(AlertType.ERROR);
            			alert.setTitle("Failed to create Appointment");
            			alert.setContentText("Sorry! This appointment date and time is already taken!");
            			alert.showAndWait();
            			
    	    		}
    	    		else {		
             			prepare = connect.prepareStatement(sql);
        	    		prepare.setInt(1,  Integer.parseInt(appointment_patientID.getText()));
        	    		prepare.setInt(2, physicianID);
        	    		prepare.setString(3, String.valueOf(appointment_date.getValue()));
        	    		prepare.setString(4, (String)appointment_time.getSelectionModel().getSelectedItem());
        	    		prepare.setString(5, appointment_treatment.getText());
        				
        	    		Alert alert = new Alert(AlertType.INFORMATION);
        	    		alert.setTitle("Appointment Created");
            			alert.setContentText("You have created an appointment!");
            			alert.showAndWait();
            			prepare.executeUpdate();
            			clearAppointment();  	    			
    	    		}
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Patient does not exist");
        			alert.setContentText("This patient does not exist.");
        			alert.showAndWait();
    			}
    		}
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void updateAppointment() {
    	connect = Database.connectDB();
    	int physicianID = getPhysicianID();
    	String sql = "UPDATE appointment SET physician_id = '" + physicianID  
    	+ "', appointment_date = '" + String.valueOf(appointment_date.getValue()) 
    	+ "',  appointment_time = '" + appointment_time.getSelectionModel().getSelectedItem() 
    	+ "',  treatment = '" + appointment_treatment.getText() 
    	+ "' WHERE patient_id = '"+ appointment_patientID.getText() + "'";
    	
    	try {
    		if(appointment_patientID.getText().isEmpty() || appointment_physician.getSelectionModel().getSelectedItem() == null 
    				|| appointment_physician.getSelectionModel().getSelectedItem() == null || appointment_date.getValue() == null 
    				|| appointment_time.getSelectionModel().getSelectedItem() == null || appointment_treatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {	
    			boolean appointPatientIdExists = false;
    			String sql1 = "SELECT patient_id FROM appointment";
    			statement = connect.createStatement();
    			result = statement.executeQuery(sql1);
    			while(result.next()) {
    				int patientIdDatabase = result.getInt("patient_id");
    				if(Integer.parseInt(appointment_patientID.getText()) == patientIdDatabase){
    					appointPatientIdExists = true;
    					break;
    				}
    			}
    			if(appointPatientIdExists) {
        			prepare = connect.prepareStatement(sql);
        			prepare.executeUpdate();
        	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Appointment Updated");
        			alert.setContentText("You have updated your appointment!");
        			alert.showAndWait();
        			clearAppointment();
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Appointment does not exist");
        			alert.setContentText("You have not created an apppointment yet!");
        			alert.showAndWait();
    			} 			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void deleteAppointment() {
       	String sql = "DELETE FROM appointment where patient_id = '" + appointment_patientID.getText() + "'";
       	connect = Database.connectDB();
    	try {
    		if(appointment_patientID.getText().isEmpty() || appointment_physician.getSelectionModel().getSelectedItem() == null 
    				|| appointment_physician.getSelectionModel().getSelectedItem() == null || appointment_date.getValue() == null 
    				|| appointment_time.getSelectionModel().getSelectedItem() == null || appointment_treatment.getText().isEmpty()) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {
    			boolean appointPatientIdExists = false;
    			String sql1 = "SELECT patient_id FROM appointment";
    			statement = connect.createStatement();
    			result = statement.executeQuery(sql1);
    			while(result.next()) {
    				int patientIdDatabase = result.getInt("patient_id");
    				if(Integer.parseInt(appointment_patientID.getText()) == patientIdDatabase){
    					appointPatientIdExists = true;
    					break;
    				}
    			}			
    			if(appointPatientIdExists) {
    				prepare = connect.prepareStatement(sql);
        			prepare.executeUpdate();
        	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Appointment deleted");
        			alert.setContentText("You have deleted your appointment!");
        			alert.showAndWait();
        			clearAppointment();
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Appointment does not exist");
        			alert.setContentText("You have not created an apppointment yet!");
        			alert.showAndWait();
    			} 	
    			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    public void clearAppointment() {
    	appointment_patientID.setText("");
    	appointment_date.setValue(null);
    	appointment_time.getSelectionModel().clearSelection();
    	appointment_physician.getSelectionModel().clearSelection();
    	appointment_treatment.setText("");;

    }
    
    public int getPhysicianID() {
    	String sql = "SELECT physician_id FROM physician WHERE name = ?";
		try {
			prepare = connect.prepareStatement(sql);
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
    
    public void updatePatientInfo() {
       	connect = Database.connectDB();
    	String sql = "UPDATE patient SET name = '" + profile_name.getText()   
    	+ "', email = '" + profile_email.getText()  
    	+ "',  username = '" + profile_username.getText() 
    	+ "',  password = '" + profile_password.getText() 
    	+ "',  phoneNumber = '" + profile_phoneNum.getText() 
    	+ "',  dateOfBirth = '" + String.valueOf(profile_dob.getValue()) 
    	+ "',  gender = '" + profile_gender.getSelectionModel().getSelectedItem() 
    	+ "' WHERE patient_id = '"+ profile_patientID.getText() + "'";
    	
    	try {
    		if(profile_patientID.getText().isEmpty() || profile_name.getText().isEmpty() || profile_email.getText().isEmpty() 
    				|| profile_username.getText().isEmpty() || profile_password.getText().isEmpty()  
    				|| profile_phoneNum.getText().isEmpty() || profile_dob.getValue() == null  
    				|| profile_gender.getSelectionModel().getSelectedItem() == null) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {	
    			boolean patientIdExists = false;
    			String sql1 = "SELECT patient_id FROM patient";
    			statement = connect.createStatement();
    			result = statement.executeQuery(sql1);
    			while(result.next()) {
    				int patientIdDatabase = result.getInt("patient_id");
    				if(Integer.parseInt(profile_patientID.getText()) == patientIdDatabase){
    					patientIdExists = true;
    					break;
    				}
    			}
    			if(patientIdExists) {
        			prepare = connect.prepareStatement(sql);
        			prepare.executeUpdate();
        	   		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Account Updated");
        			alert.setContentText("You have updated your account!");
        			alert.showAndWait();
        			setUserInfo(Integer.parseInt(profile_patientID.getText()), profile_name.getText(), profile_email.getText(), profile_username.getText(), 
        					profile_password.getText(), profile_phoneNum.getText(), String.valueOf(profile_dob.getValue()), 
        					(String)profile_gender.getSelectionModel().getSelectedItem());
        			clearPatientInfo();
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Account does not exist");
        			alert.setContentText("Be sure to enter your correct id");
        			alert.showAndWait();
    			} 			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public void deletePatient() {
       	String sql = "DELETE FROM patient where patient_id = '" + profile_patientID.getText() + "'";
       	connect = Database.connectDB();
    	try {
    		if(profile_patientID.getText().isEmpty() || profile_name.getText().isEmpty() || profile_email.getText().isEmpty() 
    				|| profile_username.getText().isEmpty() || profile_password.getText().isEmpty()  
    				|| profile_phoneNum.getText().isEmpty() || profile_dob.getValue() == null  
    				|| profile_gender.getSelectionModel().getSelectedItem() == null) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Missing fields");
    			alert.setContentText("Please make sure all fields are not blank");
    			alert.showAndWait();
    		}
    		else {
    			boolean patientIdExists = false;
    			String sql1 = "SELECT patient_id FROM patient";
    			statement = connect.createStatement();
    			result = statement.executeQuery(sql1);
    			while(result.next()) {
    				int patientIdDatabase = result.getInt("patient_id");
    				if(Integer.parseInt(profile_patientID.getText()) == patientIdDatabase){
    					patientIdExists = true;
    					break;
    				}
    			}		
    			if(patientIdExists) {
    				Alert confirmDeletion = new Alert(AlertType.CONFIRMATION);
    				confirmDeletion.setContentText("This will permanently delete your account. Would you like to proceed?");
    				Optional<ButtonType> deleteOption = confirmDeletion.showAndWait();
    				if(deleteOption.get().equals(ButtonType.OK)) {
    					prepare = connect.prepareStatement(sql);
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
//        				if(java.sql.SQLIntegrityConstraintViolationException) {
//        			       	String sql12 = "DELETE FROM appointment where patient_id = '" + appointment_patientID.getText() + "'";
//        			    	prepare = connect.prepareStatement(sql12);
//                			prepare.executeUpdate();
//
//        				}
        				
            			
    				}
    			
    			}
    			else {
    				Alert alert = new Alert(AlertType.ERROR);
    	    		alert.setTitle("Account does not exist");
        			alert.setContentText("You have not created an account yet!");
        			alert.showAndWait();
    			} 	
    			
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void clearPatientInfo() {
    	profile_patientID.setText("");
    	profile_name.setText("");
    	profile_email.setText("");
    	profile_username.setText("");
    	profile_password.setText("");
    	profile_phoneNum.setText("");
    	profile_dob.setValue(null);
    	profile_gender.getSelectionModel().clearSelection();
    	

    }
    
    public void switchForm(ActionEvent event) {
    	if(event.getSource() == appointmentBtn) {
    		appointment_form.setVisible(true);
    		profile_form.setVisible(false);
    		userInfo_form.setVisible(false);
    		
    		appointmentTimeList();
    	}
    	else if(event.getSource() == editProfileBtn) {
    		profile_form.setVisible(true);
    		appointment_form.setVisible(false);
    		userInfo_form.setVisible(false);
    	}
    	else if(event.getSource() == viewProfileBtn) {
    		userInfo_form.setVisible(true);
    		profile_form.setVisible(false);
    		appointment_form.setVisible(false);   		
    	}
    	
    }
    
    public void logout() throws IOException {
    	logoutBtn.getScene().getWindow().hide();
    	
    	Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
    	
    	Stage stage = new Stage();
    	Scene scene = new Scene(root);
    	
    	stage.setScene(scene);
    	stage.show();
    }
    
    public List<String> getPhysicians() {
    	String sql = "SELECT name FROM physician";
       	connect = Database.connectDB();
       	List<String> physicianList = new ArrayList<>();
		try {
			statement = connect.createStatement();
			result = statement.executeQuery(sql);
			while(result.next()) {
				String name = result.getString("name");
				physicianList.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return physicianList;

    }
    
    public void appointmentTimeList() {
    	List<String> timeList = new ArrayList<>();
    	
    	for(int i = 0; i < appointmentTime.length; i++) {
    		timeList.add(appointmentTime[i]);
    	}
    	ObservableList listData = FXCollections.observableArrayList(timeList);
    	appointment_time.setItems(listData);
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
    }
    
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
	
    
    public void close() {
    	javafx.application.Platform.exit();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		appointmentTimeList();
		appointmentPhysicianList();
		genderList();
		
	}

}
