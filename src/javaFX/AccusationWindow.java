package javaFX;

import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

public class AccusationWindow {
	
	public static void showWindow(Player player, Solution accusation, Boolean accusationCorrectBool) {
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
		alert.setTitle("Make an Accusation");

		alert.setHeaderText(player.getPlayerName() + " made an accusation which included: " + accusation.getRoom() + ", " + accusation.getPerson() +", " + accusation.getWeapon());
	
		if(accusationCorrectBool) {
			if(player instanceof ComputerPlayer) {
				// Set the main text of the alert window
				alert.setContentText("The accusation was correct. You lose! Thanks for playing");
			} else {
				alert.setContentText("The accusation was correct. You win!! Thanks for playing");
			}
			
			alert.setOnCloseRequest(e ->{
				alert.close();
				ClueGameStart.closeGame();
			});
			
		} else {
			alert.setContentText("The accusation was incorrect so the game continues on!");
		}
		

		// Show the window and wait until window closes
		alert.showAndWait();
	}
	
}
