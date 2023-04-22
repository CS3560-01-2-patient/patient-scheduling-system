package application;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage implements Initializable{
	
	@FXML
    private TableColumn<?, ?> appointment_col_appID;

    @FXML
    private TableColumn<?, ?> appointment_col_date;

    @FXML
    private TableColumn<?, ?> appointment_col_patientID;

    @FXML
    private TableColumn<?, ?> appointment_col_physcID;

    @FXML
    private TableColumn<?, ?> appointment_col_time;

    @FXML
    private TableColumn<?, ?> appointment_col_treatment;

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
    private TableView<?> appointment_tableView;

    @FXML
    private ChoiceBox<?> appointment_time;

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
    
    public String appointmentTime[]  = {"9:00AM", "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM", "5:00PM"};
    
    public void appointmentTimeList() {
    	List<String> timeList = new ArrayList<>();
    	
    	for(int i = 0; i < appointmentTime.length; i++) {
    		timeList.add(appointmentTime[i]);
    	}
    	
    	ObservableList listData = FXCollections.observableArrayList(timeList);
    	appointment_time.setItems(listData);
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
    
    public void close() {
    	javafx.application.Platform.exit();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		appointmentTimeList();
		
	}

}
