package application;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CreateAccountController implements Initializable{
	
	@FXML
	private TextField nameTextField;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private TextField emailTextField;
	
	@FXML
	private TextField phoneTextField;
	
	@FXML
	private DatePicker dateOfBirthField;
	
	@FXML
	private RadioButton maleRB, femaleRB, otherRB;
	
	@FXML
	private Button create_AccountButton;
	
	@FXML
	private Button cancelButton;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup toggleGroup = new ToggleGroup();
		maleRB.setToggleGroup(toggleGroup);
		femaleRB.setToggleGroup(toggleGroup);
		otherRB.setToggleGroup(toggleGroup);
		
		maleRB.setSelected(true);
		
		create_AccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
				if(!nameTextField.getText().trim().isEmpty() && !usernameTextField.getText().trim().isEmpty() && !passwordTextField.getText().trim().isEmpty() 
						&&!emailTextField.getText().trim().isEmpty() && !phoneTextField.getText().trim().isEmpty()) 
				{
					LocalDate date = dateOfBirthField.getValue();
					if(date == null) {
						System.out.println("Please fill in all information");
						Alert alert =  new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Please fill in all information to sign up!");
						alert.show();							
					}	
					String myDOB = date.toString();
										
					try {
						DBUtils.createAccount(event, nameTextField.getText(), emailTextField.getText(), usernameTextField.getText(), 
						passwordTextField.getText(), phoneTextField.getText(), myDOB, toggleName);
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
	
				}
				else {
					System.out.println("Please fill in all information");
					Alert alert =  new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill in all information to sign up!");
					alert.show();
				}
			}
		});		
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "Login-page.fxml", "Patient App", null, null, null, null, null, null, null);
			}
		});
	}
	


}
