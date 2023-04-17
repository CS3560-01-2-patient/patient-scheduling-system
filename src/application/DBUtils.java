package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Result;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class DBUtils {
	
	public static void changeScene(ActionEvent event, String fxmlFile, String title, String name, String email, 
String username, String password, String phone, String dateOfBirth, String gender) {
		Parent root = null;
		//if the user is signed in, these values are null. They are just switching from the login scene to the sign up scene 
		System.out.println(name + "," + email + "," + username + ","  + password + "," + phone + "," + dateOfBirth + "," + gender);
		if(name != null && email != null && username != null && password != null  && phone != null && dateOfBirth != null && gender != null) {
			try {
				FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
				root = loader.load();
				HomePageController homePageController = loader.getController();
				homePageController.setUserInfo(name, email, username, password, phone, dateOfBirth, gender); 

			}
			catch (IOException e){
				e.printStackTrace();
			}
			
		}
		else { //if the user just wants to switch on the login to sign up page we're just going to load the fxml
			try {
				root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	
	public static void createAccount(ActionEvent event, String name, String email, String username, String password, String phone, String dateOfBirth, String gender) throws SQLException {
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pss_schema", "root", "mypassword");
			psCheckUserExists = connection.prepareStatement("SELECT * FROM patient WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery();
			
			if(resultSet.isBeforeFirst()) {
				System.out.println("User already exists!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("You cannot use this username.");
			}
			else {
				psInsert = connection.prepareStatement("INSERT INTO patient (name, email, username, password, phoneNumber, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ? , ?)");
				psInsert.setString(1, name);
				psInsert.setString(2, email);
				psInsert.setString(3, username);
				psInsert.setString(4, password);
				psInsert.setString(5, phone);
				psInsert.setString(6, dateOfBirth);
				psInsert.setString(7, gender);
				psInsert.executeUpdate();
				
				changeScene(event, "Home-Page.fxml", "My Account", name, email, username, password, phone, dateOfBirth, gender);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(psInsert != null) {
				try {
					psInsert.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static void userLogin(ActionEvent event, String username, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pss_schema", "root", "mypassword");
			preparedStatement = connection.prepareStatement("SELECT name, password, email, phoneNumber, dateOfBirth, gender FROM patient WHERE username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.isBeforeFirst()) {
				System.out.println("User not found in the database");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Provided credentials are incorrect!");
				alert.show();
			}
			else {
				while (resultSet.next()) {
					String retrievedName = resultSet.getString("name");
					String retrievedPassword = resultSet.getString("password");
					String retrievedEmail = resultSet.getString("email");
					String retrievedPhone = resultSet.getString("phoneNumber");
					String retrievedDOB = resultSet.getString("dateOfBirth");
					String retrievedGender = resultSet.getString("gender");
					if(retrievedPassword.equals(password)) {
						changeScene(event, "Home-Page.fxml", "My Account", retrievedName, retrievedEmail, username, password, retrievedPhone, retrievedDOB, retrievedGender);
					}
					else {
						System.out.println("Passwords did not match!");
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("The Provided credentials are incorrect!");
						alert.show();
					}
				}
			}
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	



}
