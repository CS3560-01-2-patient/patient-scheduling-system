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
	
    public void login() {
    	String sql = "SELECT * FROM patient WHERE username = ? and password = ?";
    	connect = Database.connectDB();
    	
    	try {
    		prepare = connect.prepareStatement(sql);
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
    			if(result.next()) {
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
    				Parent root = loader.load();
    				HomePage homepage = loader.getController();
    				homepage.setMainController(this);
    				
    	 			String sql1 = "SELECT * FROM patient WHERE username = ?";	
	    			prepare = connect.prepareStatement(sql1);
	        		prepare.setString(1, usernameTextField.getText());
	        		result = prepare.executeQuery();
	        		while(result.next()) {
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
	        		}
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
        			alert.setTitle("Error Message");
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
    
    public Patient getPatient() {
    	return myPatient;
    }
    
    
    public void createAccount() {
    	String sql = "INSERT INTO patient (name, email, username, password, phoneNumber, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	connect = Database.connectDB();
		ToggleGroup toggleGroup = new ToggleGroup();
		maleRB.setToggleGroup(toggleGroup);
		femaleRB.setToggleGroup(toggleGroup);
		otherRB.setToggleGroup(toggleGroup);
		maleRB.setSelected(true);
		
    	try {
    		Alert alert;
        	LocalDate date = createAccountDob.getValue();
			String gender = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
    		if(createAccountName.getText().isEmpty() || createAccountEmail.getText().isEmpty() || createAccountUsername.getText().isEmpty() || createAccountPassword.getText().isEmpty() || createAccountPhone.getText().isEmpty() || date == null) { 
    			alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setContentText("Please fill all blank fields");	
    			alert.showAndWait();
    		}
    		else {
    			
    			PreparedStatement psCheckUserExists = connect.prepareStatement("SELECT * FROM patient WHERE username = ?");
    			psCheckUserExists.setString(1, createAccountUsername.getText());
    			ResultSet resultSet = psCheckUserExists.executeQuery();
    			
    			if(resultSet.isBeforeFirst()) {
    				alert = new Alert(Alert.AlertType.ERROR);
        			alert.setTitle("Username already exists!");
    				alert.setContentText("You cannot use this username.");
    				alert.showAndWait();
    			}
    			else {
    				String dateOfBirth = date.toString();
        			prepare = connect.prepareStatement(sql);
        			prepare.setString(1, createAccountName.getText());
        			prepare.setString(2, createAccountEmail.getText());
        			prepare.setString(3, createAccountUsername.getText());
        			prepare.setString(4, createAccountPassword.getText());
        			prepare.setString(5, createAccountPhone.getText());
        			prepare.setString(6, dateOfBirth);
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
    
    public void createAccountView() {
    	left_form.setVisible(false);
    	login_form.setVisible(false);
    	createAccount_form.setVisible(true);
    	right_form.setVisible(true);

    }
    
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
