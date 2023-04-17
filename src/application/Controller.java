package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller  implements Initializable{
	
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button createAccountButton;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private TextField passwordTextField;
	
	//checks to make sure we can login
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.userLogin(event, usernameTextField.getText(), passwordTextField.getText());
			}
		});		
		
		createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "Create-account.fxml", "Create Account", null, null, null, null, null, null, null);
			}
		});	
	}
	
}
