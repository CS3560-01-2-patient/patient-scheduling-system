package application;
	
import java.awt.event.ActionEvent;
import java.sql.Connection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Patient App");
			
			Parent root =  FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
	
			Image img = new Image("file:healthcare.png");
			ImageView imgView = new ImageView(img);
			



		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	


}



//button = new Button();
//button.setText("Create Account");
//
//button.setOnAction(e -> System.out.println("Hey bro"));
//
//StackPane layout = new StackPane();
//layout.getChildren().add(button);
//Scene scene = new Scene(layout, 300, 250);
//primaryStage.setScene(scene);
//primaryStage.show();