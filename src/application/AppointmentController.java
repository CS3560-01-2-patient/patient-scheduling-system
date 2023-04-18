package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class AppointmentController implements Initializable{
	@FXML
	private DatePicker appointmentDate;
	
	@FXML
	private Button nineAM, tenAM, elevenAM, twelvePM, onePM, twoPM, threePM, fourPM, fivePM;
	
	@FXML
	private Button cancelBtn;
	
	@FXML
	private Button setAppointmentBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setAppointmentBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "Home-page.fxml", "My Account", null, null, null, null, null, null, null);
			}
		});
			
	
	}
	
	

}
