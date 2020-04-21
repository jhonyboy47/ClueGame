///Authors: Jhonathan Malagon and Michael Crews
package javaFX;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

public class InvalidCellSelection{
	
	public static void displayInvalidSelection() {
		// Make an alert dialog window 
		Alert alert = new Alert(AlertType.WARNING);
		
		// Make a dialog pane that will be used for the alert dialog window
		DialogPane pane = alert.getDialogPane();
		
		// Set the size of the dialog pane text size which will the alert dialog's text size
		pane.setStyle("-fx-font-size: 25");
		
		// Set the minimum size of the dialog pane and alert dialog window
		pane.setMinSize(800, 150);
		
		// Set the dialog pane of the alert to the one we created
		alert.setDialogPane(pane);
		
		// Set the title text of the alert window
		alert.setTitle("Clue Game");
		
		// We don't want any header text
		alert.setHeaderText(null);

		// Set the main text of the alert window
		alert.setContentText("Sorry, you can't move to that cell");

		// Show the window and wait until window closes before continuing
		alert.showAndWait();
	}
	
}
