package application;

import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private DatePicker dateOfBirth;	
	@FXML 
	private RadioButton maleButton, femaleButton, otherButton;
	
	@FXML
	private DatePicker appointmentDate;
	@FXML
	private RadioButton nineAM, tenAM, elevenAM, twelvePM, onePM, twoPM, threePM, fourPM, fivePM;
	
	public void switchToHomePage(ActionEvent event) throws IOException{
		root =  FXMLLoader.load(getClass().getResource("HomePage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	public void switchToAppointment(ActionEvent event) throws IOException{
		root =  FXMLLoader.load(getClass().getResource("Appointment.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}

	public void switchToCreateAccount(ActionEvent event) throws IOException{
		root =  FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	
	public void switchToLoginPage(ActionEvent event) throws IOException{
		root =  FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void getDate(ActionEvent event) {
		LocalDate myDate = dateOfBirth.getValue();
		System.out.println(myDate.toString());
	}
	
	public void getGender(ActionEvent event) {
		if(maleButton.isSelected()) {
			System.out.println("male");
		}
		else if(femaleButton.isSelected()) {
			System.out.println("female");
		}
		else if(otherButton.isSelected()) {
			System.out.println("other");
		}
	}
	
	public void getAppointmentDate(ActionEvent event) {
		LocalDate myDate = appointmentDate.getValue();
		System.out.println(myDate.toString());
	}
	
	public void getAppointmentTime(ActionEvent event) {
		if(nineAM.isSelected()) {
			System.out.println("nineAM");
		}
		else if(tenAM.isSelected()) {
			System.out.println("tenAM");
		}
		else if(elevenAM.isSelected()) {
			System.out.println("elevenAM");
		}
		else if(twelvePM.isSelected()) {
			System.out.println("twelvePM");
		}
		else if(onePM.isSelected()) {
			System.out.println("onePM");
		}
		else if(twoPM.isSelected()) {
			System.out.println("twoPM");
		}
		else if(threePM.isSelected()) {
			System.out.println("threePM");
		}
		else if(fourPM.isSelected()) {
			System.out.println("fourPM");
		}
		else if(fivePM.isSelected()) {
			System.out.println("fivePM");
		}
	}
}

	

	
	

	

