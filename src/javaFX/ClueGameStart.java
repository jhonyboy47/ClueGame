// Authors: Michael Crews and Jhon Malagon

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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.collections.ObservableList;
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


public class ClueGameStart extends Application{
	
	private static Stage mainWindow;
	static Board board;
	
	// DetectiveNotes object to keep track of users button clickes inside of the notes window
    public static DetectiveNotes notes = new DetectiveNotes();
	
	public static void  main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("ClueGameBoardCSV.csv", "NewClueRooms.csv");	
		board.setPlayersFile("NewClueGamePlayers.csv");
		board.setWeaponsFile("NewClueGameWeapons.csv");
		board.initialize();
		launch(args);
	}
	
	@Override
	public void start(Stage window) throws Exception {
		
		mainWindow = window;
		
		ArrayList<Player> players = board.getPlayersSet();
		
		// Display dialog that tells player which player they are
		WelcomeDialog.displayWelcomeDialog(board.getHumanPlayer().getPlayerName());
		
		// Set the title of the window to "Clue Game"
		window.setTitle("Clue Game");
		
		// Close the program properly if the user tries to close the program
		window.setOnCloseRequest(e ->{
			e.consume();
	    	closeGame();
		});
		
		// Temporary Pane for drawing board and room Names
	    BorderPane tempPane = new BorderPane();
	    
	    
	    // BorderPane for the Control GUI at the bottom of the page
	    BorderPane controlGUIPane = ControlGUI.drawControlGUI(board);
	    
	    // Update tempPane to include the board drawing
	    board.drawBoard(tempPane);
	    
	    
	    // Main border pane
	    BorderPane pane = board.drawRoomNames(tempPane);
	   
	    
	    // Update pane with the players drawn on
	    pane = board.drawPlayers(pane);
	    
	    // Set the comtrol GUI to the bottom of pane
	    pane.setBottom(controlGUIPane);
	    
	    // Menu at the top of the screen
	    Menu menu = new Menu("_File");
	    menu.setStyle(("-fx-font-size: 20;"));
	    
	    // Buttom to exit out of the game inside the top menu
	    MenuItem exitItem = new MenuItem("Exit");
	    exitItem.setOnAction(e ->{
	    	closeGame();
	    });
	    
	    // Menu item for accessing the Detective Notes
	    MenuItem detectiveNotesItem = new MenuItem("Detective Notes");
	    
	    // DetectiveNotes object to keep track of users button clickes inside of the notes window
	    DetectiveNotes notes = new DetectiveNotes();
	    
	    // Display detective notes when detective notes menu item is clicked
	    detectiveNotesItem.setOnAction(e ->{
	    	notes.displayDetectiveNotes();
	    });
	    
	    // Add menu items to menu
	    menu.getItems().add(detectiveNotesItem);
	    menu.getItems().add(new SeparatorMenuItem());
	    menu.getItems().add(exitItem);
	    
	    // Menu bar at the top of the screen
	    MenuBar menuBar = new MenuBar();
	    menuBar.getMenus().addAll(menu);
	    
	    // Set the menuBar to the top of the screen
	    pane.setTop(menuBar);
	    
	    // Get the Vbox that displays the players cards
	    VBox myCardsVbox = MyCards.DisplayMyCards(board.getHumanPlayer());
	    
	    // Set the my Cards Vbox to the right section of the screen
	    pane.setRight(myCardsVbox);
	
	    
	    // Create a scene and pass in pane and the correct dimensions for the scene 
		Scene scene = new Scene(pane, 1000, 820);
		
		
		// Set the window to the scene we just created
		window.setScene(scene);
		
		// Set minimum window dimensions so everything looks okay still when the window is downsized
		window.setMinWidth(1000);
		window.setMinHeight(900);
		
		
		// Show the window to the user
		window.show();
	
	}
	
	// Method to close program
	public static void closeGame() {
		System.exit(0);
	}
}
	