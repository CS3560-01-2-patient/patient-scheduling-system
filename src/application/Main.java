package application;
	
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@FXML
    private Button createAccountBtn;

    @FXML
    private DatePicker createAccountDob;

    @FXML
    private TextField createAccountEmail;

    @FXML
    private TextField createAccountName;

    @FXML
    private PasswordField createAccountPassword;

    @FXML
    private TextField createAccountPhone;

    @FXML
    private TextField createAccountUsername;

    @FXML
    private AnchorPane createAccount_form;

    @FXML
    private RadioButton femaleRB;

    @FXML
    private AnchorPane left_form;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private RadioButton maleRB;

    @FXML
    private RadioButton otherRB;

    @FXML
    private Button otherSignInBtn;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private AnchorPane right_form;

    @FXML
    private TextField usernameTextField;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private Patient myPatient;
    
	@Override
	public void start(Stage primaryStage) {
		try {
			//Test.go();
			Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			primaryStage.setTitle("Patient App");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//patient clicks login button
    public void login() {
    	String getUsernameAndPwdSql = "SELECT * FROM patient WHERE username = ? and password = ?";
    	connect = Database.connectDB();
    	
    	try {
    		prepare = connect.prepareStatement(getUsernameAndPwdSql);
    		prepare.setString(1, usernameTextField.getText());
    		prepare.setString(2, passwordTextField.getText());
    		result = prepare.executeQuery();
    		
    		Alert alert;
    		if(usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
    			alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message");
    			alert.setHeaderText(null);
    			alert.setContentText("Please enter your username and password");
    			alert.showAndWait();
    		}
    		else {
    			if(result.next()) { //checks if username and password is in patient database
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
    				Parent root = loader.load();
    				HomePage homepage = loader.getController();
    				homepage.setMainController(this);    				
    	 	 
	        		int patient_id = result.getInt("patient_id");
	        		String name = result.getString("name");
	        		String email = result.getString("email");
	        		String username = result.getString("username");
	        		String password = result.getString("password");
	        		String phoneNumber = result.getString("phoneNumber");
	        		String dateOfBirth = result.getString("dateOfBirth");
	        		String gender= result.getString("gender");
		        	homepage.setUserInfo(patient_id, name, email, username, password, phoneNumber, dateOfBirth, gender);
		        	myPatient = new Patient(patient_id, name, email, username, password, phoneNumber, dateOfBirth, gender);
	        		
	        		
    				alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("Information Message");
    				alert.setHeaderText(null);
    				alert.setContentText("You have logged in!");
    				alert.showAndWait();	
    				
    				loginButton.getScene().getWindow().hide();
	   	        		
    				Stage stage = new Stage();
    				Scene scene = new Scene(root);
    				stage.setScene(scene);
    				stage.show();
    			}
    			else {
    	 			alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Failed to login!");
        			alert.setHeaderText(null);
        			alert.setContentText("Login details are incorrect!");
        			alert.showAndWait();
    			}
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    //patient clicks create account button from create account screen
    public void createAccount() {
    	String createAccountSql = "INSERT INTO patient (name, email, username, password, phoneNumber, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	connect = Database.connectDB();
		ToggleGroup toggleGroup = new ToggleGroup();
		maleRB.setToggleGroup(toggleGroup);
		femaleRB.setToggleGroup(toggleGroup);
		otherRB.setToggleGroup(toggleGroup);
		maleRB.setSelected(true);
		
    	try {
    		Alert alert;
			String gender = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
    		if(createAccountName.getText().isEmpty() || createAccountEmail.getText().isEmpty() || createAccountUsername.getText().isEmpty() || createAccountPassword.getText().isEmpty() || createAccountPhone.getText().isEmpty() || createAccountDob.getValue() == null) { 
    			alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setContentText("Please fill all blank fields");	
    			alert.showAndWait();
    		}
    		else {
    			String checkUsernameExistsSql = ("SELECT * FROM patient WHERE username = ?");
    			prepare = connect.prepareStatement(checkUsernameExistsSql);
    			prepare.setString(1, createAccountUsername.getText());
    			result = prepare.executeQuery();
    			
    			if(result.next()) { //check if username already exists in database
    				alert = new Alert(Alert.AlertType.ERROR);
        			alert.setTitle("Failed to create account!");
    				alert.setContentText("Sorry! This username already exists!");
    				alert.showAndWait();
    			}
    			else {
        			prepare = connect.prepareStatement(createAccountSql);
        			prepare.setString(1, createAccountName.getText());
        			prepare.setString(2, createAccountEmail.getText());
        			prepare.setString(3, createAccountUsername.getText());
        			prepare.setString(4, createAccountPassword.getText());
        			prepare.setString(5, createAccountPhone.getText());
        			prepare.setString(6, String.valueOf(createAccountDob.getValue()));
        			prepare.setString(7, gender);
        			
        			    			
        			alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle("Account Created");
        			alert.setContentText("Success! You have created a new account!");
        			alert.showAndWait();

        			prepare.executeUpdate();
    			}

    		}

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //call to retrieve the reference to myPatient object
    public Patient getPatient() {
    	return myPatient;
    }
    
    // switches to create account screen
    public void createAccountView() {
    	left_form.setVisible(false);
    	login_form.setVisible(false);
    	createAccount_form.setVisible(true);
    	right_form.setVisible(true);

    }
    
    // switches to login screen
    public void loginView() {
    	left_form.setVisible(true);
    	login_form.setVisible(true);
    	createAccount_form.setVisible(false);
    	right_form.setVisible(false);
    }
    
	
	public static void main(String[] args) {
		launch(args);
	}
}
