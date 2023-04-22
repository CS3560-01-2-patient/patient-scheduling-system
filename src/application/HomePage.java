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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
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
    private TableView<Appointment> appointment_tableView;
    
    @FXML
    private TableColumn<Appointment, Integer> appointment_col_patientID;

    @FXML
    private TableColumn<Appointment, Integer> appointment_col_physcID;
    
    @FXML
    private TableColumn<Appointment, String> appointment_col_date;

    @FXML
    private TableColumn<Appointment, String> appointment_col_time;

    @FXML
    private TableColumn<Appointment, String> appointment_col_treatment;
    
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
    private TableColumn<?, ?> profile_col_dob;

    @FXML
    private TableColumn<?, ?> profile_col_email;

    @FXML
    private TableColumn<?, ?> profile_col_gender;

    @FXML
    private TableColumn<?, ?> profile_col_name;

    @FXML
    private TableColumn<?, ?> profile_col_password;

    @FXML
    private TableColumn<?, ?> profile_col_patientID;

    @FXML
    private TableColumn<?, ?> profile_col_phoneNum;

    @FXML
    private TableColumn<?, ?> profile_col_username;

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
    private TableView<?> profile_tableView;

    @FXML
    private Button profile_updateBtn;

    @FXML
    private TextField profile_username;
	
    @FXML
    private Button profileBtn;
    
    @FXML
    private Button appointmentBtn;
    
    
    private String gender[] = {"Male", "Female", "Other"};
    private String appointmentTime[]  = {"9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM", "5:00PM"};
    private String physicians[] = {"John Smith", "Emily Nguyen", "David Kim"};
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    
    public void createAppointmentBtn() {
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
    			String sql1 = "SELECT patient_id FROM patient";
    			System.out.println(appointment_patientID.getText());
    			statement = connect.createStatement();
    			result = statement.executeQuery(sql1);
    			while(result.next()) {
    				int patientIdDatabase = result.getInt("patient_id");
    				if(Integer.parseInt(appointment_patientID.getText()) == patientIdDatabase){
    					patientIdExists = true;
    					break;
    				}
    			}
    			
    			if(patientIdExists) {
    	    		int physicianID = getPhysicianID();
    				prepare = connect.prepareStatement(sql);
    	    		prepare.setInt(1,  Integer.parseInt(appointment_patientID.getText()));
    	    		prepare.setInt(2, physicianID);
    	    		prepare.setString(3, String.valueOf(appointment_date.getValue()));
    	    		prepare.setString(4, (String)appointment_time.getSelectionModel().getSelectedItem());
    	    		prepare.setString(5, appointment_treatment.getText());
    				
    	    		Alert alert = new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Information Message");
        			alert.setContentText("You have created an appointment!");
        			alert.showAndWait();
        			prepare.executeUpdate();
        			appointmentClearBtn(); 	
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
    
    public int getPhysicianID() {
    	String sql = "SELECT physician_id FROM physician WHERE name = ?";
    	connect = Database.connectDB();
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
    
    public void appointmentClearBtn() {
    	appointment_patientID.setText("");
    	appointment_date.setValue(null);
    	appointment_time.getSelectionModel().clearSelection();
    	appointment_physician.getSelectionModel().clearSelection();
    	appointment_treatment.setText("");;

    }
    
    
    
    public void switchForm(ActionEvent event) {
    	if(event.getSource() == appointmentBtn) {
    		appointment_form.setVisible(true);
    		profile_form.setVisible(false);
    		
    		appointmentTimeList();
    	}
    	else if(event.getSource() == profileBtn) {
    		profile_form.setVisible(true);
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
    	List<String> physicianList = new ArrayList<>();
    	
    	for(int i = 0; i < physicians.length; i++) {
    		physicianList.add(physicians[i]);
    	}	
    	ObservableList listData = FXCollections.observableArrayList(physicianList);
    	appointment_physician.setItems(listData);
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