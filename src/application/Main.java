package application;
	
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class Main extends Application {
    @FXML
    private Button ca_createAccountButton;

    @FXML
    private DatePicker ca_dob;
    
    @FXML
    private TextField ca_phoneNum;

    @FXML
    private TextField ca_email;
    
    @FXML
    private RadioButton ca_femaleRB;

    @FXML
    private RadioButton ca_maleRB;

    @FXML
    private TextField ca_name;

    @FXML
    private RadioButton ca_otherRB;

    @FXML
    private PasswordField ca_password;

    @FXML
    private TextField ca_username;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane createAccount_form;

    @FXML
    private Text edit_label;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button si_loginButton;

    @FXML
    private PasswordField si_passwordTextField;

    @FXML
    private TextField si_usernameTextField;

    @FXML
    private AnchorPane sub_form;

    @FXML
    private Button sub_loginBtn;

    @FXML
    private Button sub_signupBtn;
    
    @FXML
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
	@Override
	public void start(Stage primaryStage) {
		try {
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
    		prepare.setString(1, si_usernameTextField.getText());
    		prepare.setString(2, si_passwordTextField.getText());
    		result = prepare.executeQuery();
    		Alert alert;
    		if(si_usernameTextField.getText().isEmpty() || si_passwordTextField.getText().isEmpty()) {
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
    				
    	 			String sql1 = "SELECT * FROM patient WHERE username = ?";	
	    			prepare = connect.prepareStatement(sql1);
	        		prepare.setString(1, si_usernameTextField.getText());
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
	        		}
    				alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("Information Message");
    				alert.setHeaderText(null);
    				alert.setContentText("You have logged in!");
    				alert.showAndWait();	
    				
    				si_loginButton.getScene().getWindow().hide();
	   
	        		
    				//Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
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
    
    public void createAccount() {
    	String sql = "INSERT INTO patient (name, email, username, password, phoneNumber, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	connect = Database.connectDB();
		ToggleGroup toggleGroup = new ToggleGroup();
		ca_maleRB.setToggleGroup(toggleGroup);
		ca_femaleRB.setToggleGroup(toggleGroup);
		ca_otherRB.setToggleGroup(toggleGroup);
		
		ca_maleRB.setSelected(true);
		
    	try {
    		Alert alert;
        	LocalDate date = ca_dob.getValue();
			String gender = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
    		if(ca_name.getText().isEmpty() || ca_email.getText().isEmpty() || ca_username.getText().isEmpty() || ca_password.getText().isEmpty() || ca_email.getText().isEmpty() || ca_phoneNum.getText().isEmpty() || date == null) { 
    			alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setContentText("Please fill all blank fields");	
    			alert.showAndWait();
    		}
    		else {
    			
    			PreparedStatement psCheckUserExists = connect.prepareStatement("SELECT * FROM patient WHERE username = ?");
    			psCheckUserExists.setString(1, ca_username.getText());
    			ResultSet resultSet = psCheckUserExists.executeQuery();
    			
    			if(resultSet.isBeforeFirst()) {
    				System.out.println("User already exists!");
    				alert = new Alert(Alert.AlertType.ERROR);
    				alert.setContentText("You cannot use this username.");
    				alert.showAndWait();
    			}
    			else {
    				String dateOfBirth = date.toString();
        			prepare = connect.prepareStatement(sql);
        			prepare.setString(1, ca_name.getText());
        			prepare.setString(2, ca_email.getText());
        			prepare.setString(3, ca_username.getText());
        			prepare.setString(4, ca_password.getText());
        			prepare.setString(5, ca_phoneNum.getText());
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
    
    public void createAccountSlider() {
    	TranslateTransition slider1 = new TranslateTransition();
    	slider1.setNode(sub_form);
    	slider1.setToX(300);
    	slider1.setDuration(Duration.seconds(.5));
    	slider1.play();
    	
    	slider1.setOnFinished((ActionEvent event) -> {
    		edit_label.setText("Login into your Account");
    		
    		sub_signupBtn.setVisible(false);
    		sub_loginBtn.setVisible(true); 		
    	});
    }
    
    
    
    public void loginSlider() {
    	TranslateTransition slider1 = new TranslateTransition();
    	slider1.setNode(sub_form);
    	slider1.setToX(0);
    	slider1.setDuration(Duration.seconds(.5));
    	slider1.play();
    	
    	slider1.setOnFinished((ActionEvent event) -> {
    		edit_label.setText("Don't have an account?");
    		
    		sub_signupBtn.setVisible(true);
    		sub_loginBtn.setVisible(false);	
    	});
    }
    
    public void close() {
    	javafx.application.Platform.exit();
    	
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
