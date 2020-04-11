package javaFX;

import java.util.ArrayList;
import java.util.List;

import clueGame.Board;
import clueGame.Player;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class DetectiveNotes {
	// Variables to hold what the size of the scene should be
	public static double largestWidth = 0;
	public static double overallHeight = 0;
	
	// ArrayLists to hold which buttons are pressed
	public ArrayList<String> peoplePressedNames = new ArrayList<String>();
	public ArrayList<String> weaponPressedNames = new ArrayList<String>();
	public ArrayList<String> roomPressedNames = new ArrayList<String>();
	

	public void displayDetectiveNotes() {
		// Get the board instance
		Board board = Board.getInstance();
		
		Stage window = new Stage();
		
		// Set correct window close
		window.setOnCloseRequest(e ->{
			overallHeight = 0;
			window.close();
		});
		
		window.setTitle("Detective Notes");
		
		// Get the names lists
		ArrayList<Player> players = board.getPlayersSet();
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<String> weaponNames = board.getWeapons();
		ArrayList<String> roomNames = board.getRooms();
		
		// Get the player names
		for(Player player : players) {
			playerNames.add(player.getPlayerName());
		}
		
		// Get the initial hboxs
		HBox peopleHbox = drawSection(playerNames, "People", false);
		
		HBox weaponsHbox = drawSection(weaponNames, "Weapons", false);
		
		HBox roomHbox = drawSection(roomNames, "Rooms", false);
		
		
		// Get the final hboxs
		
		peopleHbox = drawSection(playerNames, "People", true);
		
		weaponsHbox = drawSection(weaponNames, "Weapons", true);
		
		roomHbox = drawSection(roomNames, "Rooms", true);
		
	
		VBox mainVbox = new VBox();
		
		// Add all final hboxs to mainVbox
		mainVbox.getChildren().addAll(peopleHbox,weaponsHbox,roomHbox);
		
		// Declare scene and initialize using correct values
		Scene scene = new Scene(mainVbox, largestWidth*2, overallHeight);
		
		window.setScene(scene);
	
		window.show();
	
	}
	
	
	public HBox drawSection(ArrayList<String> names, String type, Boolean knowLargestWidth) {
		// Left means left part of scene
		// Right means right part of scene
		
		// Main hbox for a section of the scene
		HBox mainHbox = new HBox();
		
		VBox leftVbox = new VBox();
		leftVbox.setStyle("-fx-border-color: gray");
		leftVbox.setPadding(new Insets(5,10,10,10));
		
		if(knowLargestWidth == true) {
			leftVbox.setMinWidth(largestWidth);
		}
		
		Label leftLabel = new Label(type);
		leftLabel.setFont(new Font(25));
	
		Label rightLabel = new Label(type + " Guess");
		rightLabel.setFont(new Font(25));
		
		VBox rightVbox = new VBox();
		rightVbox.setStyle("-fx-border-color: gray");
		rightVbox.setPadding(new Insets(5,10,10,10));
		rightVbox.setSpacing(10);
		
		GridPane rightPane = new GridPane();
		
		
		GridPane checkBoxPane = new GridPane();
		checkBoxPane.setPadding(new Insets(10,0,5,0));
		
		
		GridPane leftPane = new GridPane();
		
		ComboBox<String> comboBox = new  ComboBox<>();
		
		
		// Counters for checkboxs into their correct spots
		int rowCounter = 0;
		int colCounter = 0;
	
		for(String name : names) {
			
			// Add name to comboBox
			comboBox.getItems().add(name);
			
			// Make checkbox for the name
			CheckBox box = new CheckBox(name);
			
			// If theres already a value of the checkbox, load it in
			setBoxWhenOpeningNotes(box, type, comboBox);
			box.setFont(new Font(20));
			
			// Set events when the user clicks the box
			box.setOnMouseReleased(e ->{
				// Will handle the event differently if type is People, Weapons, or Rooms
				if(type.equals("People")) {
					if(peoplePressedNames.contains(name)) {
						comboBox.getItems().add(name);
						peoplePressedNames.remove(name);
					} else {
						if(name == comboBox.getSelectionModel().getSelectedItem()) {
							comboBox.getSelectionModel().clearSelection();
						}
						comboBox.getItems().remove(name);
						peoplePressedNames.add(name);
					}
				} else if (type.equals("Weapons")) {
					if(weaponPressedNames.contains(name)) {
						comboBox.getItems().add(name);
						weaponPressedNames.remove(name);
					} else {
						if(name == comboBox.getSelectionModel().getSelectedItem()) {
							comboBox.getSelectionModel().clearSelection();
						}
						comboBox.getItems().remove(name);
						weaponPressedNames.add(name);
					}
				} else if(type.equals("Rooms")) {
					if(roomPressedNames.contains(name)) {
						comboBox.getItems().add(name);
						roomPressedNames.remove(name);
					} else {
						if(name == comboBox.getSelectionModel().getSelectedItem()) {
							comboBox.getSelectionModel().clearSelection();
						}
						comboBox.getItems().remove(name);
						roomPressedNames.add(name);
					}
				}
				
			});
			
			// Add checkbox to the checkboxpane in correct location
			checkBoxPane.add(box, colCounter, rowCounter);
			
			// Set the counters 
			if(colCounter == 1) {
				colCounter = 0;
				rowCounter++;
			} else {
				colCounter++;
			}
		}
		
		checkBoxPane.setMinSize(20, 20);
		checkBoxPane.setAlignment(Pos.CENTER_LEFT);
		
		checkBoxPane.setHgap(20);
	    checkBoxPane.setVgap(20);
		
		leftVbox.getChildren().addAll(leftLabel,checkBoxPane);
	
		leftPane.add(leftVbox, 0, 0);
		
		// testScene for calculating how much space the checkboxes will take up on screen
		Scene testScene = new Scene(leftPane,1000,1000);
		
		// calculate how much space the checkboxes will take up on screen
		leftPane.applyCss();
		leftPane.layout();
		
		
		// Set the largestWidth variable from the results of the calculation above
		if(leftVbox.getWidth() > largestWidth) {
			largestWidth = leftVbox.getWidth();
		}
		
		// divide the height by 2 because this function will run 6 times
		overallHeight += leftVbox.getHeight()/2;
		

		comboBox.setStyle("-fx-font-size: 25");
		
		
		VBox.setVgrow(comboBox, Priority.ALWAYS);
		comboBox.setMaxWidth(Double.MAX_VALUE);
		comboBox.setMaxHeight(Double.MAX_VALUE);
		
		rightVbox.getChildren().addAll(rightLabel,comboBox);
		
		// Add the ability for the comboBox to grow in size
		HBox.setHgrow(rightVbox, Priority.ALWAYS);
		rightVbox.setMaxWidth(Double.MAX_VALUE);
		rightVbox.setMaxHeight(leftVbox.getHeight() - 1);
		
		rightPane.add(rightVbox, 0, 0);
		
		mainHbox.getChildren().addAll(leftPane, rightVbox);
		

		mainHbox.setPadding(new Insets(8,8,8,8));
		mainHbox.setSpacing(15);
		
		
		return mainHbox;
	}
	
	private void setBoxWhenOpeningNotes(CheckBox box, String type, ComboBox<String> comboBox) {
		// Load in know information about the checkBoxes
		if(type == "People") {
			for(String name : peoplePressedNames) {
				if(box.getText().equals(name)) {
					box.setSelected(true);
					comboBox.getItems().remove(name);
				}
			}
		} else if(type == "Weapons") {
			for(String name : weaponPressedNames) {
				if(box.getText().equals(name)) {
					box.setSelected(true);
					comboBox.getItems().remove(name);
				}
			}
		} else if(type == "Rooms") {
			for(String name : roomPressedNames) {
				if(box.getText().equals(name)) {
					box.setSelected(true);
					comboBox.getItems().remove(name);
				}
			}
		}
		
	}
}
