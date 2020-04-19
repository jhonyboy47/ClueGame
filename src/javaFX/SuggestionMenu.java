package javaFX;
import java.util.ArrayList;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class SuggestionMenu {
	private static double largestComboBoxWidth = 0;
	
	public static void makeSuggestionMenu(String roomName) {
		
		Board board = Board.getInstance();
		
		Stage window = new Stage();
		window.setTitle("Make a Suggestion");
		GridPane gridPane = new GridPane();
		
		Label yourRoomLabel = new Label("Your room");
		yourRoomLabel.setFont(new Font(25));
		Label weaponLabel = new Label("Weapon");
		weaponLabel.setFont(new Font(25));
		Label personLabel = new Label("Person");
		personLabel.setFont(new Font(25));
		
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
		
		// Get the player names
		for(Player player : players) {
			playerNames.add(player.getPlayerName());
		}
		
		
		
		for(String playerName : playerNames) {
			personComboBox.getItems().add(playerName);
		}
		
		for(String weaponName : weaponNames) {
			weaponComboBox.getItems().add(weaponName);
		}
		
		roomComboBox.getItems().add(roomName);
		
		calculateLargestComboBoxWidth(personComboBox);
		calculateLargestComboBoxWidth(roomComboBox);
		calculateLargestComboBoxWidth(weaponComboBox);
		
		System.out.println(largestComboBoxWidth);
		
		personComboBox.setMinWidth(largestComboBoxWidth);
		weaponComboBox.setMinWidth(largestComboBoxWidth);
		roomComboBox.setMinWidth(largestComboBoxWidth);
		yourRoomLabel.setMinWidth(largestComboBoxWidth);
		
		
		// gridPane.setMaxWidth(2*largestComboBoxWidth);
		
		// gridPane.setPadding(new Insets(20));
		gridPane.add(yourRoomLabel, 0, 0);
		
		gridPane.add(roomComboBox, 1, 0);
		
		gridPane.add(personLabel, 0, 1);
		
		gridPane.add(personComboBox, 1, 1);
		
		gridPane.add(weaponLabel, 0, 2);
		
		gridPane.add(weaponComboBox, 1, 2);
		
		
		// ColumnConstraints cc = new ColumnConstraints();
		// cc.setPercentWidth(100/2);
		
		// gridPane.getColumnConstraints().add(cc);
	 
		
		
		
		
		Scene scene = new Scene(gridPane);
		
		window.setScene(scene);
		
		window.showAndWait();
	}
	
	private static void calculateLargestComboBoxWidth(ComboBox<String> comboBox) {
		
		
		GridPane testGridPane = new GridPane();
		testGridPane.add(comboBox, 0, 1);
		
		// Pane pane = new Pane();
		
		// pane.getChildren().add(comboBox);
		
		Scene testScene = new Scene(testGridPane, 1000, 1000);
		
		
		
		
		testGridPane.applyCss();
		testGridPane.layout();
		
		for(String comboBoxSelection : comboBox.getItems()) {
			System.out.println(comboBoxSelection);
			comboBox.getSelectionModel().select(comboBoxSelection);
		}
		
		double comboBoxWidth = comboBox.getWidth();
		if(comboBoxWidth > largestComboBoxWidth) {
			largestComboBoxWidth = comboBoxWidth;
		}
		
		
	}
	

}
