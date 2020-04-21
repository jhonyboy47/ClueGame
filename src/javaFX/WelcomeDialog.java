///Authors: Jhonathan Malagon and Michael Crews
package javaFX;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeDialog {
	public static void displayWelcomeDialog(String humanPlayerName) {
		// Make an alert dialog window 
		Alert alert = new Alert(AlertType.INFORMATION);
		
		// Make a dialog pane that will be used for the alert dialog window
		DialogPane pane = alert.getDialogPane();
		
		// Set the size of the dialog pane text size which will the alert dialog's text size
		pane.setStyle("-fx-font-size: 25");
		
		// Set the minimum size of the dialog pane and alert dialog window
		pane.setMinSize(800, 150);
		
		// Set the dialog pane of the alert to the one we created
		alert.setDialogPane(pane);
		
		// Set the title text of the alert window
		alert.setTitle("Welcome to Clue");
		
		// We don't want any header text
		alert.setHeaderText(null);
	
		// Set the main text of the alert window
		alert.setContentText("You are " + humanPlayerName + ", press Next player to begin game");

		// Show the window and wait until window closes before continuing in ClueGameStart
		alert.showAndWait();
		
	}
	
}
