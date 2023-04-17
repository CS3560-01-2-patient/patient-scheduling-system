package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomePageController implements Initializable {
	
	@FXML
	private Button logoutButton;
	
	@FXML
	private Label nameLabel;
	
	@FXML 
	private Label emailLabel;
	
	@FXML
	private Label usernameLabel;
	
	@FXML 
	private Label passwordLabel;
	
	@FXML
	private Label phoneLabel;
	
	@FXML
	private Label dobLabel;
	
	@FXML
	private Label genderLabel;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logoutButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "Login-page.fxml", "My Account", null, null, null, null, null, null, null);
			}
		});
		
	}
	
	public void setUserInfo(String name, String email, String username, String password, String phone, String dateOfBirth, String gender) {
		nameLabel.setText("Name: " +  name);
		emailLabel.setText("Email: " +  email);
		usernameLabel.setText("Username: " +  username);
		passwordLabel.setText("Password: " +  password);
		phoneLabel.setText("Phone Number: " +  phone);
		dobLabel.setText("Date of Birth: " +  dateOfBirth);
		genderLabel.setText("Gender: " +  gender);	
	}

}
