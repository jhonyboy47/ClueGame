///Authors: Jhonathan Malagon and Michael Crews
package javaFX;
import java.util.ArrayList;
import java.util.Map;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Suggestion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;


public class AccusationMenu {
	private static double largestComboBoxWidth = 0;
	
	public static void makeAccusationMenu() {
		
		// Need the board instance
		Board board = Board.getInstance();
		
		Stage window = new Stage();
		
		// Set the window so that the user can't click on other Clue Game windows
		window.initModality(Modality.APPLICATION_MODAL);
		
		
		window.setTitle("Make an Accusation");
	
		GridPane gridPane = new GridPane();
		
		Label yourRoomLabel = new Label("Room");
		yourRoomLabel.setFont(new Font(25));
		yourRoomLabel.setPadding(new Insets(0,0,0, 10));
		
		Label weaponLabel = new Label("Weapon");
		weaponLabel.setFont(new Font(25));
		weaponLabel.setPadding(new Insets(0,0,0, 10));
		
		Label personLabel = new Label("Person");
		personLabel.setFont(new Font(25));
		personLabel.setPadding(new Insets(0,0,0, 10));
		
		Button submitButton = new Button("Submit");
		submitButton.setFont(new Font(20));
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setFont(new Font(20));
		
		Label padding = new Label();
		padding.setMinHeight(20);
		
		ComboBox<String> weaponComboBox = new ComboBox<>();
		ComboBox<String> personComboBox = new ComboBox<>();
		ComboBox<String> roomComboBox = new ComboBox<>();
		weaponComboBox.setStyle("-fx-font-size: 25");
		personComboBox.setStyle("-fx-font-size: 25");
		roomComboBox.setStyle("-fx-font-size: 25");
		
		
		// Get the names lists
		ArrayList<Player> players = board.getPlayersSet();
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<String> weaponNames = board.getWeapons();
		ArrayList<String> roomNames = board.getRooms();
		
		// Get the player names
		for(Player player : players) {
			playerNames.add(player.getPlayerName());
		}
		
		// Load the names into the ComboBoxes
		for(String playerName : playerNames) {
			personComboBox.getItems().add(playerName);
		}
		
		for(String weaponName : weaponNames) {
			weaponComboBox.getItems().add(weaponName);
		}
		
		for(String roomName : roomNames) {
			roomComboBox.getItems().add(roomName);
		}
		
		// Calculate what the largest width is for all ComboBoxes
		calculateLargestComboBoxWidth(personComboBox);
		calculateLargestComboBoxWidth(roomComboBox);
		calculateLargestComboBoxWidth(weaponComboBox);
		
		
		// Set the min width for all ComboBoxes and labels
		personComboBox.setMinWidth(largestComboBoxWidth);
		weaponComboBox.setMinWidth(largestComboBoxWidth);
		roomComboBox.setMinWidth(largestComboBoxWidth);
		yourRoomLabel.setMinWidth(largestComboBoxWidth);
		submitButton.setMinWidth(largestComboBoxWidth);
		cancelButton.setMinWidth(largestComboBoxWidth);
		
		submitButton.setMinHeight(50);
		cancelButton.setMinHeight(50);
		
		submitButton.setOnAction(e -> {
			Solution accusation = new Solution(personComboBox.getValue(), weaponComboBox.getValue(), roomComboBox.getValue());
			Boolean accusationCorrectBool = board.checkAccusation(accusation);
			
			board.unHighlightTargets();
			
			// Set just moved to true, so the player can't cheat
			((HumanPlayer) board.getHumanPlayer()).setJustMoved(true);
			
			// Show the accusation window showing the results
			AccusationWindow.showWindow(board.getNextPlayer(), accusation, accusationCorrectBool);
			
			// Close the accusation menu
			window.close();
		});
		
		cancelButton.setOnAction(e ->{
			// Close the accusation menu
			window.close();
		});
		
		// Add all labels/comboboxes to the gridpane
		gridPane.add(yourRoomLabel, 0, 0);
		
		gridPane.add(roomComboBox, 1, 0);
		
		gridPane.add(personLabel, 0, 1);
		
		gridPane.add(personComboBox, 1, 1);
		
		gridPane.add(weaponLabel, 0, 2);
		
		gridPane.add(weaponComboBox, 1, 2);
		
		gridPane.add(padding, 0, 3);
		
		gridPane.add(submitButton, 0, 4);
		
		gridPane.add(cancelButton, 1, 4);
		
		
		Scene scene = new Scene(gridPane);
		
		window.setScene(scene);
		
		window.showAndWait();
	}
	
	private static void calculateLargestComboBoxWidth(ComboBox<String> comboBox) {
		
		Pane pane = new Pane();
		
		pane.getChildren().add(comboBox);
		Scene testScene = new Scene(pane, 1000, 1000);
		
		for(String comboBoxSelection : comboBox.getItems()) {
			comboBox.getSelectionModel().select(comboBoxSelection);
			pane.applyCss();
			pane.layout();
			double comboBoxWidth = comboBox.getWidth();
			if(comboBoxWidth > largestComboBoxWidth) {
				largestComboBoxWidth = comboBoxWidth;
			}
		}
	}
	

}
